package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryValueModify extends OperatorCapableImpl<Long> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @NotNull
    private Long id;
    /** 字典类型. 主键 */
    private Long dictionaryTypeId;
    /** 字典类型. 冗余编码方便查询 */
    @Size(min = 1, max = 32)
    private String dictionaryTypeCode;
    /** 编码 */
    @Size(min = 1, max = 32)
    private String code;
    /** 名称 */
    @Size(min = 1, max = 255)
    private String name;
    /** 序号 */
    private Integer serialNumber;
    /** 备注 */
    @Size(min = 1, max = 255)
    private String remark;

}
