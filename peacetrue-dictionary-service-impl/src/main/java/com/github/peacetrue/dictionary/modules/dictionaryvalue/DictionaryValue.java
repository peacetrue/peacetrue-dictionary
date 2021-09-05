package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.core.IdCapable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典项值实体类
 *
 * @author peace
 */
@Getter
@Setter
@ToString
public class DictionaryValue implements Serializable, IdCapable<Long> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @Id
    private Long id;
    /** 字典类型. 主键 */
    private Long dictionaryTypeId;
    /** 字典类型. 冗余编码方便查询 */
    private String dictionaryTypeCode;
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 备注 */
    private String remark;
    /** 序号 */
    private Integer serialNumber;
    /** 创建者主键 */
    private Long creatorId;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 修改者主键 */
    private Long modifierId;
    /** 最近修改时间 */
    private LocalDateTime modifiedTime;

}
