package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.AddressEntityReactiveRepository;
import pl.marcin.project.entity.AddressEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddressEntityReactiveService {
    private final AddressEntityReactiveRepository addressReactiveRepository;

    public Flux<AddressEntity> getAllAddresses() {
        Flux<AddressEntity> fluxAddresses = addressReactiveRepository.findAll();
        return fluxAddresses;
    }

    public Mono<AddressEntity> getAddressById(long id) {
        Mono<AddressEntity> monoAddressbyId = addressReactiveRepository.findById(id);
        return monoAddressbyId;
    }

}
