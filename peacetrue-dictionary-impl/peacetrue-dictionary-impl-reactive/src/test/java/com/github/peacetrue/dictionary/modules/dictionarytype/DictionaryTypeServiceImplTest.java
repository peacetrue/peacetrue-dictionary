package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.dictionary.DictionaryServiceTestAutoConfiguration;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueDelete;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueService;
import com.github.peacetrue.spring.beans.BeanUtils;
import com.github.peacetrue.spring.data.domain.PageableUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.unitils.reflectionassert.ReflectionAssert;
import reactor.test.StepVerifier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static com.github.peacetrue.dictionary.DictionaryServiceTestAutoConfiguration.DICTIONARY_TYPE;
import static com.github.peacetrue.dictionary.DictionaryServiceTestAutoConfiguration.DICTIONARY_VALUE;

/**
 * @author peace
 **/
@Slf4j
@SpringBootTest(
        classes = DictionaryServiceTestAutoConfiguration.class,
        properties = {"db.schema=dictionary_type_service"}
)
@ActiveProfiles("dictionary-service-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DictionaryTypeServiceImplTest {

    @Autowired
    private DictionaryTypeServiceImpl service;
    @Autowired
    private ReactiveTransactionManager transactionManager;
    @Autowired
    private DictionaryValueService dictionaryValueService;


    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        Files.deleteIfExists(Paths.get("dictionary_type_service.mv.db"));
    }

    @SneakyThrows
    @AfterAll
    static void afterAll() {
        // 测试完成后，删除 h2 的数据存储文件
        Files.deleteIfExists(Paths.get("dictionary_type_service.mv.db"));
    }

    @Test
    @Order(10)
    void add() {
        DictionaryTypeAdd add = new EasyRandom().nextObject(DictionaryTypeAdd.class);
        TransactionalOperator.create(transactionManager)
                .execute(status -> {
                    status.setRollbackOnly();
                    return service.add(add);
                })
                .as(StepVerifier::create)
                .assertNext(data -> {
                    Map<String, Object> voMap = BeanUtils.getPropertyValues(data);
                    Map<String, Object> addMap = BeanUtils.getPropertyValues(add);
                    addMap.remove("dictionaryValues");
                    Assertions.assertTrue(voMap.entrySet().containsAll(addMap.entrySet()));
                })
                .verifyComplete();
    }

    @Test
    @Order(20)
    void queryForPage() {
        DictionaryTypeQuery params = BeanUtils.convert(DICTIONARY_TYPE, DictionaryTypeQuery.class);
        service.queryPage(params, PageableUtils.PAGEABLE_DEFAULT)
                .as(StepVerifier::create)
                .assertNext(page -> ReflectionAssert.assertReflectionEquals(DICTIONARY_TYPE, page.getContent().get(0)))
                .verifyComplete();
    }

    @Test
    @Order(30)
    void queryForList() {
        DictionaryTypeQuery query = BeanUtils.convert(DICTIONARY_TYPE, DictionaryTypeQuery.class);
        service.queryList(query, PageableUtils.PAGEABLE_DEFAULT)
                .as(StepVerifier::create)
                .assertNext(vo -> ReflectionAssert.assertReflectionEquals(DICTIONARY_TYPE, vo))
                .verifyComplete();
    }

    @Test
    @Order(40)
    void get() {
        service.get(BeanUtils.convert(DICTIONARY_TYPE, DictionaryTypeGet.class))
                .as(StepVerifier::create)
                .assertNext(vo -> ReflectionAssert.assertReflectionEquals(DICTIONARY_TYPE, vo))
                .verifyComplete();
    }

    @Test
    @Order(50)
    void modify() {
        TransactionalOperator.create(transactionManager)
                .execute(status -> {
                    status.setRollbackOnly();
                    return service.modify(BeanUtils.convert(DICTIONARY_TYPE, DictionaryTypeModify.class));
                })
                .as(StepVerifier::create)
                .assertNext(count -> Assertions.assertEquals(1, count))
                .verifyComplete();
    }

    @Test
    @Order(60)
    void delete() {
        TransactionalOperator.create(transactionManager)
                .execute(status -> {
                    status.setRollbackOnly();
                    return dictionaryValueService.delete(new DictionaryValueDelete().setId(DICTIONARY_VALUE.getId()))
                            .then(service.delete(new DictionaryTypeDelete().setId(DICTIONARY_TYPE.getId())));
                })
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }
}
