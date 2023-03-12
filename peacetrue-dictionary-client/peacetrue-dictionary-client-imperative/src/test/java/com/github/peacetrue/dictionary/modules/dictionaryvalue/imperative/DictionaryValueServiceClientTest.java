package com.github.peacetrue.dictionary.modules.dictionaryvalue.imperative;

import com.github.peacetrue.dictionary.imperative.DictionaryClientTestAutoConfiguration;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.*;
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
class DictionaryValueServiceClientTest {

    @Autowired
    private DictionaryValueServiceClient dictionaryValueServiceClient;

    @Test
    void add() {
        DictionaryValueAdd params = new DictionaryValueAdd();
        Assertions.assertDoesNotThrow(() -> dictionaryValueServiceClient.add(params));
    }

    @Test
    void queryPage() {
        DictionaryValueQuery params = new DictionaryValueQuery();
        Assertions.assertDoesNotThrow(() -> dictionaryValueServiceClient.queryPage(params, PageRequest.ofSize(10), "id"));
    }

    @Test
    void queryList() {
        DictionaryValueQuery params = new DictionaryValueQuery();
        Assertions.assertDoesNotThrow(() -> dictionaryValueServiceClient.queryList(params, PageRequest.ofSize(10), "id"));
    }

    @Test
    void get() {
        DictionaryValueGet params = new DictionaryValueGet().setId(1L);
        Assertions.assertDoesNotThrow(() -> dictionaryValueServiceClient.get(params, "id"));
    }

    @Test
    void modify() {
        DictionaryValueModify params = new DictionaryValueModify().setId(1L);
        Assertions.assertDoesNotThrow(() -> dictionaryValueServiceClient.modify(params));
    }

    @Test
    void delete() {
        DictionaryValueDelete params = new DictionaryValueDelete().setId(1L);
        Assertions.assertDoesNotThrow(() -> dictionaryValueServiceClient.delete(params));
    }
}
