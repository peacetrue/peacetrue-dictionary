package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 字典项值控制器
 *
 * @author peace
 */
@Slf4j
@RestController
@RequestMapping(value = "/dictionary-values")
public class DictionaryValueController {

    @Autowired
    private DictionaryValueService dictionaryValueService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<DictionaryValueVO> addByForm(DictionaryValueAdd params) {
        log.info("新增字典项值信息(请求方法+表单参数)[{}]", params);
        return dictionaryValueService.add(params);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DictionaryValueVO> addByJson(@RequestBody DictionaryValueAdd params) {
        log.info("新增字典项值信息(请求方法+JSON参数)[{}]", params);
        return dictionaryValueService.add(params);
    }

    @GetMapping(params = "page")
    public Mono<Page<DictionaryValueVO>> query(DictionaryValueQuery params, Pageable pageable, String... projection) {
        log.info("分页查询字典项值信息(请求方法+参数变量)[{}]", params);
        return dictionaryValueService.query(params, pageable, projection);
    }

    @GetMapping
    public Flux<DictionaryValueVO> query(DictionaryValueQuery params, Sort sort, String... projection) {
        log.info("全量查询字典项值信息(请求方法+参数变量)[{}]", params);
        return dictionaryValueService.query(params, sort, projection);
    }

    @GetMapping("/{id}")
    public Mono<DictionaryValueVO> getByUrlPathVariable(@PathVariable Long id, String... projection) {
        log.info("获取字典项值信息(请求方法+路径变量)详情[{}]", id);
        return dictionaryValueService.get(new DictionaryValueGet(id), projection);
    }

    @RequestMapping("/get")
    public Mono<DictionaryValueVO> getByPath(DictionaryValueGet params, String... projection) {
        log.info("获取字典项值信息(请求路径+参数变量)详情[{}]", params);
        return dictionaryValueService.get(params, projection);
    }

    @PutMapping(value = {"", "/*"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<Integer> modifyByForm(DictionaryValueModify params) {
        log.info("修改字典项值信息(请求方法+表单参数)[{}]", params);
        return dictionaryValueService.modify(params);
    }

    @PutMapping(value = {"", "/*"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> modifyByJson(@RequestBody DictionaryValueModify params) {
        log.info("修改字典项值信息(请求方法+JSON参数)[{}]", params);
        return dictionaryValueService.modify(params);
    }

    @DeleteMapping("/{id}")
    public Mono<Integer> deleteByUrlPathVariable(@PathVariable Long id) {
        log.info("删除字典项值信息(请求方法+URL路径变量)[{}]", id);
        return dictionaryValueService.delete(new DictionaryValueDelete(id));
    }

    @DeleteMapping(params = "id")
    public Mono<Integer> deleteByUrlParamVariable(DictionaryValueDelete params) {
        log.info("删除字典项值信息(请求方法+URL参数变量)[{}]", params);
        return dictionaryValueService.delete(params);
    }

    @RequestMapping(path = "/delete")
    public Mono<Integer> deleteByPath(DictionaryValueDelete params) {
        log.info("删除字典项值信息(请求路径+URL参数变量)[{}]", params);
        return dictionaryValueService.delete(params);
    }


}
