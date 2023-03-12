package com.github.peacetrue.dictionary.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.reactive.DictionaryTypeService;
import com.github.peacetrue.dictionary.modules.dictionarytype.reactive.DictionaryTypeServiceImpl;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive.DictionaryValueService;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive.DictionaryValueServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据字典服务自动配置。
 *
 * @author peace
 */
@Configuration
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
