package pl.marcin.project.utils;

import org.springframework.stereotype.Component;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.model.Address;
import pl.marcin.project.model.Cup;

@Component
public class AddressCupUtilities {

    public static Address addressEntityToDto(AddressEntity addressEntity) {
        Address address = new Address();
        address.setAddress_id(addressEntity.getAddress_id().intValue());
        address.setCode(addressEntity.getCode());
        address.setTown(addressEntity.getTown());
        address.setStreet(addressEntity.getStreet());
        address.setNumber(addressEntity.getNumber());
        return address;
    }

    public static AddressEntity dtoToAddressEntity(Address address) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress_id(address.getAddress_id().longValue());
        addressEntity.setTown(address.getTown());
        addressEntity.setNumber(address.getNumber());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setCode(address.getCode());
        return addressEntity;
    }

    public static Cup cupEntityToDto(CupEntity cupEntity) {
        Cup cup = new Cup();
        cup.setPrice(cupEntity.getPrice());
        cup.setCup_id(cupEntity.getCup_id().intValue());
        cup.setColor(cupEntity.getColor());
        cup.setShape(cupEntity.getShape());
        return cup;
    }

    public static CupEntity dtoToCupEntity(Cup cup) {
        CupEntity cupEntity = new CupEntity();
        cupEntity.setPrice(cup.getPrice());
        cupEntity.setCup_id(cup.getCup_id().longValue());
        cupEntity.setColor(cup.getColor());
        cupEntity.setShape(cup.getShape());
        return cupEntity;
    }




}
