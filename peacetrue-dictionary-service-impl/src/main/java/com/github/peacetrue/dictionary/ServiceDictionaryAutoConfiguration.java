package com.github.peacetrue.dictionary;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryType;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.PropertyNameConvention;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.PropertyNameConventionImpl;
import com.github.peacetrue.r2dbc.PeaceR2dbcRepository;
import com.github.peacetrue.r2dbc.PeaceR2dbcRepositoryImpl;
import com.github.peacetrue.spring.data.relational.TableSchemaInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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

    @Bean
    public PeaceR2dbcRepository r2dbcRepository() {
        return new PeaceR2dbcRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(PropertyNameConvention.class)
    public PropertyNameConvention propertyNameConvention() {
        return new PropertyNameConventionImpl();
    }

    @Bean
    public TableSchemaInitializer dictionaryTableSchemaInitializer() {
        return new TableSchemaInitializer(DictionaryType.class, "/schema-dictionary-mysql.sql");
    }
}
