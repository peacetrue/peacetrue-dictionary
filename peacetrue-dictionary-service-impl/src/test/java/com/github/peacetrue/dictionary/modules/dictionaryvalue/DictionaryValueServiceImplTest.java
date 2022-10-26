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
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.reflectionassert.ReflectionAssert;

import javax.validation.ConstraintViolationException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.github.peacetrue.dictionary.DictionaryServiceTestAutoConfiguration.DICTIONARY_VALUE;

/**
 * @author peace
 **/
@Slf4j
@SpringBootTest(classes = DictionaryServiceTestAutoConfiguration.class)
@ActiveProfiles("dictionary-service-test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class DictionaryValueServiceImplTest {
    @Autowired
    private DictionaryValueServiceImpl service;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        Files.deleteIfExists(Paths.get("test.mv.db"));
    }

    @SneakyThrows
    @AfterAll
    static void afterAll() {
        // 测试完成后，删除 h2 的数据存储文件
        Files.deleteIfExists(Paths.get("test.mv.db"));
    }

    @Test
    void validate() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> service.add(null));
    }

    @Test
    @Order(10)
    void add() {
        DictionaryValueAdd add = new EasyRandom().nextObject(DictionaryValueAdd.class);
        add.setDictionaryTypeId(DICTIONARY_VALUE.getId());
        DictionaryValueVO vo = service.add(add);
        Map<String, Object> voMap = BeanUtils.getPropertyValues(vo);
        Map<String, Object> addMap = BeanUtils.getPropertyValues(add);
        Assertions.assertTrue(voMap.entrySet().containsAll(addMap.entrySet()));
    }

    @Test
    @Order(20)
    void queryPage() {
        DictionaryValueQuery query = BeanUtils.convert(DICTIONARY_VALUE, DictionaryValueQuery.class);
        Page<DictionaryValueVO>  page = service.queryPage(query, PageableUtils.PAGEABLE_DEFAULT);
        Assertions.assertEquals(1, page.getTotalElements());
        ReflectionAssert.assertReflectionEquals(DICTIONARY_VALUE, page.getContent().get(0));
    }

    @Test
    @Order(30)
    void queryList() {
        DictionaryValueQuery query = BeanUtils.convert(DICTIONARY_VALUE, DictionaryValueQuery.class);
        List<DictionaryValueVO> vos = service.queryList(query, PageableUtils.PAGEABLE_DEFAULT);
        Assertions.assertEquals(1, vos.size());

        ReflectionAssert.assertReflectionEquals(DICTIONARY_VALUE, vos.get(0));
    }

    @Test
    @Order(40)
    void get() {
        DictionaryValueVO vo = service.get(BeanUtils.convert(DICTIONARY_VALUE, DictionaryValueGet.class));
        ReflectionAssert.assertReflectionEquals(DICTIONARY_VALUE, vo);
    }

    @Test
    @Order(60)
    void modify() {
        DictionaryValueModify modify = BeanUtils.convert(DICTIONARY_VALUE, DictionaryValueModify.class);
        Assertions.assertEquals(1, service.modify(modify));
    }

    @Test
    @Order(70)
    void delete() {
        Assertions.assertEquals(1, service.delete(new DictionaryValueDelete(DICTIONARY_VALUE.getId())));
    }
}
