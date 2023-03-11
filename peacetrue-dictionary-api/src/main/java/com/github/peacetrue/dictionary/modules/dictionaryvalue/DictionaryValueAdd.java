package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 字典值项新增参数。
 *
 * @author peace
 */
@Data
@Accessors(chain = true)
public class DictionaryValueAdd implements Serializable {

    /** 字典类型. 主键 */
    @NotNull
    @Min(1)
    private Long dictionaryTypeId;
    /** 编码 */
    @NotNull
    @Size(min = 1, max = 32)
    private String code;
    /** 名称 */
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    /** 序号 */
    @Min(1)
    @Max(Short.MAX_VALUE)
    private Integer serialNumber;
    /** 备注 */
    @Size(min = 1, max = 255)
    private String remark;

}
