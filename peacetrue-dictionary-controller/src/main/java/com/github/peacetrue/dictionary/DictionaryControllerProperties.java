package com.github.peacetrue.dictionary;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 控制器配置
 *
 * @author peace
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "peacetrue.dictionary")
public class DictionaryControllerProperties {

}