package pl.marcin.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.marcin.project.database.AddressEntityReactiveRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.entityService.AddressEntityReactiveService;
import pl.marcin.project.entityService.CustomerEntityReactiveService;
import pl.marcin.project.model.Address;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class AddressCupUtilitiesTest {


    @Test
    void addressEntityToDto() {
        AddressEntity addressEntity = new AddressEntity(1L, "abc", 1, "abc", "30-000");

        Address address = AddressCupUtilities.addressEntityToDto(addressEntity);
        Assertions.assertEquals(1, address.getAddress_id());
    }

    @Test
    void cupEntityToDto() {
        CupEntity cupEntity = new CupEntity(1L, "ehite", "circle", BigDecimal.valueOf(2.32));
        Cup cup = AddressCupUtilities.cupEntityToDto(cupEntity);

        Assertions.assertEquals(1, cup.getCup_id());
    }


    @Test
    void dtoToAddressEntity() {
        Address address = new Address(1, "abc", 1, "Kraków", "43-300");
        AddressEntity addressEntity = AddressCupUtilities.dtoToAddressEntity(address);

        Assertions.assertEquals(1L, addressEntity.getAddress_id());
    }

    @Test
    void dtoToCupEntity() {
        Cup cup = new Cup(1, "blue", "circle", BigDecimal.valueOf(2.32));
        CupEntity cupEntity = AddressCupUtilities.dtoToCupEntity(cup);

        Assertions.assertEquals(1L, cupEntity.getCup_id());
    }
}