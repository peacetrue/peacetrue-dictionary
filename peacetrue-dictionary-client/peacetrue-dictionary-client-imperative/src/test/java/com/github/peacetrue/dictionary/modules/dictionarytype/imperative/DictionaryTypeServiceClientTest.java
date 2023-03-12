package com.github.peacetrue.dictionary.modules.dictionarytype.imperative;

import com.github.peacetrue.dictionary.imperative.DictionaryClientTestAutoConfiguration;
import com.github.peacetrue.dictionary.modules.dictionarytype.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author peace
 **/
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = DictionaryClientTestAutoConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("dictionary-client-test")
class DictionaryTypeServiceClientTest {

    @Autowired
    private DictionaryTypeServiceClient dictionaryTypeServiceClient;

    @Test
    void add() {
        DictionaryTypeAdd params = new DictionaryTypeAdd();
        Assertions.assertDoesNotThrow(() -> dictionaryTypeServiceClient.add(params));
    }

    @Test
    void queryPage() {
        DictionaryTypeQuery params = new DictionaryTypeQuery();
        Assertions.assertDoesNotThrow(() -> dictionaryTypeServiceClient.queryPage(params, PageRequest.ofSize(10), "id"));
    }

    @Test
    void queryList() {
        DictionaryTypeQuery params = new DictionaryTypeQuery();
        Assertions.assertDoesNotThrow(() -> dictionaryTypeServiceClient.queryList(params, PageRequest.ofSize(10), "id"));
    }

    @Test
    void get() {
        DictionaryTypeGet params = new DictionaryTypeGet().setId(1L);
        Assertions.assertDoesNotThrow(() -> dictionaryTypeServiceClient.get(params, "id"));
    }

    @Test
    void modify() {
        DictionaryTypeModify params = new DictionaryTypeModify().setId(1L);
        Assertions.assertDoesNotThrow(() -> dictionaryTypeServiceClient.modify(params));
    }

    @Test
    void delete() {
        DictionaryTypeDelete params = new DictionaryTypeDelete().setId(1L);
        Assertions.assertDoesNotThrow(() -> dictionaryTypeServiceClient.delete(params));
    }
}
