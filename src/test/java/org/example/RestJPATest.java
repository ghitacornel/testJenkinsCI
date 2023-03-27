package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RestJPATest {

    @Autowired
    MockMvc mvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        personRepository.deleteAll();
    }

    @Test
    public void testFindByIdWithNoResult() throws Exception {
        mvc.perform(get("/person/{id}", "-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("no person found for id -1"));
    }

    @Test
    @SneakyThrows
    public void testReadCreateReadUpdateReadDeleteRead() {

        // read
        mvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        // create
        {
            Person person = new Person(1, "name", "pass");
            mvc.perform(put("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(person)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        }

        // read
        {
            Person person = new Person(1, "name", "pass");
            mvc.perform(get("/person/{id}", "1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(objectMapper.writeValueAsString(person)));
        }

        // update
        {
            Person person = new Person(1, "new name", "new pass");
            mvc.perform(post("/person")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(person)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(""));
        }

        // read
        {
            Person person = new Person(1, "new name", "new pass");
            mvc.perform(get("/person/{id}", "1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(objectMapper.writeValueAsString(person)));
        }

        // delete
        mvc.perform(delete("/person/{id}", "1"))
                .andExpect(status().isOk());

        // read
        mvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));


    }
}
