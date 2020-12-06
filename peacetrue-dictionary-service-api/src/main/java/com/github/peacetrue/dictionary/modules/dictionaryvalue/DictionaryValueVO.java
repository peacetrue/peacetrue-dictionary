package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiayx
 */
@Data
public class DictionaryValueVO implements Serializable {

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
