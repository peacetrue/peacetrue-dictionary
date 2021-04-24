package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiayx
 */
@Data
@EqualsAndHashCode(exclude = "dictionaryValues")
public class DictionaryTypeVO implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 主键 */
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
    /** 字典值集合 */
    private List<DictionaryValueVO> dictionaryValues;
}
