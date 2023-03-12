package com.github.peacetrue.dictionary.modules.dictionaryvalue;

/**
 * @author peace
 **/
public class PropertyNameConventionImpl implements PropertyNameConvention {

    @Override
    public String findCodeById(Class<?> entityClass, String idPropertyName) {
        return idPropertyName.replaceFirst("Id$", "Code");
    }

}
