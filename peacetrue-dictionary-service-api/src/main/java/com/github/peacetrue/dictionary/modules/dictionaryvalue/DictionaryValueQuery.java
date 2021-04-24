package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.OperatorImpl;
import com.github.peacetrue.core.Range;
import lombok.*;

import javax.validation.constraints.NotNull;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryValueQuery extends OperatorImpl<Long> {

    public static final DictionaryValueQuery DEFAULT = new DictionaryValueQuery();

    private static final long serialVersionUID = 0L;

    /** 主键 */
    private Long[] id;
    /** 字典类型. 主键 */
    private Long dictionaryTypeId;
    /** 字典类型. 冗余编码方便查询 */
    private String dictionaryTypeCode;
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 序号 */
    private Integer serialNumber;
    /** 创建者主键 */
    private Long creatorId;
    /** 创建时间 */
    private Range.LocalDateTime createdTime;
    /** 修改者主键 */
    private Long modifierId;
    /** 最近修改时间 */
    private Range.LocalDateTime modifiedTime;

    public DictionaryValueQuery(Long[] id) {
        this.id = id;
    }

    public DictionaryValueQuery(String dictionaryTypeCode) {
        this.dictionaryTypeCode = dictionaryTypeCode;
    }

}
