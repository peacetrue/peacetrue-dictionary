package com.github.peacetrue.dictionary.modules.dictionaryvalue;

/**
 * @author peace
 * @since 1.0.0
 **/
public class PropertyNameConventionImpl implements PropertyNameConvention {

    @Override
    public String findCodeById(Class<?> entityClass, String idPropertyName) {
        return idPropertyName.replaceFirst("Id$", "Code");
    }

}
