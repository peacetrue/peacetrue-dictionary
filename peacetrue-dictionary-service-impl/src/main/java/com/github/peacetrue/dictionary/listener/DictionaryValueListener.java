package com.github.peacetrue.dictionary.listener;

import com.github.peacetrue.core.Operators;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeGet;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeService;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueAdd;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueModify;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueService;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
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
    private DictionaryTypeService dictionaryTypeService;
    @Autowired
    private DictionaryValueService dictionaryValueService;

    @EventListener
    public void setDictionaryTypeCodeAfterDictionaryValueAdd(PayloadApplicationEvent<DictionaryValueAdd> event) {
        DictionaryValueVO source = (DictionaryValueVO) event.getSource();
        log.info("新增字典值[{}]后，设置字典类型编码", source);
        DictionaryTypeGet dictionaryTypeGet = new DictionaryTypeGet(source.getDictionaryTypeId());
        dictionaryTypeService.get(Operators.setOperator(event.getPayload(), dictionaryTypeGet))
                .flatMap(dictionaryTypeVO -> {
                    DictionaryValueModify dictionaryValueModify = new DictionaryValueModify();
                    Operators.setOperator(event.getPayload(), dictionaryValueModify);
                    dictionaryValueModify.setId(dictionaryTypeVO.getId());
                    dictionaryValueModify.setCode(dictionaryValueModify.getCode());
                    return dictionaryValueService.modify(dictionaryValueModify);
                })
                .publishOn(Schedulers.elastic())
                .subscribe();
    }
}
