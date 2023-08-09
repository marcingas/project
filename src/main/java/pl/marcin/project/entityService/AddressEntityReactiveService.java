package pl.marcin.project.entityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.AddressEntityReactiveRepository;
import pl.marcin.project.entity.AddressEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AddressEntityReactiveService {
    @Autowired
    private final AddressEntityReactiveRepository addressReactiveRepository;

    public AddressEntityReactiveService(AddressEntityReactiveRepository addressReactiveRepository) {
        this.addressReactiveRepository = addressReactiveRepository;
    }

    public Flux<AddressEntity> getAllAddresses() {
        Flux<AddressEntity> fluxAddresses = addressReactiveRepository.findAll();
        return fluxAddresses;
    }

    public Mono<AddressEntity> getAddressById(long id) {
        Mono<AddressEntity> monoAddressbyId = addressReactiveRepository.findById(id);
        return monoAddressbyId;
    }

    public Mono<AddressEntity> saveAddress(Mono<AddressEntity> addressEntityMono) {
        return addressEntityMono.flatMap(addressReactiveRepository::save);
    }

    public Mono<AddressEntity> updateAddress(Mono<AddressEntity> addressEntityMono) {
        return addressEntityMono.flatMap(addressReactiveRepository::save);
    }

    public Mono<Void> deleteAddress(Long id) {
        return addressReactiveRepository.deleteById(id);
    }


}
