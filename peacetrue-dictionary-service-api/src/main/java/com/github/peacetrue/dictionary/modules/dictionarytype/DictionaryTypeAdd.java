package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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
public class DictionaryTypeAdd extends OperatorCapableImpl<Long> {

    private static final long serialVersionUID = 0L;

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
