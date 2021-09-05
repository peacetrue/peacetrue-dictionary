package com.github.peacetrue.dictionary.modules.dictionarytype;

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
 * 字典类型控制器
 *
 * @author peace
 */
@Slf4j
@RestController
@RequestMapping(value = "/dictionary-types")
public class DictionaryTypeController {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<DictionaryTypeVO> addByForm(DictionaryTypeAdd params) {
        log.info("新增字典类型信息(请求方法+表单参数)[{}]", params);
        return dictionaryTypeService.add(params);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DictionaryTypeVO> addByJson(@RequestBody DictionaryTypeAdd params) {
        log.info("新增字典类型信息(请求方法+JSON参数)[{}]", params);
        return dictionaryTypeService.add(params);
    }

    @GetMapping(params = "page")
    public Mono<Page<DictionaryTypeVO>> query(DictionaryTypeQuery params, Pageable pageable, String... projection) {
        log.info("分页查询字典类型信息(请求方法+参数变量)[{}]", params);
        return dictionaryTypeService.query(params, pageable, projection);
    }

    @GetMapping
    public Flux<DictionaryTypeVO> query(DictionaryTypeQuery params, Sort sort, String... projection) {
        log.info("全量查询字典类型信息(请求方法+参数变量)[{}]", params);
        return dictionaryTypeService.query(params, sort, projection);
    }

    @GetMapping("/{id}")
    public Mono<DictionaryTypeVO> getByUrlPathVariable(@PathVariable Long id, String... projection) {
        log.info("获取字典类型信息(请求方法+路径变量)详情[{}]", id);
        return dictionaryTypeService.get(new DictionaryTypeGet(id), projection);
    }

    @RequestMapping("/get")
    public Mono<DictionaryTypeVO> getByPath(DictionaryTypeGet params, String... projection) {
        log.info("获取字典类型信息(请求路径+参数变量)详情[{}]", params);
        return dictionaryTypeService.get(params, projection);
    }

    @PutMapping(value = {"", "/*"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<Integer> modifyByForm(DictionaryTypeModify params) {
        log.info("修改字典类型信息(请求方法+表单参数)[{}]", params);
        return dictionaryTypeService.modify(params);
    }

    @PutMapping(value = {"", "/*"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> modifyByJson(@RequestBody DictionaryTypeModify params) {
        log.info("修改字典类型信息(请求方法+JSON参数)[{}]", params);
        return dictionaryTypeService.modify(params);
    }

    @DeleteMapping("/{id}")
    public Mono<Integer> deleteByUrlPathVariable(@PathVariable Long id) {
        log.info("删除字典类型信息(请求方法+URL路径变量)[{}]", id);
        return dictionaryTypeService.delete(new DictionaryTypeDelete(id));
    }

    @DeleteMapping(params = "id")
    public Mono<Integer> deleteByUrlParamVariable(DictionaryTypeDelete params) {
        log.info("删除字典类型信息(请求方法+URL参数变量)[{}]", params);
        return dictionaryTypeService.delete(params);
    }

    @RequestMapping(path = "/delete")
    public Mono<Integer> deleteByPath(DictionaryTypeDelete params) {
        log.info("删除字典类型信息(请求路径+URL参数变量)[{}]", params);
        return dictionaryTypeService.delete(params);
    }


}
