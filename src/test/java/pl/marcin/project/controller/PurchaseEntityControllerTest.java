package pl.marcin.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.marcin.project.entity.AddressEntity;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void updatePurchase() throws Exception {
        //given
        Long purchaseId = 1L;
        Long cupId = 1L;
        String updateUrl = "/purchases/" + purchaseId + "/update";

        List<CupEntity> cups = new ArrayList<>(List.of(new CupEntity(cupId, "Orange",
                "circle", BigDecimal.valueOf(7.12))));
        List<Long> cupIds = new ArrayList<>(List.of(cupId));
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Janek",
                new AddressEntity());
        PurchaseEntity purchaseEntity = new PurchaseEntity(purchaseId, customerEntity, BigDecimal.valueOf(5.0),
                new ArrayList<CupEntity>());
        PurchaseEntity updatedPurchaseEntity = new PurchaseEntity(purchaseId, customerEntity, BigDecimal.valueOf(7.12),
                cups);
        PurchaseRequest purchaseRequest = new PurchaseRequest(customerEntity.getCustomerId(), BigDecimal.valueOf(7.12),
                cupIds);
        when(customerEntityService.getCustomer(purchaseRequest.getCustomerId())).thenReturn(customerEntity);
        when(cupEntityService.getCupById(cupId)).thenReturn(cups.get(0));
        when(purchaseEntityService.getPurchase(purchaseId)).thenReturn(purchaseEntity);
        when(purchaseEntityService.updatePurchase(any(PurchaseEntity.class))).thenReturn(updatedPurchaseEntity);
        //when then
        mockMvc.perform(put(updateUrl, purchaseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(purchaseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseId").value(1))
                .andExpect(jsonPath("$.cups[0].color").value("Orange"))
                .andExpect(jsonPath("$.purchaseCost").value(7.12));
        verify(customerEntityService).getCustomer(purchaseRequest.getCustomerId());
        verify(cupEntityService).getCupById(cupId);
        verify(purchaseEntityService).getPurchase(purchaseId);
        verify(purchaseEntityService).updatePurchase(purchaseEntity);
    }

    @Test
    void getAllPurchases() throws Exception {
        //given
        List<PurchaseEntity> purchases = new ArrayList<>();
        CustomerEntity customer = new CustomerEntity(1L, "Bill", "Gates", new AddressEntity());
        List<CupEntity> cups = new ArrayList<>(List.of(new CupEntity(1L, "Blue", "Circle",
                BigDecimal.valueOf(1))));
        purchases.add(new PurchaseEntity(1L, customer, BigDecimal.valueOf(1), cups));
        purchases.add(new PurchaseEntity(2L, customer, BigDecimal.valueOf(3), cups));
        when(purchaseEntityService.getAllPurchases()).thenReturn(purchases);

        //when then
        mockMvc.perform(get("/purchases"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(purchases.size()))
                .andExpect(jsonPath("$[0].purchaseId").value(1))
                .andExpect(jsonPath("$[1].purchaseId").value(2))
                .andExpect(jsonPath("$[0].customer.name").value("Bill"));
        verify(purchaseEntityService).getAllPurchases();

    }

    @Test
    void getPurchase() throws Exception {
        //given
        Long purchaseId = 1L;
        String getUrl = "/purchases/" + purchaseId;

        CustomerEntity customer = new CustomerEntity(1L, "Bill", "Gates", new AddressEntity());
        List<CupEntity> cups = new ArrayList<>(List.of(new CupEntity(1L, "Blue", "Circle",
                BigDecimal.valueOf(1))));
        PurchaseEntity purchase = new PurchaseEntity(purchaseId, customer, BigDecimal.valueOf(1), cups);
        when(purchaseEntityService.getPurchase(purchaseId)).thenReturn(purchase);

        //when then
        mockMvc.perform(get(getUrl, purchaseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.purchaseId").value(1))
                .andExpect(jsonPath("$.customer.name").value("Bill"));
        verify(purchaseEntityService).getPurchase(purchaseId);
    }

    @Test
    void getCustomersPurchaseHistory() throws Exception {
        //given
        Long customerId = 1L;
        String getUrl = "/purchases/history/" + customerId;
        List<PurchaseEntity> purchases = new ArrayList<>();
        CustomerEntity customer = new CustomerEntity(1L, "Bill", "Gates", new AddressEntity());
        List<CupEntity> cups = new ArrayList<>(List.of(new CupEntity(1L, "Blue", "Circle",
                BigDecimal.valueOf(1))));
        purchases.add(new PurchaseEntity(1L, customer, BigDecimal.valueOf(1), cups));
        purchases.add(new PurchaseEntity(2L, customer, BigDecimal.valueOf(3), cups));

        //when
        when(purchaseEntityService.getPurchaseHistoryByCustomerId(customerId)).thenReturn(purchases);

        //then
        mockMvc.perform(get(getUrl, customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(purchases.size()))
                .andExpect(jsonPath("$[0].purchaseId").value(1))
                .andExpect(jsonPath("$[1].purchaseId").value(2))
                .andExpect(jsonPath("$[0].customer.name").value("Bill"));
        verify(purchaseEntityService).getPurchaseHistoryByCustomerId(customerId);
    }

    @Test
    void deletePurchase() throws Exception {
        //given
        Long purchaseId = 1L;
        String getUrl = "/purchases/" + purchaseId + "/delete";

        CustomerEntity customer = new CustomerEntity(1L, "Bill", "Gates", new AddressEntity());
        List<CupEntity> cups = new ArrayList<>(List.of(new CupEntity(1L, "Blue", "Circle",
                BigDecimal.valueOf(1))));
        PurchaseEntity existingPurchase = new PurchaseEntity(purchaseId, customer, BigDecimal.valueOf(1), cups);

        //when
        doNothing().when(purchaseEntityService).deletePurchase(purchaseId);

        //then
        mockMvc.perform(delete(getUrl, purchaseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(existingPurchase.getPurchaseId().toString()));
        verify(purchaseEntityService).deletePurchase(purchaseId);
    }
}