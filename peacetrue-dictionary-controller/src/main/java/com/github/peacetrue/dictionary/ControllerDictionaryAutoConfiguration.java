package com.github.peacetrue.dictionary;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @author peace
 */
@Configuration
@EnableConfigurationProperties(ControllerDictionaryProperties.class)
@ComponentScan(basePackageClasses = ControllerDictionaryAutoConfiguration.class)
@PropertySource("classpath:/application-dictionary-controller.yml")
public class ControllerDictionaryAutoConfiguration {

}
