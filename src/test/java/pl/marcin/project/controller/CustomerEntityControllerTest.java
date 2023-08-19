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
import pl.marcin.project.entityService.CustomerEntityService;
import pl.marcin.project.request.CustomerRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerEntityController.class)
class CustomerEntityControllerTest {
    @MockBean
    private CustomerEntityService customerEntityService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addCustomer() throws Exception {
        CustomerRequest request = new CustomerRequest("Jan", "Kowalski",
                "Kierownicza", 12, "Krakow", "34-300");
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Kowalski",
                new AddressEntity(1L, "Kierownicza", 12, "Kraków", "34-300"));
        when(customerEntityService.addCustomer(customerEntity)).thenReturn(customerEntity);
        mockMvc.perform(post("/customers/add", request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    void updateAddress() {
    }

    @Test
    void updateData() {
    }

    @Test
    void getCustomers() {
    }
}