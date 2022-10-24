package com.github.peacetrue.dictionary;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

/**
 * 数据字典服务自动配置。
 *
 * @author peace
 */
@Configuration
@EnableConfigurationProperties(DictionaryServiceProperties.class)
@ComponentScan(basePackageClasses = DictionaryServiceAutoConfiguration.class)
@PropertySource("classpath:/application-dictionary-service.yml")
public class DictionaryServiceAutoConfiguration {

    private DictionaryServiceProperties properties;

    public DictionaryServiceAutoConfiguration(DictionaryServiceProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

}
