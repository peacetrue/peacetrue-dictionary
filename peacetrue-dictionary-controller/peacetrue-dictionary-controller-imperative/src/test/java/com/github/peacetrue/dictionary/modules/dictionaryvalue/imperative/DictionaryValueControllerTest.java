package com.github.peacetrue.dictionary.modules.dictionaryvalue.imperative;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.dictionary.imperative.DictionaryControllerTestAutoConfiguration;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueAdd;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueModify;
import com.github.peacetrue.dictionary.modules.dictionaryvalue.DictionaryValueVO;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peace
 */
@SpringBootTest(classes = DictionaryControllerTestAutoConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles("dictionary-controller-test")
class DictionaryValueControllerTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DictionaryValueService dictionaryValueService;

    @Test
    @SneakyThrows
    @Order(10)
    void add() {
        // avoid java.lang.AssertionError: Content type not set
        Mockito.when(dictionaryValueService.add(Mockito.any())).thenReturn(new DictionaryValueVO());
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/dictionary-values")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(EASY_RANDOM.nextObject(DictionaryValueAdd.class)))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    @SneakyThrows
    @Order(20)
    void queryForPage() {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dictionary-values"))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        ;
    }

    @Test
    @SneakyThrows
    @Order(30)
    void queryForList() {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dictionary-values?rtn=list"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @SneakyThrows
    @Order(40)
    void get() {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dictionary-values/1"))
                .andExpect(status().isOk())
        ;
    }

    @Test
    @SneakyThrows
    @Order(50)
    void modify() {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/dictionary-values")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(EASY_RANDOM.nextObject(DictionaryValueModify.class)))
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @SneakyThrows
    @Order(60)
    void delete() {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/dictionary-values/1"))
                .andExpect(status().isOk())
        ;
    }

}
