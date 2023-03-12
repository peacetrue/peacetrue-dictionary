package com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive;

import com.github.peacetrue.dictionary.modules.dictionaryvalue.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.github.peacetrue.dictionary.DictionaryConstants.DICTIONARY_VALUE_PATH;

/**
 * 字典项值控制器。
 *
 * @author peace
 */
@Slf4j
@RestController
@RequestMapping(value = DICTIONARY_VALUE_PATH)
@Setter(onMethod = @__(@Autowired))
public class DictionaryValueController {

    private DictionaryValueService dictionaryValueService;

    /**
     * 根据请求体，新增字典项值。
     *
     * @param params 新增参数
     * @return 字典项值视图
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DictionaryValueVO> add(@RequestBody DictionaryValueAdd params) {
        log.info("add DictionaryValue by json params(body): {}", params);
        return dictionaryValueService.add(params);
    }

    /**
     * 分页查询字典项值集合。
     *
     * @param params     查询参数
     * @param pageable   分页参数
     * @param projection 投影
     * @return 字典项值视图分页
     */
    @GetMapping
    public Mono<Page<DictionaryValueVO>> queryPage(DictionaryValueQuery params, Pageable pageable, String... projection) {
        log.info("page query DictionaryValue by form params(url): {}", params);
        return dictionaryValueService.queryPage(params, pageable, projection);
    }

    /**
     * 列表查询字典项值集合，使用 rtn=list 区别于分页查询字典项值集合。
     *
     * @param params     查询参数
     * @param pageable   分页参数
     * @param projection 投影
     * @return 字典项值视图集合
     */
    @GetMapping(params = "rtn=list")
    public Flux<DictionaryValueVO> queryList(DictionaryValueQuery params, Pageable pageable, String... projection) {
        log.info("list query DictionaryValue by form params(url): {}", params);
        return dictionaryValueService.queryList(params, pageable, projection);
    }

    /**
     * 根据路径参数，获取字典项值。
     *
     * @param id         主键
     * @param projection 投影
     * @return 字典项值视图
     */
    @GetMapping("/{id}")
    public Mono<DictionaryValueVO> get(@PathVariable Long id, String... projection) {
        log.info("get DictionaryValue by path params(url): {}", id);
        return dictionaryValueService.get(new DictionaryValueGet().setId(id), projection);
    }

    /**
     * 根据请求体，修改字典项值。
     *
     * @param params 修改参数
     * @return 受影响的行数
     */
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> modify(@RequestBody DictionaryValueModify params) {
        log.info("modify DictionaryValue by json params(body): {}", params);
        return dictionaryValueService.modify(params);
    }

    /**
     * 根据路径变量，删除字典项值。
     *
     * @param id 主键
     * @return 受影响的行数
     */
    @DeleteMapping("/{id}")
    public Mono<Integer> delete(@PathVariable Long id) {
        log.info("delete DictionaryValue by path params(url): {}", id);
        return dictionaryValueService.delete(new DictionaryValueDelete().setId(id));
    }

}
