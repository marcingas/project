package pl.marcin.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.project.database.PurchaseEntityRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.request.PurchaseRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    @Sql(scripts = "classpath:test-data.sql")
    void createPurchase() throws Exception {
        //given
        Long customerId = 1L;
        CustomerEntity customer = new CustomerEntity(1L, "Bill", "Gates", new AddressEntity());
        List<Long> cupIds = new ArrayList<>(List.of(1L, 2L, 3L));
        PurchaseRequest purchaseRequest = new PurchaseRequest(customerId, BigDecimal.valueOf(12.00), cupIds);
        String purchaseEntityJson = objectMapper.writeValueAsString(purchaseRequest);

        //when
        mockMvc.perform(post("/purchases/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseEntityJson))
                .andExpect(status().isOk());
        List<PurchaseEntity> results = purchaseEntityRepository.findAll();

        //then
        assertNotNull(results);
    }
}
