package pl.marcin.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.request.PurchaseRequest;
import pl.marcin.project.serviceentity.CupEntityService;
import pl.marcin.project.serviceentity.CustomerEntityService;
import pl.marcin.project.serviceentity.PurchaseEntityService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseEntityController.class)
class PurchaseEntityControllerTest {
    @MockBean
    PurchaseEntityService purchaseEntityService;
    @MockBean
    CustomerEntityService customerEntityService;
    @MockBean
    CupEntityService cupEntityService;
    @Autowired
    MockMvc mockMvc;


    @Test
    void addPurchase() throws Exception {
        //given
        List<Long> cupIds = new ArrayList<>(List.of(1L));
        Long customerId = 1L;
        Long purchaseId = 2L;
        CupEntity cupEntity1 = new CupEntity(1L, "orange", "circle", BigDecimal.valueOf(2.12));
        List<CupEntity> cups = new ArrayList<>(List.of(cupEntity1));
        PurchaseRequest request = new PurchaseRequest(customerId, BigDecimal.valueOf(12.12), cupIds);
        CustomerEntity customerEntity = new CustomerEntity(customerId, "John", "Rambo");
        PurchaseEntity purchaseEntity = new PurchaseEntity(customerEntity, request.getCost(), cups);
        PurchaseEntity purchaseEntityResponse = new PurchaseEntity(purchaseId, customerEntity, request.getCost(), cups);
        when(cupEntityService.getCupById(anyLong())).thenReturn(cupEntity1);
        when(customerEntityService.getCustomer(customerId)).thenReturn(customerEntity);
        when(purchaseEntityService.addPurchase(purchaseEntity)).thenReturn(purchaseEntityResponse);

        //when then
        mockMvc.perform(post("/purchases/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseId").value(2));
        verify(cupEntityService).getCupById(anyLong());
        verify(customerEntityService).getCustomer(customerId);
        verify(purchaseEntityService).addPurchase(purchaseEntity);
    }

    @Test
    void updatePurchase() {
    }

    @Test
    void getAllPurchases() {
    }

    @Test
    void getPurchase() {
    }

    @Test
    void deletePurchase() {

    }
}