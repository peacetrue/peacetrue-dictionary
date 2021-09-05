package com.github.peacetrue.dictionary.modules.dictionarytype;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;

/**
 * 字典类型客户端
 *
 * @author peace
 */
@ReactiveFeignClient(name = "peacetrue-dictionary", url = "${peacetrue.DictionaryType.url:${peacetrue.server.url:}}")
public interface DictionaryTypeServiceClient {

    @PostMapping(value = "/dictionary-types")
    Mono<DictionaryTypeVO> add(DictionaryTypeAdd params);

    @GetMapping(value = "/dictionary-types", params = "page")
    Mono<Page<DictionaryTypeVO>> query(@Nullable @SpringQueryMap DictionaryTypeQuery params, @Nullable Pageable pageable, @SpringQueryMap String... projection);

    @GetMapping(value = "/dictionary-types", params = "sort")
    Flux<DictionaryTypeVO> query(@SpringQueryMap DictionaryTypeQuery params, Sort sort, @SpringQueryMap String... projection);

    @GetMapping(value = "/dictionary-types")
    Flux<DictionaryTypeVO> query(@SpringQueryMap DictionaryTypeQuery params, @SpringQueryMap String... projection);

    @GetMapping(value = "/dictionary-types/get")
    Mono<DictionaryTypeVO> get(@SpringQueryMap DictionaryTypeGet params, @SpringQueryMap String... projection);

    @PutMapping(value = "/dictionary-types")
    Mono<Integer> modify(DictionaryTypeModify params);

    @DeleteMapping(value = "/dictionary-types/delete")
    Mono<Integer> delete(@SpringQueryMap DictionaryTypeDelete params);

}
