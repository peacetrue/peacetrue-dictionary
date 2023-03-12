package com.github.peacetrue.dictionary.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeVO;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;
import com.github.peacetrue.dictionary.reactive.DictionaryServiceAutoConfiguration;
import com.github.peacetrue.spring.operator.OperatorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author peace
 */
@Configuration
@ImportAutoConfiguration({
        R2dbcAutoConfiguration.class,
        R2dbcDataAutoConfiguration.class,
        R2dbcTransactionManagerAutoConfiguration.class,
        DictionaryServiceAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        OperatorAutoConfiguration.class
})
@EnableAutoConfiguration
public class DictionaryServiceTestAutoConfiguration {
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
