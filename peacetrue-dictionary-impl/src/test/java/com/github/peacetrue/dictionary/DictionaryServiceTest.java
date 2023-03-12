package com.github.peacetrue.dictionary;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeVO;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;

import java.time.LocalDateTime;

/**
 * @author peace
 */
public class DictionaryServiceTest {
    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.parse("2022-10-25T00:00:00");
    public static final DictionaryTypeVO DICTIONARY_TYPE = new DictionaryTypeVO()
            .setId(1L)
            .setCode("sex").setName("性别").setRemark("")
            .setCreatorId(0L)
            .setCreatedTime(LOCAL_DATE_TIME)
            .setModifierId(0L)
            .setModifiedTime(LOCAL_DATE_TIME);
    public static final DictionaryValueVO DICTIONARY_VALUE = new DictionaryValueVO()
            .setId(1L).setDictionaryTypeId(DICTIONARY_TYPE.getId())
            .setDictionaryTypeCode(DICTIONARY_TYPE.getCode())
            .setCode("man").setName("男").setRemark("")
            .setSerialNumber(1)
            .setCreatorId(0L)
            .setCreatedTime(LOCAL_DATE_TIME)
            .setModifierId(0L)
            .setModifiedTime(LOCAL_DATE_TIME);
}
