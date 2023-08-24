package pl.marcin.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.serviceentity.CustomerEntityService;
import pl.marcin.project.serviceroutecalculator.RouteCalculatorService;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)
class RouteControllerTest {
    @MockBean
    RouteCalculatorService routeCalculatorService;
    @MockBean
    CustomerEntityService customerEntityService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void sendDataForCountingDistance() throws Exception {
        //given uzupełnić dane !
        Long customerStartId = 1L;
        Long customerEndId = 2L;
        CustomerEntity customerStart = new CustomerEntity();
        CustomerEntity customerEnd = new CustomerEntity();
        AddressData addressDataStart = new AddressData();
        AddressData addressDataEnd = new AddressData();
        when(customerEntityService.getCustomer(customerStartId)).thenReturn(customerStart);
        when(customerEntityService.getCustomer(customerEndId)).thenReturn(customerEnd);
        when(routeCalculatorService.addressDataGenerator(customerStart)).thenReturn(new AddressData());
        when(routeCalculatorService.addressDataGenerator(customerEnd)).thenReturn(new AddressData());
        when(routeCalculatorService.distance(addressDataStart, addressDataEnd)).thenReturn(5000);


        //then
        mockMvc.perform(post("/route/1/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}