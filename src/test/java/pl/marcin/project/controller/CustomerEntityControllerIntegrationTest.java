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
import pl.marcin.project.database.CustomerEntityRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.request.CustomerRequest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerEntityControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CustomerEntityRepository customerEntityRepository;

    @Test
    @Transactional
    void saveCustomer() throws Exception {
        //given
        CustomerRequest customerRequest = new CustomerRequest("Johannes", "Fritz",
                "Jungle", 3, "Johhanesburg", "44000");
        String requestJson = objectMapper.writeValueAsString(customerRequest);

        //when
        mockMvc.perform(post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
        List<CustomerEntity> results = customerEntityRepository.findAll();

        //then
        assertNotNull(results);
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:test-data.sql")
    void updateCustomersAddress() throws Exception {
        //given
        Long customerId = 1L;
        String updateAddressUrl = "/customers/" + customerId + "/update-address";
        AddressEntity updatedAddress = new AddressEntity("TownHall", 34, "Georgia", "0099282");
        //when
        mockMvc.perform(put(updateAddressUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAddress)))
                .andExpect(status().isOk());
        CustomerEntity cutomerResult = customerEntityRepository.findById(customerId).get();
        //then
        assertNotNull(cutomerResult.getAddress().getAddressId());
        assertEquals(updatedAddress.getStreet(), cutomerResult.getAddress().getStreet());
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:test-data.sql")
    void updateCustomerData() throws Exception {
        //given
        Long customerId = 1L;
        String updateDataUrl = "/customers/" + customerId + "/update-data";
        CustomerEntity updatedCustomer = new CustomerEntity("Boko", "Roko");
        //then
        mockMvc.perform(put(updateDataUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk());

        CustomerEntity customerResult = customerEntityRepository.findById(customerId).get();
        assertNotNull(customerResult.getCustomerId());
        assertEquals(updatedCustomer.getName(), customerResult.getName());
    }

    @Test
    @Sql(scripts = "classpath:test-data.sql")
    void getCustomer() throws Exception {
        //given
        Long customerId = 1L;
        String getUrl = "/customers/" + customerId;

        //when
        mockMvc.perform(get(getUrl))
                .andExpect(status().isOk());
        CustomerEntity result = customerEntityRepository.findById(customerId).get();

        //then
        assertNotNull(result);
    }

    @Test
    @Sql(scripts = "classpath:test-data.sql")
    void getAllCustomer() throws Exception {

        //given
        String getUrl = "/customers";
        mockMvc.perform(get(getUrl))
                .andExpect(status().isOk());
        List<CustomerEntity> results = customerEntityRepository.findAll();
        //then
        assertNotNull(results);
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:test-data.sql")
    void deleteCustomer() throws Exception {
        //given
        Long customerId = 1L;
        String deleteUrl = "/customers/delete/" + customerId;

        //then
        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isOk());
        assertThrows(NoSuchElementException.class, () -> customerEntityRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException()));
    }
}
