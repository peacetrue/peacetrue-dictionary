package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueAdd;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 字典类型新增参数。
 *
 * @author peace
 */
@Data
@Accessors(chain = true)

public class DictionaryTypeAdd implements Serializable {

    /** 编码 */
    @NotNull
    @Size(min = 1, max = 32)
    private String code;
    /** 名称 */
    @NotNull
    @Size(min = 1, max = 32)
    private String name;
    /** 备注 */
    @Size(max = 255)
    private String remark;
    /** 字典值集合 */
    @Valid
    private transient List<DictionaryValueAdd> dictionaryValues;

}
