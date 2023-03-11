package com.github.peacetrue.dictionary;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryType;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeVO;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValue;
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
import java.util.Arrays;
import java.util.List;

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
    public static final DictionaryTypeVO DICTIONARY_TYPE = new DictionaryTypeVO(
            1L, "sex", "性别", "", 0L, LOCAL_DATE_TIME, 0L, LOCAL_DATE_TIME, null
    );
    public static final DictionaryValueVO DICTIONARY_VALUE = new DictionaryValueVO(1L, DICTIONARY_TYPE.getId(), DICTIONARY_TYPE.getCode(), "man", "男", "", 1, 0L, LOCAL_DATE_TIME, 0L, LOCAL_DATE_TIME);
}
