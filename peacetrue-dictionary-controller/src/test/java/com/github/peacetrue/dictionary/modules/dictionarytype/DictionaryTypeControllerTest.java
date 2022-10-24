package com.github.peacetrue.dictionary.modules.dictionarytype;

import com.github.peacetrue.dictionary.DictionaryControllerTestAutoConfiguration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

/**
 * @author peace
 */
@SpringBootTest(
        classes = DictionaryControllerTestAutoConfiguration.class,
        properties = {"db.schema=dictionary_type_controller"}
)
@AutoConfigureWebTestClient
@ActiveProfiles({"dictionary-controller-test", "dictionary-service-test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DictionaryTypeControllerTest {

    @Autowired
    private WebTestClient client;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        Files.deleteIfExists(Paths.get("dictionary_type_controller.mv.db"));
    }

    @SneakyThrows
    @AfterAll
    static void afterAll() {
        // 测试完成后，删除 h2 的数据存储文件
        Files.deleteIfExists(Paths.get("dictionary_type_controller.mv.db"));
    }

    @Test
    @Order(10)
    void add() {
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
    void queryForPage() {
        this.client.get()
                .uri("/dictionary-types")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.totalElements").isEqualTo(1);
    }

    @Test
    @Order(30)
    void queryForList() {
        this.client.get()
                .uri("/dictionary-types?rtn=list")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(1);
    }

    @Test
    @Order(40)
    void get() {
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
    void modify() {
        DictionaryTypeModify modify = DictionaryTypeServiceImplTest.MODIFY;
        modify.setId(DictionaryTypeServiceImplTest.vo.getId());
        this.client.put()
                .uri("/dictionary-types")
                .bodyValue(modify)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

    @Test
    @Order(60)
    void delete() {
        this.client.delete()
                .uri("/dictionary-types/{0}", DictionaryTypeServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

}
