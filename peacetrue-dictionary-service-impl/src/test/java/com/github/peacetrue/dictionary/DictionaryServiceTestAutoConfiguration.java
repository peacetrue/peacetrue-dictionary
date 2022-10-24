package com.github.peacetrue.dictionary;

import com.github.peacetrue.spring.operator.OperatorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author peace
 */
@Configuration(proxyBeanMethods = false)
@ImportAutoConfiguration({
        R2dbcAutoConfiguration.class,
        R2dbcDataAutoConfiguration.class,
        DictionaryServiceAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        OperatorAutoConfiguration.class
})
@EnableAutoConfiguration
public class DictionaryServiceTestAutoConfiguration {

}
