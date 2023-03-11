package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.dictionary.DictionaryServiceTestAutoConfiguration;
import com.github.peacetrue.spring.beans.BeanUtils;
import com.github.peacetrue.spring.data.domain.PageableUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.unitils.reflectionassert.ReflectionAssert;
import reactor.test.StepVerifier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static com.github.peacetrue.dictionary.DictionaryServiceTestAutoConfiguration.DICTIONARY_VALUE;

/**
 * @author peace
 **/
@Slf4j
@SpringBootTest(
        classes = DictionaryServiceTestAutoConfiguration.class,
        properties = {"db.schema=dictionary_value_service"}
)
@ActiveProfiles("dictionary-service-test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DictionaryValueServiceImplTest {

    /** 这里直接使用实现类，方便跳转到实现方法 */
    @Autowired
    private DictionaryValueServiceImpl service;
    @Autowired
    private R2dbcTransactionManager transactionManager;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        Files.deleteIfExists(Paths.get("dictionary_value_service.mv.db"));
    }


    @SneakyThrows
    @AfterAll
    static void afterAll() {
        // 测试完成后，删除 h2 的数据存储文件
        Files.deleteIfExists(Paths.get("dictionary_value_service.mv.db"));
    }

    @Test
    @Order(10)
    void add() {
        DictionaryValueAdd add = new EasyRandom().nextObject(DictionaryValueAdd.class);
        add.setDictionaryTypeId(DICTIONARY_VALUE.getId());
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
        DictionaryValueQuery params = BeanUtils.convert(DICTIONARY_VALUE, DictionaryValueQuery.class);
        service.queryPage(params, PageableUtils.PAGEABLE_DEFAULT)
                .as(StepVerifier::create)
                .assertNext(page -> ReflectionAssert.assertReflectionEquals(DICTIONARY_VALUE, page.getContent().get(0)))
                .verifyComplete();
    }

    @Test
    @Order(30)
    void queryForList() {
        DictionaryValueQuery query = BeanUtils.convert(DICTIONARY_VALUE, DictionaryValueQuery.class);
        service.queryList(query, PageableUtils.PAGEABLE_DEFAULT)
                .as(StepVerifier::create)
                .assertNext(vo -> ReflectionAssert.assertReflectionEquals(DICTIONARY_VALUE, vo))
                .verifyComplete();
    }

    @Test
    @Order(40)
    void get() {
        service.get(BeanUtils.convert(DICTIONARY_VALUE, DictionaryValueGet.class))
                .as(StepVerifier::create)
                .assertNext(vo -> ReflectionAssert.assertReflectionEquals(DICTIONARY_VALUE, vo))
                .verifyComplete();
    }

    @Test
    @Order(50)
    void modify() {
        TransactionalOperator.create(transactionManager)
                .execute(status -> {
                    status.setRollbackOnly();
                    return service.modify(BeanUtils.convert(DICTIONARY_VALUE, DictionaryValueModify.class));
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
                    return service.delete(new DictionaryValueDelete().setId(DICTIONARY_VALUE.getId()));
                })
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }
}
