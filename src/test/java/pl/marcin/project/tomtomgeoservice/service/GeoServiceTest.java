package pl.marcin.project.tomtomgeoservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;

import static org.mockito.Mockito.*;


class GeoServiceTest {
    private GeoService geoService;


    @Test
    void countDistanceBetweenClients() {


    }

    @Test
    void generateAddressData() {
        CustomerEntity customerEntity = new CustomerEntity(2L, "John", "Kowalski",
                new AddressEntity(1L, "ABC", 1, "Krakow", "34-322"));
        AddressData addressData = geoService.generateAddressData(customerEntity);
        Assertions.assertEquals(customerEntity.getAddress().getTown(), addressData.getTown());
    }

}