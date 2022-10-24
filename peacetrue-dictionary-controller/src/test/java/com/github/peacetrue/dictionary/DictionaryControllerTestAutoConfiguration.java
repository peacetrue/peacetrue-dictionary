package com.github.peacetrue.dictionary;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;

/**
 * @author peace
 */
@Configuration
@ImportAutoConfiguration(classes = {
        DictionaryServiceTestAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        DictionaryControllerAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,
        ReactivePageableHandlerMethodArgumentResolver.class,
        ReactiveSortHandlerMethodArgumentResolver.class
})
@EnableAutoConfiguration
public class DictionaryControllerTestAutoConfiguration {


}
