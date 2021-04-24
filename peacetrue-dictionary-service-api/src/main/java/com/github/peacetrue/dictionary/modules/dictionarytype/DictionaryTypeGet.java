package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.core.OperatorImpl;
import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.*;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MultiNotNull(propertyNames = {"id", "code"})
public class DictionaryTypeGet extends OperatorImpl<Long> {

    private static final long serialVersionUID = 0L;

    private Long id;
    private String code;

    public DictionaryTypeGet(Long id) {
        this.id = id;
    }

    public DictionaryTypeGet(String code) {
        this.code = code;
    }
}
