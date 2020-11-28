package com.github.peacetrue.dictionary.modules.dictionarytype;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典类型实体类
 *
 * @author xiayx
 */
@Getter
@Setter
@ToString
public class DictionaryType implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @Id
    private Long id;
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
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
