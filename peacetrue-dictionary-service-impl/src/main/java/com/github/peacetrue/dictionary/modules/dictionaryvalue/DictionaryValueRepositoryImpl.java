package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.IdCapable;
import com.github.peacetrue.r2dbc.PeaceR2dbcRepository;
import com.github.peacetrue.spring.data.relational.core.query.QueryUtils;
import com.github.peacetrue.spring.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author : xiayx
 * @since : 2020-12-15 07:02
 **/
@Slf4j
@Repository
public class DictionaryValueRepositoryImpl implements DictionaryValueRepository {

    @Autowired
    private PeaceR2dbcRepository r2dbcRepository;
    @Autowired
    private R2dbcEntityOperations entityOperations;
    @Autowired
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
