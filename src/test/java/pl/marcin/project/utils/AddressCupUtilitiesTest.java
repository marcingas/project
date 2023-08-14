package pl.marcin.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.model.Address;
import pl.marcin.project.model.Cup;

import java.math.BigDecimal;

class AddressCupUtilitiesTest {


    @Test
    void addressEntityToDto() {
        AddressEntity addressEntity = new AddressEntity(1L, "abc", 1, "abc", "30-000");

        Address address = AddressCupUtilities.addressEntityToDto(addressEntity);
        Assertions.assertEquals(1, address.getAddressId());
    }

    @Test
    void cupEntityToDto() {
        CupEntity cupEntity = new CupEntity(1L, "ehite", "circle", BigDecimal.valueOf(2.32));
        Cup cup = AddressCupUtilities.cupEntityToDto(cupEntity);

        Assertions.assertEquals(1, cup.getCupId());
    }


    @Test
    void dtoToAddressEntity() {
        Address address = new Address(1, "abc", 1, "Kraków", "43-300");
        AddressEntity addressEntity = AddressCupUtilities.dtoToAddressEntity(address);

        Assertions.assertEquals(1L, addressEntity.getAddressId());
    }

    @Test
    void dtoToCupEntity() {
        Cup cup = new Cup(1, "blue", "circle", BigDecimal.valueOf(2.32));
        CupEntity cupEntity = AddressCupUtilities.dtoToCupEntity(cup);

        Assertions.assertEquals(1L, cupEntity.getCupId());
    }
}