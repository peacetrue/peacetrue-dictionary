package com.github.peacetrue.dictionary.modules.dictionarytype.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 字典类型服务接口。
 *
 * @author peace
 */
public interface DictionaryTypeService {

    /**
     * 新增字典类型。
     *
     * @param params 新增参数
     * @return 字典类型视图
     */
    Mono<DictionaryTypeVO> add(DictionaryTypeAdd params);

    /**
     * 分页查询字典类型。
     *
     * @param params     查询参数
     * @param pageable   分页参数
     * @param projection 投影
     * @return 字典类型视图分页
     */
    Mono<Page<DictionaryTypeVO>> queryPage(DictionaryTypeQuery params, Pageable pageable, String... projection);

    /**
     * 列表查询字典类型。
     *
     * @param params     查询参数
     * @param pageable   分页参数
     * @param projection 投影
     * @return 字典类型视图集合
     */
    Flux<DictionaryTypeVO> queryList(DictionaryTypeQuery params, Pageable pageable, String... projection);

    /**
     * 获取字典类型。
     *
     * @param params     获取参数
     * @param projection 投影
     * @return 字典类型视图
     */
    Mono<DictionaryTypeVO> get(DictionaryTypeGet params, String... projection);

    /**
     * 修改字典类型（局部修改）。
     *
     * @param params 修改参数
     * @return 受影响行数
     */
    Mono<Integer> modify(DictionaryTypeModify params);

    /**
     * 删除字典类型。
     *
     * @param params 删除参数
     * @return 受影响行数
     */
    Mono<Integer> delete(DictionaryTypeDelete params);

}
