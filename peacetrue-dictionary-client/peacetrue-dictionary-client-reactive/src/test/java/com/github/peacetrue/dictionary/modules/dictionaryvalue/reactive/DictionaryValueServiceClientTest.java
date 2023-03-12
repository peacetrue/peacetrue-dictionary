package com.github.peacetrue.dictionary.modules.dictionaryvalue.reactive;

import com.github.peacetrue.dictionary.modules.dictionaryvalue.*;
import com.github.peacetrue.dictionary.reactive.DictionaryClientTestAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

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
        StepVerifier
                .create(dictionaryValueServiceClient.add(params))
                .expectComplete()
                .verify();
    }

    @Test
    void queryPage() {
        DictionaryValueQuery params = new DictionaryValueQuery();
        StepVerifier
                .create(dictionaryValueServiceClient.queryPage(params, PageRequest.ofSize(10), "id"))
                .expectComplete()
                .verify();
    }

    @Test
    void queryList() {
        DictionaryValueQuery params = new DictionaryValueQuery();
        StepVerifier
                .create(dictionaryValueServiceClient.queryList(params, PageRequest.ofSize(10), "id"))
                .expectComplete()
                .verify();
    }

    @Test
    void get() {
        DictionaryValueGet params = new DictionaryValueGet().setId(1L);
        StepVerifier
                .create(dictionaryValueServiceClient.get(params, "id"))
                .expectComplete()
                .verify();
    }

    @Test
    void modify() {
        DictionaryValueModify params = new DictionaryValueModify().setId(1L);
        StepVerifier
                .create(dictionaryValueServiceClient.modify(params))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        DictionaryValueDelete params = new DictionaryValueDelete().setId(1L);
        StepVerifier
                .create(dictionaryValueServiceClient.delete(params))
                .expectComplete()
                .verify();
    }
}
