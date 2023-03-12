package com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive;

import com.github.peacetrue.dictionary.modules.dictionaryvalue.*;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 字典项值客户端。
 *
 * @author peace
 */
@ReactiveFeignClient(name = "peacetrue-dictionary", url = "${peacetrue.dictionary-value.url:${peacetrue.server.url:}}", primary = false)
public interface DictionaryValueServiceClient extends DictionaryValueService {

    @Override
    @PostMapping(value = "/dictionary-values")
    Mono<DictionaryValueVO> add(DictionaryValueAdd params);

    @Override
    @GetMapping(value = "/dictionary-values", params = "page")
    Mono<Page<DictionaryValueVO>> queryPage(@SpringQueryMap DictionaryValueQuery params, Pageable pageable, @SpringQueryMap String... projection);

    @Override
    @GetMapping(value = "/dictionary-values", params = "sort")
    Flux<DictionaryValueVO> queryList(@SpringQueryMap DictionaryValueQuery params, Pageable pageable, @SpringQueryMap String... projection);

    @GetMapping(value = "/dictionary-values/{id}")
    Mono<DictionaryValueVO> get(@PathVariable("id") Long id, @SpringQueryMap String... projection);

    @Override
    default Mono<DictionaryValueVO> get(DictionaryValueGet params, String... projection) {
        return get(params.getId(), projection);
    }

    @Override
    @PutMapping(value = "/dictionary-values")
    Mono<Integer> modify(DictionaryValueModify params);

    @DeleteMapping(value = "/dictionary-values/{id}")
    Mono<Integer> delete(@PathVariable("id") Long id);

    @Override
    default Mono<Integer> delete(DictionaryValueDelete params) {
        return delete(params.getId());
    }
}
