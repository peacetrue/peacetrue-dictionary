package com.github.peacetrue.dictionary;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.dictionary")
public class ServiceDictionaryProperties {

}
