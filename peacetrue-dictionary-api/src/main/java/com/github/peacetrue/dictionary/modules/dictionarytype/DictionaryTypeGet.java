package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 字典类型获取参数。
 *
 * @author peace
 */
@Data
@Accessors(chain = true)@MultiNotNull(properties = {"id", "code"})
public class DictionaryTypeGet implements Serializable {

    /** 主键 */
    @Min(1)
    private Long id;
    /** 编码 */
    @Size(min = 1, max = 32)
    private String code;

}
