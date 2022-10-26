package com.github.peacetrue.dictionary;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeVO;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;
import com.github.peacetrue.spring.operator.OperatorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
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
    public static final DictionaryTypeVO DICTIONARY_TYPE = new DictionaryTypeVO(
            1L, "sex", "性别", "", 0L, LOCAL_DATE_TIME, 0L, LOCAL_DATE_TIME, null
    );
    public static final DictionaryValueVO DICTIONARY_VALUE = new DictionaryValueVO(1L, DICTIONARY_TYPE.getId(), DICTIONARY_TYPE.getCode(), "man", "男", "", 1, 0L, LOCAL_DATE_TIME, 0L, LOCAL_DATE_TIME);

}
