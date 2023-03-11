package com.github.peacetrue.dictionary;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeService;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueService;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;

/**
 * @author peace
 */
@Configuration
@ImportAutoConfiguration(classes = {
        WebMvcAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,//支持 Pageable / Sort
        JacksonAutoConfiguration.class,
        DictionaryControllerAutoConfiguration.class,
})
public class DictionaryControllerTestAutoConfiguration {

    @Bean
    @Primary
    public DictionaryTypeService dictionaryTypeService() {
        return Mockito.mock(DictionaryTypeService.class);
    }

    @Bean
    @Primary
    public DictionaryValueService dictionaryValueService() {
        return Mockito.mock(DictionaryValueService.class);
    }

}
