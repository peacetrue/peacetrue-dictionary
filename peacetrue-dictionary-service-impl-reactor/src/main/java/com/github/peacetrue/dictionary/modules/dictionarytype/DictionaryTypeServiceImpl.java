package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.operator.OperatorSupplier;
import com.github.peacetrue.range.LocalDateRange;
import com.github.peacetrue.range.LocalDateTimeRange;
import com.github.peacetrue.spring.beans.BeanUtils;
import com.github.peacetrue.spring.data.domain.SortUtils;
import com.github.peacetrue.spring.data.relational.core.query.CriteriaUtils;
import com.github.peacetrue.spring.data.relational.core.query.QueryUtils;
import com.github.peacetrue.spring.data.relational.core.query.UpdateUtils;
import com.github.peacetrue.lang.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
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
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * 字典类型服务实现。
 *
 * @author peace
 */
@Slf4j
@Service
@AllArgsConstructor
public class DictionaryTypeServiceImpl implements DictionaryTypeService {

    private R2dbcEntityOperations entityOperations;
    private OperatorSupplier operatorSupplier;
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Mono<DictionaryTypeVO> add(DictionaryTypeAdd params) {
        log.info("add DictionaryType: {}", params);
        DictionaryType entity = BeanUtils.convert(params, DictionaryType.class);
        ObjectUtils.setDefault(entity::getRemark, entity::setRemark, "");
        entity.setCreatorId((Long) operatorSupplier.getOperator().getId());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setModifierId(entity.getCreatorId());
        entity.setModifiedTime(entity.getCreatedTime());
        return entityOperations.insert(entity)
                .map(item -> BeanUtils.convert(item, DictionaryTypeVO.class))
                // 发布一个后置新增事件，新增字典值项集合，避免依赖 DictionaryValueService
                .doOnNext(vo -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(vo, params)))
                ;
    }

    /**
     * 构建查询条件。
     *
     * @param params 查询参数
     * @return 查询条件
     */
    private static Criteria buildCriteria(DictionaryTypeQuery params) {
        LocalDateTimeRange createdTime = ObjectUtils.invokeSafely(params.getCreatedTime(), LocalDateTimeRange.DEFAULT, LocalDateRange::toLocalDateTimeRange);
        LocalDateTimeRange modifiedTime = ObjectUtils.invokeSafely(params.getModifiedTime(), LocalDateTimeRange.DEFAULT, LocalDateRange::toLocalDateTimeRange);
        return CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(CriteriaUtils.smartIn("id"), params::getId),
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
    public Mono<Page<DictionaryTypeVO>> queryPage(DictionaryTypeQuery params, Pageable pageable, String... projection) {
        log.info("page query DictionaryType: {}", params);
        Criteria where = buildCriteria(params);
        return entityOperations.count(Query.query(where), DictionaryType.class)
                .<Page<DictionaryTypeVO>>flatMap(total -> {
                    if (total == 0L) {
                        return Mono.just(new PageImpl<>(Collections.emptyList(), pageable, 0L));
                    }
                    Query query = Query.query(where).with(pageable).sort(pageable.getSortOr(SortUtils.SORT_CREATED_TIME_DESC));
                    return entityOperations.select(query, DictionaryType.class)
                            .map(item -> BeanUtils.convert(item, DictionaryTypeVO.class))
                            .collectList()
                            .map(item -> new PageImpl<>(item, pageable, total));
                })
                ;
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<DictionaryTypeVO> queryList(DictionaryTypeQuery params, Pageable pageable, String... projection) {
        log.info("list query DictionaryType: {}", params);
        Query query = Query.query(buildCriteria(params)).with(pageable).sort(pageable.getSortOr(SortUtils.SORT_CREATED_TIME_DESC));
        return entityOperations.select(query, DictionaryType.class)
                .map(item -> BeanUtils.convert(item, DictionaryTypeVO.class))
                ;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<DictionaryTypeVO> get(DictionaryTypeGet params, String... projection) {
        log.info("get DictionaryType: {}", params);
        Criteria where = CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(Criteria.where("id")::is, params::getId),
                CriteriaUtils.nullableCriteria(Criteria.where("code")::is, params::getCode)
        );
        return entityOperations.selectOne(Query.query(where), DictionaryType.class)
                .map(item -> BeanUtils.convert(item, DictionaryTypeVO.class))
                ;
    }

    @Override
    @Transactional
    public Mono<Integer> modify(DictionaryTypeModify params) {
        log.info("modify DictionaryType: {}", params);
        Query idQuery = QueryUtils.id(params::getId);
        return entityOperations.selectOne(idQuery, DictionaryType.class)
                .map(item -> BeanUtils.convert(item, DictionaryTypeVO.class))
                .zipWhen(entity -> {
                    DictionaryType modify = BeanUtils.convert(params, DictionaryType.class);
                    modify.setModifierId((Long) operatorSupplier.getOperator().getId());
                    modify.setModifiedTime(LocalDateTime.now());
                    Update update = UpdateUtils.selectiveUpdateFromExample(modify);
                    return entityOperations.update(idQuery, update, DictionaryType.class);
                })
                .map(Tuple2::getT2)
                .switchIfEmpty(Mono.just(0));
    }

    @Override
    @Transactional
    public Mono<Integer> delete(DictionaryTypeDelete params) {
        log.info("delete DictionaryType: {}", params);
        Query idQuery = QueryUtils.id(params::getId);
        return entityOperations.selectOne(idQuery, DictionaryType.class)
                .map(entity -> BeanUtils.convert(entity, DictionaryTypeVO.class))
                // 发布一个删除前置检查事件，字典值项服务需判断不存在记录方可删除
                .doOnNext(vo -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(vo, params)))
                .flatMap(region -> entityOperations.delete(idQuery, DictionaryType.class))
                .switchIfEmpty(Mono.just(0));
    }

}
