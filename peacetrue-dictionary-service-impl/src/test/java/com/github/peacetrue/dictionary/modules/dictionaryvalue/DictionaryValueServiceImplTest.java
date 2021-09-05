package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.dictionary.TestServiceDictionaryAutoConfiguration;
import com.github.peacetrue.spring.util.BeanUtils;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.io.Serializable;


/**
 * @author peace
 * @since 1.0.0
 **/
@SpringBootTest(classes = TestServiceDictionaryAutoConfiguration.class)
@ActiveProfiles("dictionary-service-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DictionaryValueServiceImplTest {

    public static final EasyRandom EASY_RANDOM = new EasyRandom(new EasyRandomParameters().randomize(Serializable.class, () -> 1L));
    public static final DictionaryValueAdd ADD = EASY_RANDOM.nextObject(DictionaryValueAdd.class);
    public static final DictionaryValueModify MODIFY = EASY_RANDOM.nextObject(DictionaryValueModify.class);
    public static DictionaryValueVO vo;

    static {
        ADD.setOperatorId(EASY_RANDOM.nextObject(Long.class));
        MODIFY.setOperatorId(EASY_RANDOM.nextObject(Long.class));
    }

    @Autowired
    private DictionaryValueServiceImpl service;

    @Test
    @Order(10)
    void add() {
        service.add(ADD)
                .as(StepVerifier::create)
                .assertNext(data -> {
                    Assertions.assertEquals(data.getCreatorId(), ADD.getOperatorId());
                    vo = data;
                })
                .verifyComplete();
    }

    @Test
    @Order(20)
    void queryForPage() {
        DictionaryValueQuery params = BeanUtils.map(vo, DictionaryValueQuery.class);
        service.query(params, PageRequest.of(0, 10))
                .as(StepVerifier::create)
                .assertNext(page -> Assertions.assertEquals(1, page.getTotalElements()))
                .verifyComplete();
    }

    @Test
    @Order(30)
    void queryForList() {
        DictionaryValueQuery params = BeanUtils.map(vo, DictionaryValueQuery.class);
        service.query(params)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(40)
    void get() {
        DictionaryValueGet params = BeanUtils.map(vo, DictionaryValueGet.class);
        service.get(params)
                .as(StepVerifier::create)
                .assertNext(item -> Assertions.assertEquals(vo.getId(), item.getId()))
                .verifyComplete();
    }

    @Test
    @Order(50)
    void modify() {
        DictionaryValueModify params = MODIFY;
        params.setId(vo.getId());
        service.modify(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    @Order(60)
    void delete() {
        DictionaryValueDelete params = new DictionaryValueDelete(vo.getId());
        service.delete(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }
}
