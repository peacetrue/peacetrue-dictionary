package com.github.peacetrue.dictionary.modules.dictionaryvalue;

/**
 * @author peace
 **/
public interface PropertyNameConvention {

    String findCodeById(Class<?> entityClass, String idPropertyName);

}
