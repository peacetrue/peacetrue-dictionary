package com.github.peacetrue.dictionary.modules.dictionarytype.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.*;
import com.github.peacetrue.dictionary.modules.dictionarytype.reactive.DictionaryTypeService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 字典类型控制器。
 *
 * @author peace
 */
@Slf4j
@RestController
@RequestMapping(value = "/dictionary-types")
@Setter(onMethod = @__(@Autowired))
public class DictionaryTypeController {

    private DictionaryTypeService dictionaryTypeService;

    /**
     * 根据请求体，新增字典类型。
     *
     * @param params 新增参数
     * @return 字典类型视图
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DictionaryTypeVO> add(@RequestBody DictionaryTypeAdd params) {
        log.info("add DictionaryType by json params(body): {}", params);
        return dictionaryTypeService.add(params);
    }

    /**
     * 分页查询字典类型集合。
     *
     * @param params     查询参数
     * @param pageable   分页参数
     * @param projection 投影
     * @return 字典类型视图分页
     */
    @GetMapping
    public Mono<Page<DictionaryTypeVO>> queryPage(DictionaryTypeQuery params, Pageable pageable, String... projection) {
        log.info("page query DictionaryType by form params(url): {}", params);
        return dictionaryTypeService.queryPage(params, pageable, projection);
    }

    /**
     * 列表查询字典类型集合，使用 rtn=list 区别于分页查询字典类型集合。
     *
     * @param params     查询参数
     * @param pageable   分页参数
     * @param projection 投影
     * @return 字典类型视图集合
     */
    @GetMapping(params = "rtn=list")
    public Flux<DictionaryTypeVO> queryList(DictionaryTypeQuery params, Pageable pageable, String... projection) {
        log.info("list query DictionaryType by form params(url): {}", params);
        return dictionaryTypeService.queryList(params, pageable, projection);
    }

    /**
     * 根据路径参数，获取字典类型。
     *
     * @param id         主键
     * @param projection 投影
     * @return 字典类型视图
     */
    @GetMapping("/{id}")
    public Mono<DictionaryTypeVO> get(@PathVariable Long id, String... projection) {
        log.info("get DictionaryType by path params(url): {}", id);
        return dictionaryTypeService.get(new DictionaryTypeGet().setId(id), projection);
    }

    /**
     * 根据请求体，修改字典类型。
     *
     * @param params 修改参数
     * @return 受影响的行数
     */
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> modify(@RequestBody DictionaryTypeModify params) {
        log.info("modify DictionaryType by json params(body): {}", params);
        return dictionaryTypeService.modify(params);
    }

    /**
     * 根据路径变量，删除字典类型。
     *
     * @param id 主键
     * @return 受影响的行数
     */
    @DeleteMapping("/{id}")
    public Mono<Integer> delete(@PathVariable Long id) {
        log.info("delete DictionaryType by path params(url): {}", id);
        return dictionaryTypeService.delete(new DictionaryTypeDelete().setId(id));
    }

}
