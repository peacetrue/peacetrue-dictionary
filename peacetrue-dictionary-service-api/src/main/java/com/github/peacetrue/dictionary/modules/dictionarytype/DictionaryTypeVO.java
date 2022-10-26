package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.beans.properties.id.Id;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典类型视图。
 *
 * @author peace
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryTypeVO implements Serializable, Id<Long> {

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
