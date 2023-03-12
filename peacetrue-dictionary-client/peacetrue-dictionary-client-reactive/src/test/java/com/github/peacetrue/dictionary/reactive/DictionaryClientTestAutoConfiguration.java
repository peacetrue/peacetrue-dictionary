package com.github.peacetrue.dictionary.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.reactive.DictionaryTypeService;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive.DictionaryValueService;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactivefeign.spring.config.ReactiveFeignAutoConfiguration;

/**
 * @author peace
 **/
@Configuration
@ImportAutoConfiguration({
        ReactiveFeignAutoConfiguration.class,
        DictionaryControllerAutoConfiguration.class,
        WebClientAutoConfiguration.class,
        WebFluxAutoConfiguration.class,
        HttpHandlerAutoConfiguration.class,
        ReactiveWebServerFactoryAutoConfiguration.class,
})
//@EnableFeignClients(basePackages = "com.github.peacetrue.dictionary")
@EnableReactiveFeignClients(basePackages = "com.github.peacetrue.dictionary")
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

    @Bean
    public WebFluxConfigurer pageableWebFluxConfigurer() {
        return new WebFluxConfigurer() {
            @Override
            public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
                configurer.addCustomResolver(new ReactivePageableHandlerMethodArgumentResolver());
                configurer.addCustomResolver(new ReactiveSortHandlerMethodArgumentResolver());
            }
        };
    }

}
