package com.github.peacetrue.dictionary.modules.dictionarytype;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 字典类型删除参数。
 *
 * @author peace
 */
@Data
@Accessors(chain = true)
public class DictionaryTypeDelete implements Serializable {

    @NotNull
    @Min(1)
    private Long id;

}
