package com.github.peacetrue.dictionary.imperative;

import com.github.peacetrue.dictionary.imperative.DictionaryServiceAutoConfiguration;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeVO;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;
import com.github.peacetrue.spring.operator.OperatorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author peace
 */
@Configuration
@ImportAutoConfiguration({
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        TransactionAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        ValidationAutoConfiguration.class,
        OperatorAutoConfiguration.class,
        DictionaryServiceAutoConfiguration.class,
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
