package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.AddressEntityRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CupEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressEntityService {
    private AddressEntityRepository addressEntityRepository;

    public AddressEntity getAddress(Long addressId) {
        Optional<AddressEntity> addressEntity = addressEntityRepository.findById(addressId);
        if (addressEntity.isPresent()) {
            return addressEntity.get();
        } else {
            throw new RuntimeException("Address not found with id: " + addressId);
        }
    }


    public AddressEntity addAddress(AddressEntity addressEntity) {
        return addressEntityRepository.save(addressEntity);
    }

    public AddressEntity updateAddress(AddressEntity addressEntity) {
        return addressEntityRepository.save(addressEntity);
    }

    public void deleteAddress(Long addressId) {
        addressEntityRepository.deleteById(addressId);
    }
}
