package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.core.OperatorImpl;
import com.github.peacetrue.core.Range;
import lombok.*;


/**
 * @author peace
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryTypeQuery extends OperatorImpl<Long> {

    public static final DictionaryTypeQuery DEFAULT = new DictionaryTypeQuery();

    private static final long serialVersionUID = 0L;

    /** 主键 */
    private Long[] id;
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 创建者主键 */
    private Long creatorId;
    /** 创建时间 */
    private Range.LocalDateTime createdTime;
    /** 修改者主键 */
    private Long modifierId;
    /** 最近修改时间 */
    private Range.LocalDateTime modifiedTime;

    public DictionaryTypeQuery(Long[] id) {
        this.id = id;
    }

}
