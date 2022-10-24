package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.dictionary.DictionaryControllerTestAutoConfiguration;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author peace
 */
@SpringBootTest(classes = DictionaryControllerTestAutoConfiguration.class)
@AutoConfigureWebTestClient
@ActiveProfiles("dictionary-controller-test")
class DictionaryValueControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    @Order(10)
    void add() {
        this.client.post().uri("/dictionary-values")
                .bodyValue(new DictionaryValueAdd())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(20)
    void queryForPage() {
        this.client.get()
                .uri("/dictionary-values")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(30)
    void queryForList() {
        this.client.get()
                .uri("/dictionary-values?rtn=list")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(40)
    void get() {
        this.client.get()
                .uri("/dictionary-values/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(50)
    void modify() {
        this.client.put()
                .uri("/dictionary-values")
                .bodyValue(new DictionaryValueModify())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(60)
    void delete() {
        this.client.delete()
                .uri("/dictionary-values/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

}
