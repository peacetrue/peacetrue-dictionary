package com.github.peacetrue.dictionary.modules.dictionaryvalue;

/**
 * @author peace
 * @since 1.0.0
 **/
public interface PropertyNameConvention {

    String findCodeById(Class<?> entityClass, String idPropertyName);

}
