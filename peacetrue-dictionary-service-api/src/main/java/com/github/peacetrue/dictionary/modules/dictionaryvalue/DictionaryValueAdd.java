package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
public class DictionaryValueAdd extends OperatorCapableImpl<Long> {

    private static final long serialVersionUID = 0L;

    /** 字典类型. 主键 */
    @NotNull
    private Long dictionaryTypeId;
//    /** 字典类型. 冗余编码方便查询 */
//    @NotNull
//    @Size(min = 1, max = 32)
//    private String dictionaryTypeCode;
    /** 编码 */
    @NotNull
    @Size(min = 1, max = 32)
    private String code;
    /** 名称 */
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
//    /** 序号 */
//    @NotNull
//    private Integer serialNumber;
    /** 备注 */
    @NotNull
    @Size(min = 1, max = 255)
    private String remark;

}
