package pl.marcin.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entityService.CupEntityService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CupEntityController.class)
class CupEntityControllerTest {
    @MockBean
    private CupEntityService cupEntityService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveCup() throws Exception {
        CupEntity cupEntity = new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14));
        when(cupEntityService.addCup(cupEntity)).thenReturn(cupEntity);
        mockMvc.perform(post("/cups/add", cupEntity)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cupId\": 1, \"color\": \"white\",\"shape\": \"circle\", \"price\": 2.14}"))
                .andExpect(status().isOk());

    }

    @Test
    void getCupById() throws Exception {
        CupEntity cupEntity = new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14));
        when(cupEntityService.getCupById(1L)).thenReturn(cupEntity);
        mockMvc.perform(get("/cups/{cupId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("white"));

    }

    @Test
    void getCupByIdExceptionThrow() throws Exception {

        when(cupEntityService.getCupById(anyLong())).thenThrow(RuntimeException.class);
        mockMvc.perform(get("/cups/{cupId}", 1L))
                .andExpect(status().isNotFound());

    }

    @Test
    void getCups() throws Exception {
        CupEntity cupEntity1 = new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14));
        CupEntity cupEntity2 = new CupEntity(2L, "black", "curly", BigDecimal.valueOf(5.14));
        List<CupEntity> cups = new ArrayList<>();
        cups.add(cupEntity1);
        cups.add(cupEntity2);
        when(cupEntityService.getAllCups()).thenReturn(cups);
        mockMvc.perform(get("/cups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }
}