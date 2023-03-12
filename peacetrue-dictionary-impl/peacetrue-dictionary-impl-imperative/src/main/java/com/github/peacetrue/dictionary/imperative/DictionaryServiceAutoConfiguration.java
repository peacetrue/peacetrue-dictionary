package com.github.peacetrue.dictionary.imperative;

import com.github.peacetrue.dictionary.modules.dictionarytype.imperative.DictionaryTypeService;
import com.github.peacetrue.dictionary.modules.dictionarytype.imperative.DictionaryTypeServiceImpl;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.imperative.DictionaryValueService;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.imperative.DictionaryValueServiceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 数据字典服务自动配置。
 *
 * @author peace
 */
@Configuration
@EntityScan(basePackages = "com.github.peacetrue.dictionary")
@EnableJpaRepositories(basePackages = "com.github.peacetrue.dictionary")
public class DictionaryServiceAutoConfiguration {
    @Bean
    public DictionaryTypeService dictionaryTypeService() {
        return new DictionaryTypeServiceImpl();
    }

    @Bean
    public DictionaryValueService dictionaryValueService() {
        return new DictionaryValueServiceImpl();
    }
}
