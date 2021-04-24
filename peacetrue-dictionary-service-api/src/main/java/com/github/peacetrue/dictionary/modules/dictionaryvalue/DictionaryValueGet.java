package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.OperatorImpl;
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
public class DictionaryValueGet extends OperatorImpl<Long> {

    private static final long serialVersionUID = 0L;

    private Long id;

    @Size(min = 1, max = 32)
    private String dictionaryTypeCode;
    @Size(min = 1, max = 32)
    private String code;

    public DictionaryValueGet(Long id) {
        this.id = id;
    }

    public DictionaryValueGet(String dictionaryTypeCode, String code) {
        this.dictionaryTypeCode = dictionaryTypeCode;
        this.code = code;
    }
}
