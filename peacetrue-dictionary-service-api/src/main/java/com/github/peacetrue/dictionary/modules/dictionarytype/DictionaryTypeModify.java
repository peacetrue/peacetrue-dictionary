package com.github.peacetrue.dictionary.modules.dictionarytype;

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
public class DictionaryTypeModify extends OperatorCapableImpl<Long> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @NotNull
    private Long id;
    /** 编码 */
    @NotNull
    @Size(min = 1, max = 32)
    private String code;
    /** 名称 */
    @NotNull
    @Size(min = 1, max = 32)
    private String name;
    /** 备注 */
    @NotNull
    @Size(min = 1, max = 255)
    private String remark;

}
