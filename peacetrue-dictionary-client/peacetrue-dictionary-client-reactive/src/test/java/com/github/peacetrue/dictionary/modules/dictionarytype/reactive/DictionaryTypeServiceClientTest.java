package com.github.peacetrue.dictionary.modules.dictionarytype.reactive;

import com.github.peacetrue.dictionary.modules.dictionarytype.*;
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
class DictionaryTypeServiceClientTest {

    @Autowired
    private DictionaryTypeServiceClient dictionaryTypeServiceClient;

    @Test
    void add() {
        DictionaryTypeAdd params = new DictionaryTypeAdd();
        StepVerifier
                .create(dictionaryTypeServiceClient.add(params))
                .expectComplete()
                .verify();
    }

    @Test
    void queryPage() {
        DictionaryTypeQuery params = new DictionaryTypeQuery();
        StepVerifier
                .create(dictionaryTypeServiceClient.queryPage(params, PageRequest.ofSize(10), "id"))
                .expectComplete()
                .verify();
    }

    @Test
    void queryList() {
        DictionaryTypeQuery params = new DictionaryTypeQuery();
        StepVerifier
                .create(dictionaryTypeServiceClient.queryList(params, PageRequest.ofSize(10), "id"))
                .expectComplete()
                .verify();
    }

    @Test
    void get() {
        DictionaryTypeGet params = new DictionaryTypeGet().setId(1L);
        StepVerifier
                .create(dictionaryTypeServiceClient.get(params, "id"))
                .expectComplete()
                .verify();
    }

    @Test
    void modify() {
        DictionaryTypeModify params = new DictionaryTypeModify().setId(1L);
        StepVerifier
                .create(dictionaryTypeServiceClient.modify(params))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        DictionaryTypeDelete params = new DictionaryTypeDelete().setId(1L);
        StepVerifier
                .create(dictionaryTypeServiceClient.delete(params))
                .expectComplete()
                .verify();
    }
}
