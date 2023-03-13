package com.github.peacetrue.dictionary.modules.dictionarytype.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.*;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.github.peacetrue.dictionary.DictionaryConstants.DICTIONARY_TYPE_PATH;

/**
 * 字典类型客户端。
 *
 * @author peace
 */
@ReactiveFeignClient(name = "peacetrue-dictionary", url = "${peacetrue.dictionary-type.url:${peacetrue.server.url:}}", primary = false)
public interface DictionaryTypeServiceClient extends DictionaryTypeService {

    @Override
    @PostMapping(value = DICTIONARY_TYPE_PATH)
    Mono<DictionaryTypeVO> add(DictionaryTypeAdd params);

    @Override
    @GetMapping(value = DICTIONARY_TYPE_PATH, params = "page")
    Mono<Page<DictionaryTypeVO>> queryPage(@SpringQueryMap DictionaryTypeQuery params, @SpringQueryMap Pageable pageable, @SpringQueryMap String... projection);

    @Override
    @GetMapping(value = DICTIONARY_TYPE_PATH, params = "sort")
    Flux<DictionaryTypeVO> queryList(@SpringQueryMap DictionaryTypeQuery params, @SpringQueryMap Pageable pageable, @SpringQueryMap String... projection);

    @GetMapping(value = "/dictionary-types/{id}")
    Mono<DictionaryTypeVO> get(@PathVariable("id") Long id, @SpringQueryMap String... projection);

    @Override
    default Mono<DictionaryTypeVO> get(DictionaryTypeGet params, String... projection) {
        return get(params.getId(), projection);
    }

    @Override
    @PutMapping(value = DICTIONARY_TYPE_PATH)
    Mono<Integer> modify(DictionaryTypeModify params);

    @DeleteMapping(value = "/dictionary-types/{id}")
    Mono<Integer> delete(@PathVariable("id") Long id);

    @Override
    default Mono<Integer> delete(DictionaryTypeDelete params) {
        return delete(params.getId());
    }
}
