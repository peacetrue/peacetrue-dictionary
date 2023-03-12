package com.github.peacetrue.dictionary.modules.dictionarytype.imperative;

import com.github.peacetrue.dictionary.modules.dictionarytype.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 字典类型客户端。
 * <p>
 * primary = false:
 * org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'dictionaryTypeController': Unsatisfied dependency expressed through method 'setDictionaryTypeService' parameter 0; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.github.peacetrue.dictionary.modules.dictionarytype.imperative.DictionaryTypeService' available: more than one 'primary' bean found among candidates: [dictionaryTypeService, com.github.peacetrue.dictionary.modules.dictionarytype.imperative.DictionaryTypeServiceClient]
 *
 * @author peace
 */
@FeignClient(name = "peacetrue-dictionary", url = "${peacetrue.dictionary-type.url:${peacetrue.server.url:}}", primary = false)
public interface DictionaryTypeServiceClient extends DictionaryTypeService {

    @Override
    @PostMapping(value = "/dictionary-types")
    DictionaryTypeVO add(DictionaryTypeAdd params);

    @Override
    @GetMapping(value = "/dictionary-types")
    Page<DictionaryTypeVO> queryPage(@Nullable @SpringQueryMap DictionaryTypeQuery params, Pageable pageable, @SpringQueryMap String... projection);

    @Override
    @GetMapping(value = "/dictionary-types", params = "rtn=list")
    List<DictionaryTypeVO> queryList(@SpringQueryMap DictionaryTypeQuery params, Pageable pageable, @SpringQueryMap String... projection);

    @GetMapping(value = "/dictionary-types/{id}")
    DictionaryTypeVO get(@PathVariable("id") Long id, @SpringQueryMap String... projection);

    @Override
    default DictionaryTypeVO get(DictionaryTypeGet params, String... projection) {
        return get(params.getId(), projection);
    }

    @Override
    @PutMapping(value = "/dictionary-types")
    Integer modify(DictionaryTypeModify params);

    @DeleteMapping(value = "/dictionary-types/{id}")
    Integer delete(@PathVariable("id") Long id);

    @Override
    default Integer delete(DictionaryTypeDelete params) {
        return delete(params.getId());
    }

}
