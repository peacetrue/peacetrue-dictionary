package com.github.peacetrue.dictionary.imperative;

import com.github.peacetrue.dictionary.modules.dictionarytype.imperative.DictionaryTypeService;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.imperative.DictionaryValueService;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

/**
 * @author peace
 **/
@Configuration
@ImportAutoConfiguration({
        FeignAutoConfiguration.class,
        DictionaryControllerAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        ServletWebServerFactoryAutoConfiguration.class,
})
@EnableFeignClients(basePackages = "com.github.peacetrue.dictionary")
public class DictionaryClientTestAutoConfiguration {

    //You are seeing this disclaimer because Mockito is configured to create inlined mocks.
    //You can learn about inline mocks and their limitations under item #39 of the Mockito class javadoc.
    // Service 上注解 @NotNull 没找到
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
