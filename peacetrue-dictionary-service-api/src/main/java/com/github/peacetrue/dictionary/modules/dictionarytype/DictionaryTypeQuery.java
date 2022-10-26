package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.range.LocalDateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 字典类型查询参数。
 *
 * @author peace
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryTypeQuery implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 默认占位值，解决 null 引用问题；此值不能被篡改 */
    public static final DictionaryTypeQuery DEFAULT = new DictionaryTypeQuery();

    /** 主键 */
    private Long[] id;
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 创建时间 */
    private LocalDateRange createdTime;
    /** 最近修改时间 */
    private LocalDateRange modifiedTime;

}
