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
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.reflectionassert.ReflectionAssert;

import javax.validation.ConstraintViolationException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.github.peacetrue.dictionary.DictionaryServiceTestAutoConfiguration.DICTIONARY_TYPE;
import static com.github.peacetrue.dictionary.DictionaryServiceTestAutoConfiguration.DICTIONARY_VALUE;

/**
 * @author peace
 **/
@Slf4j
@SpringBootTest(classes = DictionaryServiceTestAutoConfiguration.class)
@ActiveProfiles("dictionary-service-test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class DictionaryTypeServiceImplTest {
    @Autowired
    private DictionaryTypeServiceImpl service;
    @Autowired
    private DictionaryValueService dictionaryValueService;


    @Test
    void validate() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> service.add(null));
    }

    @Test
    @Order(10)
    void add() {
        DictionaryTypeAdd add = new EasyRandom().nextObject(DictionaryTypeAdd.class);
        DictionaryTypeVO vo = service.add(add);
        Map<String, Object> voMap = BeanUtils.getPropertyValues(vo);
        Map<String, Object> addMap = BeanUtils.getPropertyValues(add);
        addMap.remove("dictionaryValues");
        Assertions.assertTrue(voMap.entrySet().containsAll(addMap.entrySet()));
    }

    @Test
    @Order(20)
    void queryPage() {
        DictionaryTypeQuery query = BeanUtils.convert(DICTIONARY_TYPE, DictionaryTypeQuery.class);
        Page<DictionaryTypeVO> page = service.queryPage(query, PageableUtils.PAGEABLE_DEFAULT);
        Assertions.assertEquals(1, page.getTotalElements());

        ReflectionAssert.assertReflectionEquals(DICTIONARY_TYPE, page.getContent().get(0));
    }

    @Test
    @Order(30)
    void queryList() {
        DictionaryTypeQuery query = BeanUtils.convert(DICTIONARY_TYPE, DictionaryTypeQuery.class);
        List<DictionaryTypeVO> vos = service.queryList(query, PageableUtils.PAGEABLE_DEFAULT);
        Assertions.assertEquals(1, vos.size());

        ReflectionAssert.assertReflectionEquals(DICTIONARY_TYPE, vos.get(0));
    }

    @Test
    @Order(40)
    void get() {
        DictionaryTypeVO vo = service.get(BeanUtils.convert(DICTIONARY_TYPE, DictionaryTypeGet.class));
        ReflectionAssert.assertReflectionEquals(DICTIONARY_TYPE, vo);
    }

    @Test
    @Order(60)
    void modify() {
        DictionaryTypeModify modify = BeanUtils.convert(DICTIONARY_TYPE, DictionaryTypeModify.class);
        Assertions.assertEquals(1, service.modify(modify));
    }

    @Test
    @Order(70)
    void delete() {
        Assertions.assertEquals(1, dictionaryValueService.delete(new DictionaryValueDelete(DICTIONARY_VALUE.getId())));
        Assertions.assertEquals(1, service.delete(new DictionaryTypeDelete(DICTIONARY_TYPE.getId())));
    }
}
