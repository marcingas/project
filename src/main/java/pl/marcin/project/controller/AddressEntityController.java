package pl.marcin.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entityService.AddressEntityService;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressEntityController {
    @Autowired
    private final AddressEntityService addressEntityService;

    @GetMapping
    public List<AddressEntity> findAllAddresses() {
        return addressEntityService.getAddresses();
    }

    @GetMapping("/{addressID}")
    public AddressEntity getAddress(@PathVariable Long addressId) {
        return addressEntityService.getAddress(addressId);
    }

    @DeleteMapping("/{addressId}/delete")
    public Long deleteAddressById(@PathVariable Long addressId) {
        addressEntityService.deleteAddress(addressId);
        return addressId;
    }

    @PostMapping("/save")
    public AddressEntity saveAddress(@RequestBody AddressEntity address) {
        return addressEntityService.addAddress(address);
    }

    @PutMapping("/{addressId}/update")
    public AddressEntity updateAddressById(@PathVariable Long addressId, @RequestBody AddressEntity address) {
        AddressEntity addressForUpdate = addressEntityService.getAddress(addressId);
        addressForUpdate.setCode(address.getCode());
        addressForUpdate.setTown(address.getTown());
        addressForUpdate.setStreet(address.getStreet());
        addressForUpdate.setNumber(address.getNumber());

        return addressEntityService.updateAddress(addressForUpdate);
    }


}
