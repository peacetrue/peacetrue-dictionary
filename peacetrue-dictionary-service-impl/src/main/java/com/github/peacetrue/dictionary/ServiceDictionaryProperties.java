package com.github.peacetrue.dictionary;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author peace
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.dictionary")
public class ServiceDictionaryProperties {

}
