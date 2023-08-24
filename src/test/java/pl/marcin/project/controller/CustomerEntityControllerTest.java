package pl.marcin.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.request.CustomerRequest;
import pl.marcin.project.serviceentity.AddressEntityService;
import pl.marcin.project.serviceentity.CustomerEntityService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerEntityController.class)
class CustomerEntityControllerTest {
    @MockBean
    private CustomerEntityService customerEntityService;
    @MockBean
    private AddressEntityService addressEntityService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addCustomer() throws Exception {
        //given
        CustomerRequest request = new CustomerRequest("Jan", "Kowalski",
                "Kierownicza", 12, "Krakow", "34-300");
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Kowalski",
                new AddressEntity(1L, "Kierownicza", 12, "Kraków", "34-300"));
        when(customerEntityService.addCustomer(any())).thenReturn(customerEntity);

        //when then
        mockMvc.perform(post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.surname").value("Kowalski"));
    }

    @Test
    void updateAddress() throws Exception {
        //given
        AddressEntity addressEntity = new AddressEntity(1L, "Kierownicza", 12,
                "Kraków", "34-300");
        AddressEntity updatedAddress = new AddressEntity("Stolarska",
                23, "Opole", "30-200");
        AddressEntity updatedAddressWithId = new AddressEntity(1L, "Stolarska",
                23, "Opole", "30-200");
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Kowalski", addressEntity);
        CustomerEntity updatedCustomer = new CustomerEntity(1L, "Jan", "Kowalski",
                updatedAddressWithId);

        //when
        when(customerEntityService.getCustomer(customerEntity.getCustomerId())).thenReturn(customerEntity);
        when(addressEntityService.addAddress(updatedAddress)).thenReturn(updatedAddressWithId);
        when(customerEntityService.updateCustomer(any())).thenReturn(updatedCustomer);


        //then
        mockMvc.perform(put("/customers/{customerId}/update-address", customerEntity.getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAddress)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.street").value("Stolarska"))
                .andExpect(jsonPath("$.address.number").value(23));
        verify(customerEntityService, times(1)).getCustomer(customerEntity.getCustomerId());
        verify(addressEntityService, times(1)).addAddress(updatedAddress);
        verify(customerEntityService, times(1)).updateCustomer(any());
    }

    @Test
    void updateData() throws Exception {
        //given
        AddressEntity address = new AddressEntity(1L, "Kierownicza", 12,
                "Kraków", "34-300");
        CustomerEntity customerEntity = new CustomerEntity(1L, "Jan", "Kowalski", address);
        CustomerEntity updatedCustomerData = new CustomerEntity("Piotr", "Zawadzki");
        CustomerEntity updatedCustomer = new CustomerEntity(1L, "Piotr", "Zawadzki", address);

        //when
        when(customerEntityService.getCustomer(customerEntity.getCustomerId())).thenReturn(customerEntity);
        when(customerEntityService.updateCustomer(updatedCustomer)).thenReturn(updatedCustomer);

        //then
        mockMvc.perform(put("/customers/{customerId}/update-data", customerEntity.getCustomerId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCustomerData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Piotr"))
                .andExpect(jsonPath("$.surname").value("Zawadzki"));
    }

    @Test
    void getCustomers() throws Exception {
        //given
        List<CustomerEntity> customerList = new ArrayList<>();
        customerList.add(new CustomerEntity(1L, "John", "Summers", new AddressEntity()));
        customerList.add(new CustomerEntity(2L, "Susan", "Summers", new AddressEntity()));
        customerList.add(new CustomerEntity(3L, "Olivia", "Summers", new AddressEntity()));

        //when
        when(customerEntityService.getAllCustomers()).thenReturn(customerList);

        //then
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(customerList.size()))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Summers"));
    }

    @Test
    void deleteCustomer() throws Exception {
        //given
        AddressEntity address = new AddressEntity(1L, "Kierownicza", 12,
                "Kraków", "34-300");
        CustomerEntity existingCustomer = new CustomerEntity(1L, "Jan", "Kowalski", address);

        //when
        doNothing().when(customerEntityService).deleteCustomer(existingCustomer.getCustomerId());

        //then
        mockMvc.perform(delete("/customers/delete/{customerId}", existingCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(content().string(existingCustomer.getCustomerId().toString()));
        verify(customerEntityService, times(1)).deleteCustomer(existingCustomer.getCustomerId());
    }
}