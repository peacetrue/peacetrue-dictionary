package com.github.peacetrue.r2dbc;

import com.github.peacetrue.core.IdCapable;
import reactor.core.publisher.Mono;

/**
 * @author : xiayx
 * @since : 2020-12-15 07:04
 **/
public interface PeaceR2dbcRepository {

    Mono<Integer> setPropertyValue(Class<?> entityClass, Long id, String propertyName, Object propertyValue);

    default Mono<Integer> setPropertyValue(IdCapable<Long> entity, String propertyName, Object propertyValue) {
        return this.setPropertyValue(entity.getClass(), entity.getId(), propertyName, propertyValue);
    }
}
