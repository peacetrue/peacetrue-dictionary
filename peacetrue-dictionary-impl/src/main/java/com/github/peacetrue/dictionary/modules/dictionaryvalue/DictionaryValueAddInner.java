package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeAdd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 调用接口时，传入字典类型主键，
 * 再通过字典类型主键获取字典类型编码，
 * 避免产生字典类型主键和字典类型编码数据不一致的情况。
 * <p>
 * 内部使用时可以同时传递字典类型主键和字典类型编码，避免二次查询。
 *
 * @author peace
 * @see DictionaryTypeAdd#getDictionaryValues()
 **/
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DictionaryValueAddInner extends DictionaryValueAdd {

    /** 字典类型编码 */
    @NotNull
    @Size(min = 1, max = 32)
    private String dictionaryTypeCode;

}
