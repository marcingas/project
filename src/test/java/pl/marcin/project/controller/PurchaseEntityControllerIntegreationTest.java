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
import pl.marcin.project.database.PurchaseEntityRepository;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.request.PurchaseRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:test-data.sql")
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
        Long customerId = 1L;
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

    @Transactional
    @Test
    void updatePurchase() throws Exception {
        //given
        Long customerId = 2L;
        Long purchaseId = 1L;
        List<Long> cupIds = new ArrayList<>(List.of(1L, 2L));
        String updateDataUrl = "/purchases/" + purchaseId + "/update";
        PurchaseRequest purchaseRequest = new PurchaseRequest(customerId, BigDecimal.valueOf(70.00), cupIds);
        String purchaseEntityJson = objectMapper.writeValueAsString(purchaseRequest);

        //when
        mockMvc.perform(put(updateDataUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseEntityJson))
                .andExpect(status().isOk());
        PurchaseEntity result = purchaseEntityRepository.findById(purchaseId).get();

        //then
        assertNotNull(result);
        assertEquals(cupIds.size(), result.getCups().size());
        assertEquals(purchaseRequest.getCustomerId(), result.getCustomer().getCustomerId());
        assertEquals(purchaseRequest.getCost(), result.getPurchaseCost());
    }

    @Test
    void getAllPurchases() throws Exception {
        //when
        mockMvc.perform(get("/purchases"))
                .andExpect(status().isOk());
        //then
        List<PurchaseEntity> results = purchaseEntityRepository.findAll();
        assertNotNull(results);
    }

    @Test
    void getPurchase() throws Exception {
        //given
        Long purchaseId = 1L;
        String getUrl = "/purchases/" + purchaseId;

        //when
        mockMvc.perform(get(getUrl))
                .andExpect(status().isOk());

        //then
        PurchaseEntity result = purchaseEntityRepository.findById(purchaseId).get();
        assertNotNull(result);
    }

    @Test
    void getCustomersPurchaseHistory() throws Exception {
        //given
        Long customerId = 1L;
        String getHistoryUrl = "/purchases/history/" + customerId;

        //when
        mockMvc.perform(get(getHistoryUrl))
                .andExpect(status().isOk());

        //then
        List<PurchaseEntity> results = purchaseEntityRepository.findByCustomerCustomerId(customerId).get();
        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    @Transactional
    void deletePurchase() throws Exception {
        //given
        Long purchaseId = 1L;
        String deleteUrl = "/purchases/" + purchaseId + "/delete";

        //when
        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isOk());

        //then
        assertThrows(NoSuchElementException.class, () -> purchaseEntityRepository.findById(purchaseId)
                .orElseThrow(() -> new NoSuchElementException()));
    }
}
