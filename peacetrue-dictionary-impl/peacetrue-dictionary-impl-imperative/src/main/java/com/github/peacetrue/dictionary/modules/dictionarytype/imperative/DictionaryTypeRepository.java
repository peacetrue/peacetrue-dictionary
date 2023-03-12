package com.github.peacetrue.dictionary.modules.dictionarytype.imperative;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * 字典类型资源库。
 *
 * @author peace
 **/
public interface DictionaryTypeRepository extends JpaRepository<DictionaryType, Long>, JpaSpecificationExecutor<DictionaryType> {

    /**
     * 根据主键查询编码。
     *
     * @param id 主键
     * @return 编码
     */
    @Query("select code from #{#entityName} where id=?1")
    Optional<String> findCodeById(Long id);

    /**
     * 根据主键查询编码。
     *
     * @param id 主键
     * @return 编码
     */
    default String findRequiredCodeById(Long id) {
        return findCodeById(id).orElseThrow(() -> new EntityNotFoundException("Can't found DictionaryType with id " + id));
    }
}
