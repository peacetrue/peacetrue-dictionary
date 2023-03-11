package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.dictionary.modules.dictionarytype.*;
import com.github.peacetrue.lang.ObjectUtils;
import com.github.peacetrue.operator.OperatorSupplier;
import com.github.peacetrue.range.LocalDateRange;
import com.github.peacetrue.range.LocalDateTimeRange;
import com.github.peacetrue.spring.beans.BeanUtils;
import com.github.peacetrue.spring.data.relational.core.query.CriteriaUtils;
import com.github.peacetrue.spring.data.relational.core.query.QueryUtils;
import com.github.peacetrue.spring.data.relational.core.query.UpdateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 字典项值服务实现。
 *
 * @author peace
 */
@Slf4j
@Service
@AllArgsConstructor
public class DictionaryValueServiceImpl implements DictionaryValueService {

    private R2dbcEntityOperations entityOperations;
    private OperatorSupplier operatorSupplier;
    private ApplicationEventPublisher eventPublisher;
    private DictionaryTypeService dictionaryTypeService;

    @Override
    @Transactional
    public Mono<DictionaryValueVO> add(DictionaryValueAdd params) {
        log.info("add DictionaryValue: {}", params);
        // 这里转换时，自动转换 DictionaryValueAddInner 中的 dictionaryTypeCode 字段
        DictionaryValue entity = BeanUtils.convert(params, DictionaryValue.class);
        ObjectUtils.setDefault(entity::getRemark, entity::setRemark, "");
        entity.setCreatorId((Long) operatorSupplier.getOperator().getId());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setModifierId(entity.getCreatorId());
        entity.setModifiedTime(entity.getCreatedTime());
        return Mono.justOrEmpty(entity.getDictionaryTypeCode())
                .switchIfEmpty(Mono.defer(() -> setDictionaryTypeCode(entity)))
                .then(Mono.justOrEmpty(entity.getSerialNumber()))
                .switchIfEmpty(Mono.defer(() -> setNextSerialNumber(entity)))
                .flatMap(serialNumber -> entityOperations.insert(entity))
                .map(item -> BeanUtils.convert(item, DictionaryValueVO.class))
                ;
    }

    private Mono<String> setDictionaryTypeCode(DictionaryValue entity) {
        return dictionaryTypeService.get(new DictionaryTypeGet().setId(entity.getDictionaryTypeId()))
                .map(DictionaryTypeVO::getCode)
                .doOnNext(entity::setDictionaryTypeCode);
    }

    private Mono<Integer> setNextSerialNumber(DictionaryValue entity) {
        return entityOperations.getDatabaseClient()
                .sql("select max(serial_number) from dictionary_value where dictionary_type_id=:dictionaryTypeId")
                .bind("dictionaryTypeId", entity.getDictionaryTypeId())
                .map(row -> ObjectUtils.invokeSafely(row.get(0), 1, value -> ((Short) value).intValue() + 1))
                .one()
                .doOnNext(entity::setSerialNumber);
    }

    // 行不通的反例

    private Mono<Integer> getNextSerialNumber0(DictionaryValue entity) {
        // BadSqlGrammarException: executeMany; bad SQL grammar [SELECT dictionary_value.max(serial_number) FROM dictionary_value WHERE dictionary_value.dictionary_type_id = $1 LIMIT 2]; nested exception is io.r2dbc.spi.R2dbcBadGrammarException: [90079] [90079] Schema "dictionary_value" not found; SQL statement:
        // SELECT dictionary_value.max(serial_number) FROM dictionary_value WHERE dictionary_value.dictionary_type_id = $1 LIMIT 2 [90079-212]))
        Query query = Query.query(Criteria.where("dictionaryTypeId").is(entity.getDictionaryTypeId()))
                .columns("max(serial_number)");
        return entityOperations
                .select(DictionaryValue.class)
                .from("dictionary_value")
                .as(Integer.class)
                .matching(query)
                .one()
                .switchIfEmpty(Mono.just(0))
                .map(serialNumber -> serialNumber + 1)
                .doOnNext(serialNumber -> log.debug("got serialNumber: {}", serialNumber))
                ;
    }

    /**
     * 添加字典值项。
     *
     * @param event 字典类型新增事件
     */
    @EventListener
    public void addDictionaryValues(PayloadApplicationEvent<DictionaryTypeAdd> event) {
        log.info("add DictionaryValues after add DictionaryType: {}", event.getSource());
        addDictionaryValues((DictionaryTypeVO) event.getSource(), event.getPayload().getDictionaryValues())
                .publishOn(Schedulers.immediate())
                .subscribe();
    }

    private Mono<List<DictionaryValueVO>> addDictionaryValues(DictionaryTypeVO type, List<DictionaryValueAdd> values) {
        return Mono.justOrEmpty(values)
                .flatMapMany(Flux::fromIterable)
                .index()
                .flatMap(indexDictionary -> {
                    DictionaryValueAddInner dictionary = BeanUtils.convert(indexDictionary.getT2(), DictionaryValueAddInner.class);
                    dictionary.setDictionaryTypeId(type.getId());
                    dictionary.setDictionaryTypeCode(type.getCode());
                    dictionary.setSerialNumber(indexDictionary.getT1().intValue() + 1);
                    return this.add(dictionary);
                })
                .collectList()
                .doOnNext(type::setDictionaryValues)
                ;
    }

