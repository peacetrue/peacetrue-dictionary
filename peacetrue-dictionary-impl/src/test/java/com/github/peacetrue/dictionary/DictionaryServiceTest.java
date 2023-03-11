package com.github.peacetrue.dictionary;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeVO;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;

import java.time.LocalDateTime;

/**
 * @author peace
 */
public class DictionaryServiceTest {
    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.parse("2022-10-25T00:00:00");
    public static final DictionaryTypeVO DICTIONARY_TYPE = new DictionaryTypeVO(
            1L, "sex", "性别", "", 0L, LOCAL_DATE_TIME, 0L, LOCAL_DATE_TIME, null
    );
    public static final DictionaryValueVO DICTIONARY_VALUE = new DictionaryValueVO(
            1L, DICTIONARY_TYPE.getId(), DICTIONARY_TYPE.getCode(), "man", "男", "", 1, 0L, LOCAL_DATE_TIME, 0L, LOCAL_DATE_TIME
    );
}
