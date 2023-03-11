package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.beans.properties.id.IdCapable;
import com.github.peacetrue.r2dbc.R2dbcRepository;
import com.github.peacetrue.spring.beans.BeanUtils;
import com.github.peacetrue.spring.data.relational.core.query.QueryUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import reactor.core.publisher.Mono;

/**
 * 字典值项资源库实现。
 *
 * @author peace
 **/
@Slf4j
//@Repository
@AllArgsConstructor
public class DictionaryValueRepositoryImpl implements DictionaryValueRepository {

    private R2dbcRepository r2dbcRepository;
    private R2dbcEntityOperations entityOperations;
    private PropertyNameConvention propertyNameConvention;

    public Mono<Integer> setCodePropertyValue(IdCapable<Long> entity, String idPropertyName) {
        log.debug("设置[{}({}).{}]的字典编码值", entity.getClass().getSimpleName(), entity.getId(), idPropertyName);
        Long propertyValue = (Long) BeanUtils.getPropertyValue(entity, idPropertyName);
        return entityOperations.selectOne(QueryUtils.id(() -> propertyValue), DictionaryValue.class)
                .flatMap(vo -> {
                    String codePropertyName = propertyNameConvention.findCodeById(entity.getClass(), idPropertyName);
                    return r2dbcRepository.setPropertyValue(entity, codePropertyName, vo.getCode());
                });
    }

}
