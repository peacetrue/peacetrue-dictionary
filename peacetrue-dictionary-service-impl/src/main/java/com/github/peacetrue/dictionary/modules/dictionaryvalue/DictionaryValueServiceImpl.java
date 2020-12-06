package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.Range;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeService;
import com.github.peacetrue.spring.data.relational.core.query.CriteriaUtils;
import com.github.peacetrue.spring.data.relational.core.query.UpdateUtils;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.util.DateUtils;
import com.github.peacetrue.util.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.data.domain.*;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 字典项值服务实现
 *
 * @author xiayx
 */
@Slf4j
@Service
public class DictionaryValueServiceImpl implements DictionaryValueService {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;
    @Autowired
    private R2dbcEntityTemplate entityTemplate;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public static Criteria buildCriteria(DictionaryValueQuery params) {
        return CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(CriteriaUtils.smartIn("id"), params::getId),
                CriteriaUtils.nullableCriteria(Criteria.where("dictionaryTypeId")::is, params::getDictionaryTypeId),
                CriteriaUtils.nullableCriteria(Criteria.where("dictionaryTypeCode")::is, params::getDictionaryTypeCode),
                CriteriaUtils.nullableCriteria(Criteria.where("code")::is, params::getCode),
                CriteriaUtils.nullableCriteria(Criteria.where("name")::like, value -> "%" + value + "%", params::getName),
                CriteriaUtils.nullableCriteria(Criteria.where("creatorId")::is, params::getCreatorId),
                CriteriaUtils.nullableCriteria(Criteria.where("createdTime")::greaterThanOrEquals, params.getCreatedTime()::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("createdTime")::lessThan, DateUtils.DATE_CELL_EXCLUDE, params.getCreatedTime()::getUpperBound),
                CriteriaUtils.nullableCriteria(Criteria.where("modifierId")::is, params::getModifierId),
                CriteriaUtils.nullableCriteria(Criteria.where("modifiedTime")::greaterThanOrEquals, params.getModifiedTime()::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("modifiedTime")::lessThan, DateUtils.DATE_CELL_EXCLUDE, params.getModifiedTime()::getUpperBound)
        );
    }

    @Override
    @Transactional
    public Mono<DictionaryValueVO> add(DictionaryValueAdd params) {
        log.info("新增字典项值信息[{}]", params);
        if (params.getRemark() == null) params.setRemark("");
        DictionaryValue entity = BeanUtils.map(params, DictionaryValue.class);
        entity.setDictionaryTypeCode("");
        entity.setCreatorId(params.getOperatorId());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setModifierId(entity.getCreatorId());
        entity.setModifiedTime(entity.getCreatedTime());
        Query dictionaryTypeIdQuery = Query.query(Criteria.where("dictionaryTypeId")
                .is(params.getDictionaryTypeId()));
        return entityTemplate.count(dictionaryTypeIdQuery, DictionaryValue.class)
                .switchIfEmpty(Mono.just(0L))
                .doOnNext(count -> entity.setSerialNumber(count.intValue() + 1))
                .flatMap(count -> entityTemplate.insert(entity))
                .map(item -> BeanUtils.map(item, DictionaryValueVO.class))
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<DictionaryValueVO>> query(@Nullable DictionaryValueQuery params, @Nullable Pageable pageable, String... projection) {
        log.info("分页查询字典项值信息[{}]", params);
        if (params == null) params = DictionaryValueQuery.DEFAULT;
        if (params.getCreatedTime() == null) params.setCreatedTime(Range.LocalDateTime.DEFAULT);
        if (params.getModifiedTime() == null) params.setModifiedTime(Range.LocalDateTime.DEFAULT);
        Pageable finalPageable = pageable == null ? PageRequest.of(0, 10) : pageable;
        Criteria where = buildCriteria(params);

        return entityTemplate.count(Query.query(where), DictionaryValue.class)
                .flatMap(total -> total == 0L ? Mono.empty() : Mono.just(total))
                .<Page<DictionaryValueVO>>flatMap(total -> {
                    Query query = Query.query(where).with(finalPageable).sort(finalPageable.getSortOr(Sort.by("createdTime").descending()));
                    return entityTemplate.select(query, DictionaryValue.class)
                            .map(item -> BeanUtils.map(item, DictionaryValueVO.class))
                            .reduce(new ArrayList<>(), StreamUtils.reduceToCollection())
                            .map(item -> new PageImpl<>(item, finalPageable, total));
                })
                .switchIfEmpty(Mono.just(new PageImpl<>(Collections.emptyList(), finalPageable, 0L)));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<DictionaryValueVO> query(@Nullable DictionaryValueQuery params, @Nullable Sort sort, String... projection) {
        log.info("全量查询字典项值信息[{}]", params);
        if (params == null) params = DictionaryValueQuery.DEFAULT;
        if (params.getCreatedTime() == null) params.setCreatedTime(Range.LocalDateTime.DEFAULT);
        if (params.getModifiedTime() == null) params.setModifiedTime(Range.LocalDateTime.DEFAULT);
        if (sort == null) sort = Sort.by("createdTime").descending();
        Criteria where = buildCriteria(params);
        Query query = Query.query(where).sort(sort).limit(100);
        return entityTemplate.select(query, DictionaryValue.class)
                .map(item -> BeanUtils.map(item, DictionaryValueVO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<DictionaryValueVO> get(DictionaryValueGet params, String... projection) {
        log.info("获取字典项值信息[{}]", params);
//        Criteria where = CriteriaUtils.and(
//                CriteriaUtils.nullableCriteria(Criteria.where("id")::is, params::getId),
//        );
        Criteria where = Criteria.where("id").is(params.getId());
        return entityTemplate.selectOne(Query.query(where), DictionaryValue.class)
                .map(item -> BeanUtils.map(item, DictionaryValueVO.class));
    }

    @Override
    @Transactional
    public Mono<Integer> modify(DictionaryValueModify params) {
        log.info("修改字典项值信息[{}]", params);
        Criteria where = Criteria.where("id").is(params.getId());
        Query idQuery = Query.query(where);
        return entityTemplate.selectOne(idQuery, DictionaryValue.class)
                .map(item -> BeanUtils.map(item, DictionaryValueVO.class))
                .zipWhen(entity -> {
                    DictionaryValue modify = BeanUtils.map(params, DictionaryValue.class);
                    modify.setModifierId(params.getOperatorId());
                    modify.setModifiedTime(LocalDateTime.now());
                    Update update = UpdateUtils.selectiveUpdateFromExample(modify);
                    return entityTemplate.update(idQuery, update, DictionaryValue.class);
                })
                .doOnNext(tuple2 -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(tuple2.getT1(), params)))
                .map(Tuple2::getT2)
                .switchIfEmpty(Mono.just(0));
    }

    @Override
    @Transactional
    public Mono<Integer> delete(DictionaryValueDelete params) {
        log.info("删除字典项值信息[{}]", params);
        Criteria where = Criteria.where("id").is(params.getId());
        Query idQuery = Query.query(where);
        return entityTemplate.selectOne(idQuery, DictionaryValue.class)
                .map(item -> BeanUtils.map(item, DictionaryValueVO.class))
                .zipWhen(region -> entityTemplate.delete(idQuery, DictionaryValue.class))
                .doOnNext(tuple2 -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(tuple2.getT1(), params)))
                .map(Tuple2::getT2)
                .switchIfEmpty(Mono.just(0));
    }

}
