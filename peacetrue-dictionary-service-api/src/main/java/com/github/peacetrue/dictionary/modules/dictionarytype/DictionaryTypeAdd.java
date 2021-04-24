package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.core.OperatorImpl;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueAdd;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryTypeAdd extends OperatorImpl<Long> {

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
    @Size(min = 1, max = 255)
    private String remark;
    /** 字典值集合 */
    private List<DictionaryValueAdd> dictionaryValues;

    public DictionaryTypeAdd(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public DictionaryTypeAdd(String code, String name, String remark) {
        this.code = code;
        this.name = name;
        this.remark = remark;
    }

}
