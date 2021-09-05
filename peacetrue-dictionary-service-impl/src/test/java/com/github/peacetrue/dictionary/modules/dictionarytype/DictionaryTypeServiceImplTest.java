package com.github.peacetrue.dictionary.modules.dictionarytype;

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
class DictionaryTypeServiceImplTest {

    public static final EasyRandom EASY_RANDOM = new EasyRandom(new EasyRandomParameters().randomize(Serializable.class, () -> 1L));
    public static final DictionaryTypeAdd ADD = EASY_RANDOM.nextObject(DictionaryTypeAdd.class);
    public static final DictionaryTypeModify MODIFY = EASY_RANDOM.nextObject(DictionaryTypeModify.class);
    public static DictionaryTypeVO vo;

    static {
        ADD.setOperatorId(EASY_RANDOM.nextObject(Long.class));
        MODIFY.setOperatorId(EASY_RANDOM.nextObject(Long.class));
    }

    @Autowired
    private DictionaryTypeServiceImpl service;

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
        DictionaryTypeQuery params = BeanUtils.map(vo, DictionaryTypeQuery.class);
        service.query(params, PageRequest.of(0, 10))
                .as(StepVerifier::create)
                .assertNext(page -> Assertions.assertEquals(1, page.getTotalElements()))
                .verifyComplete();
    }

    @Test
    @Order(30)
    void queryForList() {
        DictionaryTypeQuery params = BeanUtils.map(vo, DictionaryTypeQuery.class);
        service.query(params)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(40)
    void get() {
        DictionaryTypeGet params = BeanUtils.map(vo, DictionaryTypeGet.class);
        service.get(params)
                .as(StepVerifier::create)
                .assertNext(item -> Assertions.assertEquals(vo.getId(), item.getId()))
                .verifyComplete();
    }

    @Test
    @Order(50)
    void modify() {
        DictionaryTypeModify params = MODIFY;
        params.setId(vo.getId());
        service.modify(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    @Order(60)
    void delete() {
        DictionaryTypeDelete params = new DictionaryTypeDelete(vo.getId());
        service.delete(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }
}
