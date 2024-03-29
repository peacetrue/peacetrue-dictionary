package com.github.peacetrue.dictionary.modules.dictionaryvalue.imperative;

import com.github.peacetrue.dictionary.modules.dictionaryvalue.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

import static com.github.peacetrue.dictionary.DictionaryConstants.DICTIONARY_VALUE_PATH;

/**
 * 字典项值客户端。
 *
 * @author peace
 */
@FeignClient(name = "peacetrue-dictionary", url = "${peacetrue.dictionary-value.url:${peacetrue.server.url:}}", primary = false)
public interface DictionaryValueServiceClient extends DictionaryValueService {

    @Override
    @PostMapping(value = DICTIONARY_VALUE_PATH)
    DictionaryValueVO add(DictionaryValueAdd params);

    @Override
    @GetMapping(value = DICTIONARY_VALUE_PATH, params = "page")
    Page<DictionaryValueVO> queryPage(@Nullable @SpringQueryMap DictionaryValueQuery params, @Nullable Pageable pageable, @SpringQueryMap String... projection);

    @Override
    @GetMapping(value = DICTIONARY_VALUE_PATH, params = "sort")
    List<DictionaryValueVO> queryList(@SpringQueryMap DictionaryValueQuery params, @Nullable Pageable pageable, @SpringQueryMap String... projection);

    @GetMapping(value = DICTIONARY_VALUE_PATH + "/{id}")
    DictionaryValueVO get(@PathVariable("id") Long id, @SpringQueryMap String... projection);

    @Override
    default DictionaryValueVO get(DictionaryValueGet params, String... projection) {
        return get(params.getId(), projection);
    }

    @Override
    @PutMapping(value = DICTIONARY_VALUE_PATH)
    Integer modify(DictionaryValueModify params);

    @DeleteMapping(value = DICTIONARY_VALUE_PATH + "/{id}")
    Integer delete(@PathVariable("id") Long id);

    @Override
    default Integer delete(DictionaryValueDelete params) {
        return delete(params.getId());
    }

}
