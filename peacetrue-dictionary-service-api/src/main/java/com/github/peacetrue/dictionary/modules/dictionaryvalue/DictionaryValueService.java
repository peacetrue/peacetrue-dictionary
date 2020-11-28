package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;

/**
 * 字典项值服务接口
 *
 * @author xiayx
 */
public interface DictionaryValueService {

    /** 新增 */
    Mono<DictionaryValueVO> add(DictionaryValueAdd params);

    /** 分页查询 */
    Mono<Page<DictionaryValueVO>> query(@Nullable DictionaryValueQuery params, @Nullable Pageable pageable, String... projection);

    /** 全量查询 */
    Flux<DictionaryValueVO> query(DictionaryValueQuery params, @Nullable Sort sort, String... projection);

    /** 全量查询 */
    default Flux<DictionaryValueVO> query(DictionaryValueQuery params, String... projection) {
        return this.query(params, (Sort) null, projection);
    }

    /** 获取 */
    Mono<DictionaryValueVO> get(DictionaryValueGet params, String... projection);

    /** 修改 */
    Mono<Integer> modify(DictionaryValueModify params);

    /** 删除 */
    Mono<Integer> delete(DictionaryValueDelete params);

}
