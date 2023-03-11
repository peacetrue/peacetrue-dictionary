package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.beans.createmodify.CreateModify;
import com.github.peacetrue.operator.OperatorSupplier;
import com.github.peacetrue.persistence.criteria.CriteriaBuilderUtils;
import com.github.peacetrue.range.LocalDateRange;
import com.github.peacetrue.range.LocalDateTimeRange;
import com.github.peacetrue.spring.beans.BeanUtils;
import com.github.peacetrue.spring.data.domain.PageableUtils;
import com.github.peacetrue.spring.data.domain.SortUtils;
import com.github.peacetrue.lang.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nullable;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

import static com.github.peacetrue.beans.properties.createdtime.CreatedTimeCapable.PROPERTY_CREATED_TIME;
import static com.github.peacetrue.lang.ObjectUtils.invokeSafely;

/**
 * 字典类型服务实现。
 *
 * @author peace
 */
@Slf4j
@Service
@Validated
@AllArgsConstructor
public class DictionaryTypeServiceImpl implements DictionaryTypeService {

    private DictionaryTypeRepository dictionaryTypeRepository;
    private OperatorSupplier operatorSupplier;
    private ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public DictionaryTypeVO add(DictionaryTypeAdd params) {
        log.info("add DictionaryType: {}", params);
        DictionaryType entity = BeanUtils.convert(params, DictionaryType.class);
        ObjectUtils.setDefault(entity::getRemark, entity::setRemark, "");
        CreateModify.setCreateModify(operatorSupplier.getOperator(), entity, LocalDateTime.now());
        dictionaryTypeRepository.save(entity);
        DictionaryTypeVO vo = BeanUtils.convert(entity, DictionaryTypeVO.class);
        // 发布一个后置新增事件，新增字典值项集合，避免依赖 DictionaryValueService
        eventPublisher.publishEvent(new PayloadApplicationEvent<>(vo, params));
        return vo;
    }

    /**
     * 构建查询条件。
     *
     * @param params 查询参数
     * @return 查询条件
     */
    private static Predicate buildPredicates(CriteriaBuilder cb, Root<DictionaryType> root, DictionaryTypeQuery params) {
        LocalDateTimeRange createdTime = invokeSafely(params.getCreatedTime(), range -> range.toLocalDateTimeRange());
        LocalDateTimeRange modifiedTime = invokeSafely(params.getModifiedTime(), range -> range.toLocalDateTimeRange());
        return CriteriaBuilderUtils.and(cb, Arrays.asList(
                invokeSafely(params.getId(), id -> root.get("id").in(Arrays.asList(id))),
                invokeSafely(params.getCode(), code -> cb.like(root.get("code"), "%" + code + "%")),
                invokeSafely(params.getName(), name -> cb.like(root.get("name"), "%" + name + "%")),
                CriteriaBuilderUtils.between(cb, root.get("createdTime"), createdTime),
                CriteriaBuilderUtils.between(cb, root.get("modifiedTime"), modifiedTime)
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DictionaryTypeVO> queryPage(DictionaryTypeQuery params, Pageable pageable, String... projection) {
        log.info("page query DictionaryType: {}", params);
        pageable = PageableUtils.setDefaultSort(pageable, SortUtils.SORT_CREATED_TIME_DESC);
        Specification<DictionaryType> specification = (root, query, cb) -> buildPredicates(cb, root, params);
        return dictionaryTypeRepository.findAll(specification, pageable)
                .map(item -> BeanUtils.convert(item, DictionaryTypeVO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictionaryTypeVO> queryList(DictionaryTypeQuery params, Pageable pageable, String... projection) {
        log.info("list query DictionaryType: {}", params);
        return queryPage(params, pageable, projection).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public DictionaryTypeVO get(DictionaryTypeGet params, String... projection) {
        log.info("get DictionaryType: {}", params);
        return dictionaryTypeRepository.findOne((root, query, cb) -> CriteriaBuilderUtils.or(cb, Arrays.asList(
                        invokeSafely(params.getId(), id -> cb.equal(root.get("id"), id)),
                        invokeSafely(params.getCode(), code -> cb.equal(root.get("code"), code))
                )))
                .map(item -> BeanUtils.convert(item, DictionaryTypeVO.class))
                .orElseThrow(() -> new EntityNotFoundException("Can't fount DictionaryType by " + params))
                ;
    }

    @Override
    @Transactional
    public Integer modify(DictionaryTypeModify params) {
        log.info("modify DictionaryType: {}", params);
        DictionaryType dictionaryType = dictionaryTypeRepository.getById(params.getId());
        BeanUtils.copyPropertiesNotEmpty(params, dictionaryType);
        dictionaryType.setModifierId((Long) operatorSupplier.getOperator().getId());
        dictionaryType.setModifiedTime(LocalDateTime.now());
        dictionaryTypeRepository.save(dictionaryType);
        return 1;
    }

    @Override
    @Transactional
    public Integer delete(DictionaryTypeDelete params) {
        log.info("delete DictionaryType: {}", params);
        Optional<DictionaryType> optional = dictionaryTypeRepository.findById(params.getId());
        if (!optional.isPresent()) return 0;
        // 发布一个删除前置检查事件，字典值项服务需判断不存在记录方可删除
        DictionaryType entity = optional.get();
        eventPublisher.publishEvent(new PayloadApplicationEvent<>(BeanUtils.convert(entity, DictionaryTypeVO.class), params));
        dictionaryTypeRepository.delete(entity);
        return 1;
    }

}
