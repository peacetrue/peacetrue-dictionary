package com.github.peacetrue.dictionary.modules.dictionaryvalue.imperative;

import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValue;
import com.github.peacetrue.lang.ObjectUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Nullable;

/**
 * 字典项值资源库。
 *
 * @author peace
 **/
public interface DictionaryValueRepository extends JpaRepository<DictionaryValue, Long>, JpaSpecificationExecutor<DictionaryValue> {

    /**
     * 获取下一个可用的需要。
     *
     * @param dictionaryTypeId 字典类型主键
     * @return 下一个序号
     */
    default Integer findNextSerialNumber(Long dictionaryTypeId) {
        return ObjectUtils.defaultIfNull(findMaxSerialNumber(dictionaryTypeId), 0) + 1;
    }

    /**
     * 查询指定字典类型下字典项值的最大的序号。
     *
     * @param dictionaryTypeId 字典类型主键
     * @return 最大的序号
     */
    @Nullable
    @Query("select max(serialNumber) from DictionaryValue where dictionaryTypeId=?1")
    Integer findMaxSerialNumber(Long dictionaryTypeId);

    /**
     * 查询指定字典类型下字典项值的数目。
     *
     * @param dictionaryTypeId 字典类型主键
     * @return 字典项值的数目
     */
    Long countByDictionaryTypeId(Long dictionaryTypeId);
}
