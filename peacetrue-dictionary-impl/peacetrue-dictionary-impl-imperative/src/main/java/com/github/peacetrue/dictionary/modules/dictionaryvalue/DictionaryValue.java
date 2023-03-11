package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.beans.createmodify.CreateModify;
import com.github.peacetrue.beans.properties.id.IdCapable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典项值实体类。
 *
 * @author peace
 */
@Getter
@Setter
@ToString
@Entity

public class DictionaryValue implements Serializable, IdCapable<Long>, CreateModify<Long, LocalDateTime> {

    

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 字典类型. 主键 */
    @Column(nullable = false)
    private Long dictionaryTypeId;
    /** 字典类型. 冗余编码方便查询 */
    @Column(nullable = false, length = 32)
    private String dictionaryTypeCode;
    /** 编码 */
    @Column(nullable = false, length = 32)
    private String code;
    /** 名称 */
    @Column(nullable = false, length = 32)
    private String name;
    /** 备注 */
    @Column(nullable = false)
    private String remark;
    /** 序号 */
    @Column(nullable = false)
    private Integer serialNumber;
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
