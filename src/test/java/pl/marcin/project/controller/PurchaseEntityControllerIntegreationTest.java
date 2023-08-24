package pl.marcin.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.project.database.PurchaseEntityRepository;
import pl.marcin.project.entity.CupEntity;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PurchaseEntityControllerIntegreationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PurchaseEntityRepository purchaseEntityRepository;

    @Transactional
    @Test
    void createPurchase() throws Exception {
        //given
        CupEntity cupEntity = new CupEntity("Red", "circle", BigDecimal.valueOf(2.13));
        String cupEntityJson = objectMapper.writeValueAsString(cupEntity);

        //when
        mockMvc.perform(post("/cups/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cupEntityJson))
                .andExpect(status().isOk());
//        List<CupEntity> result = cupEntityRepository.findAll();

        //then
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(cupEntity.getColor(), result.get(0).getColor());
    }


}
