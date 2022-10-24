package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.beans.properties.id.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典值项视图。
 *
 * @author peace
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryValueVO implements Serializable, Id<Long> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    private Long id;
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
    /** 备注 */
    private String remark;
    /** 创建者主键 */
    private Long creatorId;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 修改者主键 */
    private Long modifierId;
    /** 最近修改时间 */
    private LocalDateTime modifiedTime;
}
