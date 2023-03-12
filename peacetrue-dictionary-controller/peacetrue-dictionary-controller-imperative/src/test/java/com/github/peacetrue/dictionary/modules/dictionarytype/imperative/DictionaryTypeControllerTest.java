package com.github.peacetrue.dictionary.modules.dictionarytype.imperative;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.dictionary.imperative.DictionaryControllerTestAutoConfiguration;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeAdd;
import com.github.peacetrue.dictionary.modules.dictionarytype.DictionaryTypeModify;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peace
 */
@SpringBootTest(classes = DictionaryControllerTestAutoConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles("dictionary-controller-test")
class DictionaryTypeControllerTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void add() {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/dictionary-types")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(EASY_RANDOM.nextObject(DictionaryTypeAdd.class)))
                )
                .andExpect(status().isOk())
        ;
    }

    @Test
    @SneakyThrows
    void queryForPage() {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dictionary-types"))
                .andExpect(status().isOk())

        ;
    }

    @Test
    @SneakyThrows
    void queryForList() {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dictionary-types?rtn=list"))
                .andExpect(status().isOk())

        ;
    }

    @Test
    @SneakyThrows
    void get() {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dictionary-types/1"))
                .andExpect(status().isOk())

        ;
    }

    @Test
    @SneakyThrows
    void modify() {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/dictionary-types")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(EASY_RANDOM.nextObject(DictionaryTypeModify.class)))
                )
                .andExpect(status().isOk())

        ;
    }

    @Test
    @SneakyThrows
    void delete() {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/dictionary-types/1")
                )
                .andExpect(status().isOk())

        ;
    }

}
