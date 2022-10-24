package com.github.peacetrue.dictionary.modules.dictionarytype;

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
        properties = {"db.schema=dictionary_type_service"}
)
@ActiveProfiles("dictionary-service-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DictionaryTypeServiceImplTest {
    public static final EasyRandom EASY_RANDOM = new EasyRandom();
    public static final DictionaryTypeAdd ADD = EASY_RANDOM.nextObject(DictionaryTypeAdd.class);
    public static final DictionaryTypeModify MODIFY = EASY_RANDOM.nextObject(DictionaryTypeModify.class);
    public static DictionaryTypeVO vo;

    @Autowired
    private DictionaryTypeServiceImpl service;

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
        service.add(ADD)
                .as(StepVerifier::create)
                .assertNext(data -> {
                    Map<String, Object> vo = BeanUtils.getPropertyValues(data);
                    Map<String, Object> add = BeanUtils.getPropertyValues(ADD);
                    add.remove("dictionaryValues");
                    Assertions.assertTrue(vo.entrySet().containsAll(add.entrySet()));
                    DictionaryTypeServiceImplTest.vo = data;
                })
                .verifyComplete();
    }

    @Test
    @Order(20)
    void queryForPage() {
        DictionaryTypeQuery params = BeanUtils.convert(vo, DictionaryTypeQuery.class);
        service.queryPage(params, PageableUtils.PAGEABLE_DEFAULT)
                .as(StepVerifier::create)
                .assertNext(page -> Assertions.assertEquals(1, page.getTotalElements()))
                .verifyComplete();
    }

    @Test
    @Order(30)
    void queryForList() {
        DictionaryTypeQuery params = BeanUtils.convert(vo, DictionaryTypeQuery.class);
        service.queryList(params, PageableUtils.PAGEABLE_DEFAULT)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(40)
    void get() {
        DictionaryTypeGet params = BeanUtils.convert(vo, DictionaryTypeGet.class);
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
