package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 字典值项删除参数。
 *
 * @author peace
 */
@Data
@Accessors(chain = true)
public class DictionaryValueDelete implements Serializable {

    @NotNull
    @Min(1)
    private Long id;

}
