package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.beans.properties.id.IdCapable;
import reactor.core.publisher.Mono;

/**
 * 字典值项资源库接口。
 *
 * @author peace
 **/
public interface DictionaryValueRepository {

    Mono<Integer> setCodePropertyValue(IdCapable<Long> entity, String idPropertyName);

}