    private static Criteria buildCriteria(DictionaryValueQuery params) {
        LocalDateTimeRange createdTime = ObjectUtils.invokeSafely(params.getCreatedTime(), LocalDateTimeRange.DEFAULT, LocalDateRange::toLocalDateTimeRange);
        LocalDateTimeRange modifiedTime = ObjectUtils.invokeSafely(params.getModifiedTime(), LocalDateTimeRange.DEFAULT, LocalDateRange::toLocalDateTimeRange);
        return CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(CriteriaUtils.smartIn("id"), params::getId),
                CriteriaUtils.nullableCriteria(Criteria.where("dictionaryTypeId")::is, params::getDictionaryTypeId),
                CriteriaUtils.nullableCriteria(Criteria.where("code")::like, value -> "%" + value + "%", params::getCode),
                CriteriaUtils.nullableCriteria(Criteria.where("name")::like, value -> "%" + value + "%", params::getName),
                CriteriaUtils.nullableCriteria(Criteria.where("createdTime")::greaterThanOrEquals, createdTime::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("createdTime")::lessThan, createdTime::getUpperBound),
                CriteriaUtils.nullableCriteria(Criteria.where("modifiedTime")::greaterThanOrEquals, modifiedTime::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("modifiedTime")::lessThan, modifiedTime::getUpperBound)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<DictionaryValueVO>> queryPage(DictionaryValueQuery params, Pageable pageable, String... projection) {
        log.info("page query DictionaryValue: {}", params);
        Criteria where = buildCriteria(params);
        return entityOperations.count(Query.query(where), DictionaryValue.class)
                .flatMap(total -> {
                    if (total == 0L) {
                        return Mono.just(new PageImpl<>(Collections.emptyList(), pageable, 0L));
                    }
                    Query query = Query.query(where).with(pageable).sort(pageable.getSortOr(SORT_SERIAL_NUMBER));
                    return entityOperations.select(query, DictionaryValue.class)
                            .map(item -> BeanUtils.convert(item, DictionaryValueVO.class))
                            .collectList()
                            .map(item -> new PageImpl<>(item, pageable, total));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<DictionaryValueVO> queryList(DictionaryValueQuery params, Pageable pageable, String... projection) {
        log.info("list query DictionaryValue: {}", params);
        Query query = Query.query(buildCriteria(params)).with(pageable).sort(pageable.getSortOr(SORT_SERIAL_NUMBER));
        return entityOperations.select(query, DictionaryValue.class)
                .map(item -> BeanUtils.convert(item, DictionaryValueVO.class))
                ;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<DictionaryValueVO> get(DictionaryValueGet params, String... projection) {
        log.info("get DictionaryValue: {}", params);
        Criteria where = CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(Criteria.where("id")::is, params::getId),
                CriteriaUtils.nullableCriteria(Criteria.where("dictionaryTypeCode")::is, params::getDictionaryTypeCode),
                CriteriaUtils.nullableCriteria(Criteria.where("code")::is, params::getCode)
        );
        return entityOperations.selectOne(Query.query(where), DictionaryValue.class)
                .map(item -> BeanUtils.convert(item, DictionaryValueVO.class))
                ;
    }

    @Override
    @Transactional
    public Mono<Integer> modify(DictionaryValueModify params) {
        log.info("modify DictionaryValue: {}", params);
        Query idQuery = QueryUtils.id(params::getId);
        return entityOperations.selectOne(idQuery, DictionaryValue.class)
                .flatMap(entity -> {
                    DictionaryValue modify = BeanUtils.convert(params, DictionaryValue.class);
                    modify.setModifierId((Long) operatorSupplier.getOperator().getId());
                    modify.setModifiedTime(LocalDateTime.now());
                    Update update = UpdateUtils.selectiveUpdateFromExample(modify);
                    return entityOperations.update(idQuery, update, DictionaryValue.class);
                })
                .switchIfEmpty(Mono.just(0));
    }

    @Override
    @Transactional
    public Mono<Integer> delete(DictionaryValueDelete params) {
        log.info("delete DictionaryValue: {}", params);
        Query idQuery = QueryUtils.id(params::getId);
        return entityOperations.selectOne(idQuery, DictionaryValue.class)
                .map(entity -> BeanUtils.convert(entity, DictionaryValueVO.class))
                // 发布一个删除前置检查事件，不存在关联记录方可删除
                .doOnNext(vo -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(vo, params)))
                .flatMap(vo -> entityOperations.delete(idQuery, DictionaryValue.class))
                .switchIfEmpty(Mono.just(0));
    }

    /**
     * 删除字典类型前，检查是否存在字典项值。
     *
     * @param event 删除事件
     */
    @EventListener
    public void checkExistDictionaryValue(PayloadApplicationEvent<DictionaryTypeDelete> event) {
        DictionaryTypeVO source = (DictionaryTypeVO) event.getSource();
        log.info("check exists DictionaryValue before delete DictionaryType: {}", source);
        Query query = Query.query(Criteria.where("dictionaryTypeId").is(source.getId()));
        entityOperations.count(query, DictionaryValue.class)
                .filter(count -> count == 0)
                .switchIfEmpty(Mono.error(() -> new IllegalStateException("Can't delete DictionaryType which has DictionaryValue")))
                .publishOn(Schedulers.immediate())
                .subscribe();
    }


}
