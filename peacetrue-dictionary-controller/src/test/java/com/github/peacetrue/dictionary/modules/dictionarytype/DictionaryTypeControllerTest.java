package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.dictionary.TestControllerDictionaryAutoConfiguration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

/**
 * @author xiayx
 */
@SpringBootTest(classes = TestControllerDictionaryAutoConfiguration.class)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DictionaryTypeControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    @Order(10)
    public void add() {
        this.client.post().uri("/dictionary-types")
                .bodyValue(DictionaryTypeServiceImplTest.ADD)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DictionaryTypeVO.class).value((Consumer<DictionaryTypeVO>) vo -> DictionaryTypeServiceImplTest.vo = vo);
    }

    @Test
    @Order(20)
    public void queryForPage() {
        this.client.get()
                .uri("/dictionary-types?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.totalElements").isEqualTo(1);
    }

    @Test
    @Order(30)
    public void queryForList() {
        this.client.get()
                .uri("/dictionary-types")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(1);
    }

    @Test
    @Order(40)
    public void get() {
        this.client.get()
                .uri("/dictionary-types/{0}", DictionaryTypeServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DictionaryTypeVO.class).isEqualTo(DictionaryTypeServiceImplTest.vo);
    }


    @Test
    @Order(50)
    public void modify() {
        DictionaryTypeModify modify = DictionaryTypeServiceImplTest.MODIFY;
        modify.setId(DictionaryTypeServiceImplTest.vo.getId());
        this.client.put()
                .uri("/dictionary-types/{id}", DictionaryTypeServiceImplTest.vo.getId())
                .bodyValue(modify)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

    @Test
    @Order(60)
    public void delete() {
        this.client.delete()
                .uri("/dictionary-types/{0}", DictionaryTypeServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

}
