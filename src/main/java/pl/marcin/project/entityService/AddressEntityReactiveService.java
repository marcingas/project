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
        return addressReactiveRepository.findAll();
    }

    public Mono<AddressEntity> getAddressById(long id) {
        return addressReactiveRepository.findById(id);
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
