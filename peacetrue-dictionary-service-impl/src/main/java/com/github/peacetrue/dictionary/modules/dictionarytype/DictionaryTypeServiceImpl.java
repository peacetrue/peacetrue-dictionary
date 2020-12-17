package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.core.Range;
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
 * 字典类型服务实现
 *
 * @author xiayx
 */
@Slf4j
@Service
public class DictionaryTypeServiceImpl implements DictionaryTypeService {

    @Autowired
    private R2dbcEntityTemplate entityTemplate;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public static Criteria buildCriteria(DictionaryTypeQuery params) {
        return CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(CriteriaUtils.smartIn("id"), params::getId),
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
    public Mono<DictionaryTypeVO> add(DictionaryTypeAdd params) {
        log.info("新增字典类型信息[{}]", params);
        DictionaryType entity = BeanUtils.map(params, DictionaryType.class);
        if (entity.getRemark() == null) entity.setRemark("");
        entity.setCreatorId(params.getOperatorId());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setModifierId(entity.getCreatorId());
        entity.setModifiedTime(entity.getCreatedTime());
        return entityTemplate.insert(entity)
                .map(item -> BeanUtils.map(item, DictionaryTypeVO.class))
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<DictionaryTypeVO>> query(DictionaryTypeQuery params, @Nullable Pageable pageable, String... projection) {
        log.info("分页查询字典类型信息[{}]", params);
        if (params.getCreatedTime() == null) params.setCreatedTime(Range.LocalDateTime.DEFAULT);
        if (params.getModifiedTime() == null) params.setModifiedTime(Range.LocalDateTime.DEFAULT);
        Pageable finalPageable = pageable == null ? PageRequest.of(0, 10) : pageable;
        Criteria where = buildCriteria(params);

        return entityTemplate.count(Query.query(where), DictionaryType.class)
                .flatMap(total -> total == 0L ? Mono.empty() : Mono.just(total))
                .<Page<DictionaryTypeVO>>flatMap(total -> {
                    Query query = Query.query(where).with(finalPageable).sort(finalPageable.getSortOr(Sort.by("createdTime").descending()));
                    return entityTemplate.select(query, DictionaryType.class)
                            .map(item -> BeanUtils.map(item, DictionaryTypeVO.class))
                            .reduce(new ArrayList<>(), StreamUtils.reduceToCollection())
                            .map(item -> new PageImpl<>(item, finalPageable, total));
                })
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)))
                .switchIfEmpty(Mono.just(new PageImpl<>(Collections.emptyList(), finalPageable, 0L)));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<DictionaryTypeVO> query(DictionaryTypeQuery params, @Nullable Sort sort, String... projection) {
        log.info("全量查询字典类型信息[{}]", params);
        if (params.getCreatedTime() == null) params.setCreatedTime(Range.LocalDateTime.DEFAULT);
        if (params.getModifiedTime() == null) params.setModifiedTime(Range.LocalDateTime.DEFAULT);
        if (sort == null) sort = Sort.by("createdTime").descending();
        Criteria where = buildCriteria(params);
        Query query = Query.query(where).sort(sort).limit(100);
        return entityTemplate.select(query, DictionaryType.class)
                .map(item -> BeanUtils.map(item, DictionaryTypeVO.class))
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)))
                ;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<DictionaryTypeVO> get(DictionaryTypeGet params, String... projection) {
        log.info("获取字典类型信息[{}]", params);
        Criteria where = CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(Criteria.where("id")::is, params::getId),
                CriteriaUtils.nullableCriteria(Criteria.where("code")::is, params::getCode)
        );
        return entityTemplate.selectOne(Query.query(where), DictionaryType.class)
                .map(item -> BeanUtils.map(item, DictionaryTypeVO.class))
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)))
                ;
    }

    @Override
    @Transactional
    public Mono<Integer> modify(DictionaryTypeModify params) {
        log.info("修改字典类型信息[{}]", params);
        Criteria where = Criteria.where("id").is(params.getId());
        Query idQuery = Query.query(where);
        return entityTemplate.selectOne(idQuery, DictionaryType.class)
                .map(item -> BeanUtils.map(item, DictionaryTypeVO.class))
                .zipWhen(entity -> {
                    DictionaryType modify = BeanUtils.map(params, DictionaryType.class);
                    modify.setModifierId(params.getOperatorId());
                    modify.setModifiedTime(LocalDateTime.now());
                    Update update = UpdateUtils.selectiveUpdateFromExample(modify);
                    return entityTemplate.update(idQuery, update, DictionaryType.class);
                })
                .doOnNext(tuple2 -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(tuple2.getT1(), params)))
                .map(Tuple2::getT2)
                .switchIfEmpty(Mono.just(0));
    }

    @Override
    @Transactional
    public Mono<Integer> delete(DictionaryTypeDelete params) {
        log.info("删除字典类型信息[{}]", params);
        Criteria where = Criteria.where("id").is(params.getId());
        Query idQuery = Query.query(where);
        return entityTemplate.selectOne(idQuery, DictionaryType.class)
                .map(item -> BeanUtils.map(item, DictionaryTypeVO.class))
                .zipWhen(region -> entityTemplate.delete(idQuery, DictionaryType.class))
                .doOnNext(tuple2 -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(tuple2.getT1(), params)))
                .map(Tuple2::getT2)
                .switchIfEmpty(Mono.just(0));
    }

}
