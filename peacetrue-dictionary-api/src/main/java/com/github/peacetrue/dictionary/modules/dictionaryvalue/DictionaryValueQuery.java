package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.range.LocalDateRange;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 字典项值查询参数。
 *
 * @author peace
 */
@Data
@Accessors(chain = true)

public class DictionaryValueQuery implements Serializable {

    /** 默认占位值，解决 null 引用问题；此值不能被篡改 */
    public static final DictionaryValueQuery DEFAULT = new DictionaryValueQuery();

    

    /** 主键 */
    private Long[] id;
    /** 字典类型. 主键 */
    private Long dictionaryTypeId;
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 创建时间 */
    private LocalDateRange createdTime;
    /** 最近修改时间 */
    private LocalDateRange modifiedTime;

}
