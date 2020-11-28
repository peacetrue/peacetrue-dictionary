package com.github.peacetrue.dictionary;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ServiceDictionaryProperties.class)
@ComponentScan(basePackageClasses = ServiceDictionaryAutoConfiguration.class)
@PropertySource("classpath:/application-dictionary-service.yml")
public class ServiceDictionaryAutoConfiguration {

    private ServiceDictionaryProperties properties;

    public ServiceDictionaryAutoConfiguration(ServiceDictionaryProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

}
