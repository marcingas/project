package pl.marcin.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CupEntityController.class)
class CupEntityControllerTest {
    @MockBean
    private CupEntityService cupEntityService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveCup() throws Exception {
        //given
        CupEntity cupEntity = new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14));

        //when
        when(cupEntityService.addCup(cupEntity)).thenReturn(cupEntity);

        //then
        mockMvc.perform(post("/cups/add", cupEntity)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cupEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("white"))
                .andExpect(jsonPath("$.shape").value("circle"));
    }

    @Test
    void getCupById() throws Exception {
        //given
        CupEntity cupEntity = new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14));

        //when
        when(cupEntityService.getCupById(1L)).thenReturn(cupEntity);

        //then
        mockMvc.perform(get("/cups/{cupId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("white"));

    }

    @Test
    void getCupByIdExceptionThrow() throws Exception {
        //when
        when(cupEntityService.getCupById(anyLong())).thenThrow(RuntimeException.class);

        //then
        mockMvc.perform(get("/cups/{cupId}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCups() throws Exception {
        //given
        CupEntity cupEntity1 = new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14));
        CupEntity cupEntity2 = new CupEntity(2L, "black", "curly", BigDecimal.valueOf(5.14));
        List<CupEntity> cups = new ArrayList<>();
        cups.add(cupEntity1);
        cups.add(cupEntity2);

        //when
        when(cupEntityService.getAllCups()).thenReturn(cups);

        //then
        mockMvc.perform(get("/cups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(cups.size())))
                .andExpect(jsonPath("$[0].color").value("white"));
    }

    @Test
    void updateCup() throws Exception {
        //given
        CupEntity existingCup = new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14));
        CupEntity updatedCup = new CupEntity(1L, "black", "curly", BigDecimal.valueOf(5.14));

        //when
        when(cupEntityService.getCupById(existingCup.getCupId())).thenReturn(existingCup);
        when(cupEntityService.updateCup(any(CupEntity.class))).thenReturn(updatedCup);

        //then
        mockMvc.perform(put("/cups/{cupId}/update", existingCup.getCupId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCup)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cupId").value(1))
                .andExpect(jsonPath("$.color").value("black"))
                .andExpect(jsonPath("$.shape").value("curly"));
    }

    @Test
    void deleteCup() throws Exception {
        //given
        CupEntity cupToDelete = new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14));

        //when
        doNothing().when(cupEntityService).deleteCup(cupToDelete.getCupId());

        //then
        mockMvc.perform(delete("/cups/{cupId}/delete", cupToDelete.getCupId()))
                .andExpect(status().isOk())
                .andExpect(content().string(cupToDelete.getCupId().toString()));
        verify(cupEntityService, times(1)).deleteCup(cupToDelete.getCupId());
    }
}