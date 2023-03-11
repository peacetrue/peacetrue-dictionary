package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.beans.createmodify.CreateModify;
import com.github.peacetrue.dictionary.modules.dictionarytype.*;
import com.github.peacetrue.operator.OperatorSupplier;
import com.github.peacetrue.persistence.criteria.CriteriaBuilderUtils;
import com.github.peacetrue.range.LocalDateRange;
import com.github.peacetrue.range.LocalDateTimeRange;
import com.github.peacetrue.spring.beans.BeanUtils;
import com.github.peacetrue.spring.data.domain.PageableUtils;
import com.github.peacetrue.lang.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.github.peacetrue.lang.ObjectUtils.invokeSafely;

/**
 * 字典项值服务实现。
 *
 * @author peace
 */
@Slf4j
@Validated
@Service
@AllArgsConstructor
public class DictionaryValueServiceImpl implements DictionaryValueService {

    private DictionaryValueRepository dictionaryValueRepository;
    private DictionaryTypeRepository dictionaryTypeRepository;
    private OperatorSupplier operatorSupplier;
    private ApplicationEventPublisher eventPublisher;
    private DictionaryTypeService dictionaryTypeService;

    @Override
    @Transactional
    public DictionaryValueVO add(DictionaryValueAdd params) {
        log.info("add DictionaryValue: {}", params);
        // 这里转换时，自动转换 DictionaryValueAddInner 中的 dictionaryTypeCode 字段
        DictionaryValue entity = BeanUtils.convert(params, DictionaryValue.class);
        ObjectUtils.setDefaultLazily(entity::getDictionaryTypeCode, entity::setDictionaryTypeCode, () -> dictionaryTypeRepository.findRequiredCodeById(entity.getDictionaryTypeId()));
        ObjectUtils.setDefaultLazily(entity::getSerialNumber, entity::setSerialNumber, () -> dictionaryValueRepository.findNextSerialNumber(entity.getDictionaryTypeId()));
        ObjectUtils.setDefault(entity::getRemark, entity::setRemark, "");
        CreateModify.setCreateModify(operatorSupplier.getOperator(), entity, LocalDateTime.now());
        dictionaryValueRepository.save(entity);
        return BeanUtils.convert(entity, DictionaryValueVO.class);
    }

    /**
     * 添加字典值项。
     *
     * @param event 字典类型新增事件
     */
    @EventListener
    public void addDictionaryValues(PayloadApplicationEvent<DictionaryTypeAdd> event) {
        log.info("add DictionaryValues after add DictionaryType: {}", event.getSource());
        addDictionaryValues((DictionaryTypeVO) event.getSource(), event.getPayload().getDictionaryValues());
    }

    private void addDictionaryValues(DictionaryTypeVO type, List<DictionaryValueAdd> values) {
        if (CollectionUtils.isEmpty(values)) return;
        for (int i = 0; i < values.size(); i++) {
            DictionaryValueAddInner dictionary = BeanUtils.convert(values.get(i), DictionaryValueAddInner.class);
            dictionary.setDictionaryTypeId(type.getId());
            dictionary.setDictionaryTypeCode(type.getCode());
            dictionary.setSerialNumber(i + 1);
            this.add(dictionary);
        }
    }

    private static Predicate buildPredicates(CriteriaBuilder cb, Root<DictionaryValue> root, DictionaryValueQuery params) {
        LocalDateTimeRange createdTime = invokeSafely(params.getCreatedTime(), range -> range.toLocalDateTimeRange());
        LocalDateTimeRange modifiedTime = invokeSafely(params.getModifiedTime(), range -> range.toLocalDateTimeRange());
        return CriteriaBuilderUtils.and(cb, Arrays.asList(
                invokeSafely(params.getId(), id -> cb.in(root.get("id")).in(Arrays.asList(id))),
                invokeSafely(params.getDictionaryTypeId(), dictionaryTypeId -> cb.equal(root.get("dictionaryTypeId"), dictionaryTypeId)),
                invokeSafely(params.getCode(), code -> cb.like(root.get("code"), "%" + code + "%")),
                invokeSafely(params.getName(), name -> cb.like(root.get("name"), "%" + name + "%")),
                CriteriaBuilderUtils.between(cb, root.get("createdTime"), createdTime),
                CriteriaBuilderUtils.between(cb, root.get("modifiedTime"), modifiedTime)
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DictionaryValueVO> queryPage(DictionaryValueQuery params, Pageable pageable, String... projection) {
        log.info("page query DictionaryValue: {}", params);
        pageable = PageableUtils.setDefaultSort(pageable, SORT_SERIAL_NUMBER);
        return dictionaryValueRepository.findAll((root, query, cb) -> buildPredicates(cb, root, params), pageable)
                .map(item -> BeanUtils.convert(item, DictionaryValueVO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictionaryValueVO> queryList(DictionaryValueQuery params, Pageable pageable, String... projection) {
        log.info("list query DictionaryValue: {}", params);
        return queryPage(params, pageable, projection).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public DictionaryValueVO get(DictionaryValueGet params, String... projection) {
        log.info("get DictionaryValue: {}", params);
        return dictionaryValueRepository.findOne((root, query, cb) -> CriteriaBuilderUtils.or(cb, Arrays.asList(
                        invokeSafely(params.getId(), id -> cb.equal(root.get("id"), id)),
                        invokeSafely(params.getCode(), code -> cb.equal(root.get("code"), code))
                )))
                .map(item -> BeanUtils.convert(item, DictionaryValueVO.class))
                .orElseThrow(() -> new EntityNotFoundException("Can't fount DictionaryValue by " + params))
                ;
    }

    @Override
    @Transactional
    public Integer modify(DictionaryValueModify params) {
        log.info("modify DictionaryValue: {}", params);
        DictionaryValue dictionaryValue = dictionaryValueRepository.getById(params.getId());
        BeanUtils.copyPropertiesNotEmpty(params, dictionaryValue);
        dictionaryValue.setModifierId((Long) operatorSupplier.getOperator().getId());
        dictionaryValue.setModifiedTime(LocalDateTime.now());
        dictionaryValueRepository.save(dictionaryValue);
        return 1;
    }

    @Override
    @Transactional
    public Integer delete(DictionaryValueDelete params) {
        log.info("delete DictionaryValue: {}", params);
        Optional<DictionaryValue> optional = dictionaryValueRepository.findById(params.getId());
        if (!optional.isPresent()) return 0;
        // 发布一个删除前置检查事件，字典值项服务需判断不存在记录方可删除
        DictionaryValue entity = optional.get();
        eventPublisher.publishEvent(new PayloadApplicationEvent<>(BeanUtils.convert(entity, DictionaryValueVO.class), params));
        dictionaryValueRepository.delete(entity);
        return 1;
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
        if (dictionaryValueRepository.countByDictionaryTypeId(source.getId()) > 0) {
            throw new IllegalStateException("Can't delete DictionaryType which has DictionaryValue");
        }
    }


}
