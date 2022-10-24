package com.github.peacetrue.dictionary.modules.dictionaryvalue;

import com.github.peacetrue.dictionary.DictionaryControllerTestAutoConfiguration;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeServiceImplTest;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeVO;
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
        properties = {"db.schema=dictionary_value_controller"}
)
@AutoConfigureWebTestClient
@ActiveProfiles({"dictionary-controller-test", "dictionary-service-test"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DictionaryValueControllerTest {

    @Autowired
    private WebTestClient client;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        Files.deleteIfExists(Paths.get("dictionary_value_controller.mv.db"));
    }

    @SneakyThrows
    @AfterAll
    static void afterAll() {
        // 测试完成后，删除 h2 的数据存储文件
        Files.deleteIfExists(Paths.get("dictionary_value_controller.mv.db"));
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
                .expectBody(DictionaryTypeVO.class).value(vo -> {
                    DictionaryTypeServiceImplTest.vo = vo;
                    DictionaryValueServiceImplTest.ADD.setDictionaryTypeId(vo.getId());
                });

        this.client.post().uri("/dictionary-values")
                .bodyValue(DictionaryValueServiceImplTest.ADD)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DictionaryValueVO.class).value((Consumer<DictionaryValueVO>) vo -> DictionaryValueServiceImplTest.vo = vo);
    }

    @Test
    @Order(20)
    void queryForPage() {
        this.client.get()
                .uri("/dictionary-values?page=0")
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
                .uri("/dictionary-values?rtn=list")
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
                .uri("/dictionary-values/{0}", DictionaryValueServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DictionaryValueVO.class).isEqualTo(DictionaryValueServiceImplTest.vo);
    }


    @Test
    @Order(50)
    void modify() {
        DictionaryValueModify modify = DictionaryValueServiceImplTest.MODIFY;
        modify.setId(DictionaryValueServiceImplTest.vo.getId());
        this.client.put()
                .uri("/dictionary-values")
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
                .uri("/dictionary-values/{0}", DictionaryValueServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

}
