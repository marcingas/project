package pl.marcin.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.project.database.CupEntityRepository;
import pl.marcin.project.entity.CupEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CupEntityControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CupEntityRepository cupEntityRepository;


    @Transactional
    @Test
    void createCup() throws Exception {
        //given
        CupEntity cupEntity = new CupEntity("Red", "circle", BigDecimal.valueOf(2.13));
        String cupEntityJson = objectMapper.writeValueAsString(cupEntity);

        //when
        mockMvc.perform(post("/cups/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cupEntityJson))
                .andExpect(status().isOk());
        List<CupEntity> result = cupEntityRepository.findAll();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cupEntity.getColor(), result.get(0).getColor());
    }

    @Test
    @Sql(scripts = "classpath:test-data.sql")
    void getCupWithId() throws Exception {
        //given
        Long cupId = 1L;
        String getUrl = "/cups/" + cupId;

        //when
        mockMvc.perform(get(getUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Red"));
        CupEntity result = cupEntityRepository.findById(cupId)
                .orElseThrow(() -> new NoSuchElementException("No cup found"));

        //then
        assertNotNull(result);
    }

    @Test
    @Sql(scripts = "classpath:test-data.sql")
    void getCupWithFalseId() throws Exception {
        //given
        Long cupId = 100L;
        String getUrl = "/cups/" + cupId;

        //when
        mockMvc.perform(get(getUrl))
                .andExpect(status().isNotFound());
        //then
        assertThrows(NoSuchElementException.class, () -> {
            cupEntityRepository.findById(cupId)
                    .orElseThrow(() -> new NoSuchElementException());
        });
    }

    @Test
    @Sql(scripts = "classpath:test-data.sql")
    void getAllCups() throws Exception {
        //when
        mockMvc.perform(get("/cups"))
                .andExpect(status().isOk());
        List<CupEntity> resultCups = cupEntityRepository.findAll();
        //then
        assertNotNull(resultCups);
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:test-data.sql")
    void updateCup() throws Exception {
        //given
        Long cupId = 1L;
        CupEntity existingCup = cupEntityRepository.findById(cupId)
                .orElseThrow(() -> new NoSuchElementException());
        String updateUrl = "/cups/" + cupId + "/update";
        CupEntity updatedCup = new CupEntity("brown", "curly", BigDecimal.valueOf(2.10));
        //then
        mockMvc.perform(put(updateUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCup)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:test-data.sql")
    void deleteCup() throws Exception {
        //given
        Long cupIdForDeletion = 1L;
        String deleteUrl = "/cups/" + cupIdForDeletion + "/delete";

        //then
        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isOk());
        assertThrows(NoSuchElementException.class, () -> cupEntityRepository.findById(cupIdForDeletion)
                .orElseThrow(() -> new NoSuchElementException()));
    }
}
