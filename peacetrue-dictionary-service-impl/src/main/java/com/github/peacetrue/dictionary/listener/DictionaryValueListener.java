package com.github.peacetrue.dictionary.listener;

import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryType;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValue;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueAdd;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;
import com.github.peacetrue.r2dbc.PeaceR2dbcRepository;
import com.github.peacetrue.spring.data.relational.core.query.QueryUtils;
import com.github.peacetrue.spring.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

/**
 * @author : xiayx
 * @since : 2020-12-06 08:10
 **/
@Slf4j
@Component
public class DictionaryValueListener {

    @Autowired
    private PeaceR2dbcRepository peaceR2dbcRepository;
    @Autowired
    private R2dbcEntityOperations entityOperations;

    @EventListener
    public void setDictionaryTypeCodeAfterDictionaryValueAdd(PayloadApplicationEvent<DictionaryValueAdd> event) {
        DictionaryValueVO source = (DictionaryValueVO) event.getSource();
        log.info("新增字典值[{}]后，设置字典类型编码", source);
        entityOperations.selectOne(QueryUtils.id(source::getDictionaryTypeId), DictionaryType.class)
                .flatMap(dictionaryType -> peaceR2dbcRepository.setPropertyValue(
                        BeanUtils.map(source, DictionaryValue.class),
                        "dictionaryTypeCode",
                        dictionaryType.getCode()
                ))
                .publishOn(Schedulers.elastic())
                .subscribe();
    }
}
