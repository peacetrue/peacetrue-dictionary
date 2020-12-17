package com.github.peacetrue.dictionary.modules.dictionaryvalue;

/**
 * @author : xiayx
 * @since : 2020-12-15 07:24
 **/
public interface PropertyNameConvention {

    String findCodeById(Class<?> entityClass, String idPropertyName);

}
