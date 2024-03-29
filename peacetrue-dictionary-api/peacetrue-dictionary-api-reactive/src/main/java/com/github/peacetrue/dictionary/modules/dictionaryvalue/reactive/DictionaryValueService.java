package com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive;

import com.github.peacetrue.dictionary.modules.dictionaryvalue.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 字典项值服务接口。
 *
 * @author peace
 */
public interface DictionaryValueService {

    /** 按序号排列 */
    Sort SORT_SERIAL_NUMBER = Sort.by("dictionaryTypeCode", "serialNumber");

    /**
     * 新增字典项值。
     *
     * @param params 新增参数
     * @return 字典项值视图
     */
    Mono<DictionaryValueVO> add(DictionaryValueAdd params);

    /**
     * 分页查询字典项值。
     *
     * @param params     查询参数
     * @param pageable   分页参数
     * @param projection 投影
     * @return 字典项值分页
     */
    Mono<Page<DictionaryValueVO>> queryPage(DictionaryValueQuery params, Pageable pageable, String... projection);

    /**
     * 列表查询字典项值。
     *
     * @param params     查询参数
     * @param pageable   分页参数
     * @param projection 投影
     * @return 字典项值视图集合
     */
    Flux<DictionaryValueVO> queryList(DictionaryValueQuery params, Pageable pageable, String... projection);

    /**
     * 获取字典项值。
     *
     * @param params     获取参数
     * @param projection 投影
     * @return 字典项值视图
     */
    Mono<DictionaryValueVO> get(DictionaryValueGet params, String... projection);

    /**
     * 修改字典项值（局部修改）。
     *
     * @param params 修改参数
     * @return 受影响行数
     */
    Mono<Integer> modify(DictionaryValueModify params);

    /**
     * 删除字典项值。
     *
     * @param params 删除参数
     * @return 受影响行数
     */
    Mono<Integer> delete(DictionaryValueDelete params);

}
