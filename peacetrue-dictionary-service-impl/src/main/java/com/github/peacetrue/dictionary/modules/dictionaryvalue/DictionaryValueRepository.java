package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.IdCapable;
import reactor.core.publisher.Mono;

/**
 * @author : xiayx
 * @since : 2020-12-15 07:02
 **/
public interface DictionaryValueRepository {

    Mono<Integer> setCodePropertyValue(IdCapable<Long> entity, String idPropertyName);

}
