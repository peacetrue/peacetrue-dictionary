package com.github.peacetrue.dictionary.modules.dictionarytype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;

/**
 * 字典类型服务接口
 *
 * @author xiayx
 */
public interface DictionaryTypeService {

    /** 新增 */
    Mono<DictionaryTypeVO> add(DictionaryTypeAdd params);

    /** 分页查询 */
    Mono<Page<DictionaryTypeVO>> query(@Nullable DictionaryTypeQuery params, @Nullable Pageable pageable, String... projection);

    /** 全量查询 */
    Flux<DictionaryTypeVO> query(DictionaryTypeQuery params, @Nullable Sort sort, String... projection);

    /** 全量查询 */
    default Flux<DictionaryTypeVO> query(DictionaryTypeQuery params, String... projection) {
        return this.query(params, (Sort) null, projection);
    }

    /** 获取 */
    Mono<DictionaryTypeVO> get(DictionaryTypeGet params, String... projection);

    /** 修改 */
    Mono<Integer> modify(DictionaryTypeModify params);

    /** 删除 */
    Mono<Integer> delete(DictionaryTypeDelete params);

}
