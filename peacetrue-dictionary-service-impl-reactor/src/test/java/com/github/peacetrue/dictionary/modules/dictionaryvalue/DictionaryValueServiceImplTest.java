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
import org.springframework.test.context.ActiveProfiles;
import org.unitils.reflectionassert.ReflectionAssert;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author peace
 **/
@Slf4j
@SpringBootTest(
        classes = DictionaryServiceTestAutoConfiguration.class,
        properties = {"db.schema=dictionary_value_service"}
)
@ActiveProfiles("dictionary-service-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DictionaryValueServiceImplTest {

    public static final EasyRandom EASY_RANDOM = new EasyRandom();
    public static final DictionaryValueAdd ADD = EASY_RANDOM.nextObject(DictionaryValueAddInner.class);
    public static final DictionaryValueModify MODIFY = EASY_RANDOM.nextObject(DictionaryValueModify.class);
    public static DictionaryValueVO vo = DictionaryValueVO.builder().id(1L).build();

    /** 这里直接使用实现类，方便跳转到实现方法 */
    @Autowired
    private DictionaryValueServiceImpl service;

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

//    @Test
    void queryAll() {
        DictionaryValueGet params = BeanUtils.convert(vo, DictionaryValueGet.class);
        service.get(params)
                .doOnNext(ii -> {
                    log.info("Ii: {}", ii);
                })
                .publishOn(Schedulers.immediate())
                .subscribe();
    }

    @Test
    @Order(10)
    void add() {
        service.add(ADD)
                .as(StepVerifier::create)
                .assertNext(data -> {
                    Map<String, Object> vos = BeanUtils.getPropertyValues(data);
                    Map<String, Object> adds = BeanUtils.getPropertyValues(ADD);
                    Assertions.assertTrue(vos.entrySet().containsAll(adds.entrySet()));
                    vo = data;
                })
                .verifyComplete();
    }

    @Test
    @Order(20)
    void queryForPage() {
        DictionaryValueQuery params = BeanUtils.convert(vo, DictionaryValueQuery.class);
        service.queryPage(params, PageableUtils.PAGEABLE_DEFAULT)
                .as(StepVerifier::create)
                .assertNext(page -> Assertions.assertEquals(1, page.getTotalElements()))
                .verifyComplete();
    }

    @Test
    @Order(30)
    void queryForList() {
        DictionaryValueQuery params = BeanUtils.convert(vo, DictionaryValueQuery.class);
        service.queryList(params, PageableUtils.PAGEABLE_DEFAULT)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(40)
    void get() {
        DictionaryValueGet params = BeanUtils.convert(vo, DictionaryValueGet.class);
        service.get(params)
                .as(StepVerifier::create)
                // H2 正常，MySQL 时间搓不同
                .assertNext(item -> ReflectionAssert.assertReflectionEquals(vo, item))
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
