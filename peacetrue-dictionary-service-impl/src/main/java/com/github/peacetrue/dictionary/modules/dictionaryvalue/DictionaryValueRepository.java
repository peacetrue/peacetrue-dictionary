package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.IdCapable;
import reactor.core.publisher.Mono;

/**
 * @author peace
 * @since 1.0.0
 **/
public interface DictionaryValueRepository {

    Mono<Integer> setCodePropertyValue(IdCapable<Long> entity, String idPropertyName);

}
