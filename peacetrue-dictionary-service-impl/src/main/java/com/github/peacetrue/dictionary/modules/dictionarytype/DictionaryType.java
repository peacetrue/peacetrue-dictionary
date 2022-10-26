package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.beans.createmodify.CreateModify;
import com.github.peacetrue.beans.properties.id.IdCapable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典类型实体类。
 *
 * @author peace
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryType implements Serializable, IdCapable<Long>, CreateModify<Long, LocalDateTime> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 编码 */
    @Column(nullable = false, length = 32)
    private String code;
    /** 名称 */
    @Column(nullable = false, length = 32)
    private String name;
    /** 备注 */
    @Column(nullable = false)
    private String remark;
    /** 创建者主键 */
    @Column(nullable = false, updatable = false)
    private Long creatorId;
    /** 创建时间 */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;
    /** 修改者主键 */
    @Column(nullable = false)
    private Long modifierId;
    /** 最近修改时间 */
    @Column(nullable = false)
    private LocalDateTime modifiedTime;

}
