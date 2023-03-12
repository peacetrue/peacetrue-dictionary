package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 字典值项修改参数。
 *
 * @author peace
 */
@Data
@Accessors(chain = true)
@MultiNotNull(properties = {"name", "serialNumber", "remark"})
public class DictionaryValueModify implements Serializable {


    /** 主键 */
    @NotNull
    @Min(1)
    private Long id;
    /** 名称 */
    @Size(min = 1, max = 255)
    private String name;
    /** 序号 */
    @Min(1)
    @Max(Short.MAX_VALUE)
    private Integer serialNumber;
    /** 备注 */
    @Size(max = 255)
    private String remark;

}
