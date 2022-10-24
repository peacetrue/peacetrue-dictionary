package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 字典值项获取参数。
 *
 * @author peace
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MultiNotNull(properties = {"id", "dictionaryTypeCode", "code"})
public class DictionaryValueGet implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @Min(1)
    private Long id;
    /** 字典类型编码 */
    @Size(min = 1, max = 32)
    private String dictionaryTypeCode;
    /** 字典项值编码 */
    @Size(min = 1, max = 32)
    private String code;

}