package com.github.peacetrue.r2dbc;

import com.github.peacetrue.spring.data.relational.core.query.QueryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.relational.core.query.Update;
import reactor.core.publisher.Mono;

/**
 * @author peace
 **/
@Slf4j
public class R2dbcRepositoryImpl implements R2dbcRepository {

    @Autowired
    private R2dbcEntityOperations entityOperations;

    public Mono<Integer> setPropertyValue(Class<?> entityClass, Long id, String propertyName, Object propertyValue) {
        log.debug("设置属性: {}({}).{}={}", entityClass.getSimpleName(), id, propertyName, propertyValue);
        return entityOperations.update(entityClass)
                .matching(QueryUtils.id(() -> id))
                .apply(Update.update(propertyName, propertyValue))
                ;
    }


}
