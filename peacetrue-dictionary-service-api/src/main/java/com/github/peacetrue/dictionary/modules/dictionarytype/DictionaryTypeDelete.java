package com.github.peacetrue.dictionary.modules.dictionarytype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 字典类型删除参数。
 *
 * @author peace
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryTypeDelete implements Serializable {

    private static final long serialVersionUID = 0L;

    @NotNull
    @Min(1)
    private Long id;

}
