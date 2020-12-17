package com.github.peacetrue.dictionary.modules.dictionaryvalue;

/**
 * @author : xiayx
 * @since : 2020-12-15 07:26
 **/
public class PropertyNameConventionImpl implements PropertyNameConvention {

    @Override
    public String findCodeById(Class<?> entityClass, String idPropertyName) {
        return idPropertyName.replaceFirst("Id$", "Code");
    }

}
