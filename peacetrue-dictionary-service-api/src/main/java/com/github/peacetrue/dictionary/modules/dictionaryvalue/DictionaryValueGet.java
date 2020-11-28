package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.OperatorCapableImpl;
import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.*;

import javax.validation.constraints.Size;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MultiNotNull(propertyNames = {"id", "code"})
public class DictionaryValueGet extends OperatorCapableImpl<Long> {

    private static final long serialVersionUID = 0L;

    private Long id;
    @Size(min = 1, max = 32)
    private String code;

    public DictionaryValueGet(Long id) {
        this.id = id;
    }
}
