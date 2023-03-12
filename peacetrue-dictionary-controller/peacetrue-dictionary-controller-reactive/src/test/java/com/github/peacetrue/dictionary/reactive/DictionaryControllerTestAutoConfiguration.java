package com.github.peacetrue.dictionary.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.reactive.DictionaryTypeService;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive.DictionaryValueService;
import com.github.peacetrue.dictionary.reactive.DictionaryControllerAutoConfiguration;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;

/**
 * @author peace
 */
@Configuration
@ImportAutoConfiguration(classes = {
        WebMvcAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        DictionaryControllerAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,
        ReactivePageableHandlerMethodArgumentResolver.class,
        ReactiveSortHandlerMethodArgumentResolver.class
})
@EnableAutoConfiguration
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
