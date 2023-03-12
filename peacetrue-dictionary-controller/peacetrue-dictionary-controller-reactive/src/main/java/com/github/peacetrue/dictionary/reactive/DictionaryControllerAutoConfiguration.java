package com.github.peacetrue.dictionary.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.reactive.DictionaryTypeController;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive.DictionaryValueController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 数据字典控制器自动配置。
 *
 * @author peace
 */
@Configuration
public class DictionaryControllerAutoConfiguration {

    @Bean
    public DictionaryTypeController dictionaryTypeController() {
        return new DictionaryTypeController();
    }

    @Bean
    public DictionaryValueController dictionaryValueController() {
        return new DictionaryValueController();
    }
}
