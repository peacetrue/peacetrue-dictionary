package com.github.peacetrue.dictionary;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 数据字典服务自动配置。
 *
 * @author peace
 */
@Configuration
@ComponentScan(basePackageClasses = DictionaryServiceAutoConfiguration.class)
@EntityScan(basePackageClasses = DictionaryServiceAutoConfiguration.class)
@EnableJpaRepositories(basePackageClasses = DictionaryServiceAutoConfiguration.class)
public class DictionaryServiceAutoConfiguration {

}
