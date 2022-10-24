package com.github.peacetrue.dictionary;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据字典服务属性。
 *
 * @author peace
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.dictionary")
public class DictionaryServiceProperties {

}
