package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;

/**
 * 字典项值客户端
 *
 * @author peace
 */
@ReactiveFeignClient(name = "peacetrue-dictionary", url = "${peacetrue.DictionaryValue.url:${peacetrue.server.url:}}")
public interface DictionaryValueServiceClient extends DictionaryValueService {

    @Override
    @PostMapping(value = "/dictionary-values")
    Mono<DictionaryValueVO> add(DictionaryValueAdd params);

    @Override
    @GetMapping(value = "/dictionary-values", params = "page")
    Mono<Page<DictionaryValueVO>> queryPage(@Nullable @SpringQueryMap DictionaryValueQuery params, @Nullable Pageable pageable, @SpringQueryMap String... projection);

    @Override
    @GetMapping(value = "/dictionary-values", params = "sort")
    Flux<DictionaryValueVO> queryList(@SpringQueryMap DictionaryValueQuery params, @Nullable Pageable pageable, @SpringQueryMap String... projection);

    @Override
    @GetMapping(value = "/dictionary-values/get")
    Mono<DictionaryValueVO> get(@SpringQueryMap DictionaryValueGet params, @SpringQueryMap String... projection);

    @Override
    @PutMapping(value = "/dictionary-values")
    Mono<Integer> modify(DictionaryValueModify params);

    @Override
    @DeleteMapping(value = "/dictionary-values/delete")
    Mono<Integer> delete(@SpringQueryMap DictionaryValueDelete params);

}
