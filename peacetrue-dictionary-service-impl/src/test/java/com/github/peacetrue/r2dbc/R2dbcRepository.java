package com.github.peacetrue.r2dbc;

import com.github.peacetrue.beans.properties.id.IdCapable;
import reactor.core.publisher.Mono;

/**
 * @author peace
 *
 **/
public interface R2dbcRepository {

    Mono<Integer> setPropertyValue(Class<?> entityClass, Long id, String propertyName, Object propertyValue);

    default Mono<Integer> setPropertyValue(IdCapable<Long> entity, String propertyName, Object propertyValue) {
        return this.setPropertyValue(entity.getClass(), entity.getId(), propertyName, propertyValue);
    }
}
