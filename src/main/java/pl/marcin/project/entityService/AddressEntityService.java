package pl.marcin.project.entityService;

import jakarta.persistence.EntityNotFoundException;
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
    private final AddressEntityRepository addressEntityRepository;

    public List<AddressEntity> getAddresses() {
        return addressEntityRepository.findAll();
    }

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
        AddressEntity existingAddress = addressEntityRepository.findById(addressEntity.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException("No address found"));
        existingAddress.setNumber(addressEntity.getNumber());
        existingAddress.setTown(addressEntity.getTown());
        existingAddress.setStreet(addressEntity.getStreet());
        existingAddress.setCode(addressEntity.getCode());

        return addressEntityRepository.save(existingAddress);
    }

    public void deleteAddress(Long addressId) {
        AddressEntity existingAddress = addressEntityRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("No address found"));
        addressEntityRepository.delete(existingAddress);
    }
}
