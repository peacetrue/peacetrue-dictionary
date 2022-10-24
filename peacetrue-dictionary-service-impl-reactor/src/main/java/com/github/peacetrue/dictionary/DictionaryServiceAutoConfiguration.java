package com.github.peacetrue.dictionary;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 数据字典服务自动配置。
 *
 * @author peace
 */
@Configuration
@ComponentScan(basePackageClasses = DictionaryServiceAutoConfiguration.class)
public class DictionaryServiceAutoConfiguration {

}
