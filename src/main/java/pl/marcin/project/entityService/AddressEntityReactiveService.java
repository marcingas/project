package pl.marcin.project.entityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.AddressEntityReactiveRepository;
import pl.marcin.project.model.Address;
import pl.marcin.project.utils.AddressCupUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AddressEntityReactiveService {
    @Autowired
    private final AddressEntityReactiveRepository addressReactiveRepository;

    public AddressEntityReactiveService(AddressEntityReactiveRepository addressReactiveRepository) {
        this.addressReactiveRepository = addressReactiveRepository;
    }

    public Flux<Address> getAllAddresses() {
        return addressReactiveRepository.findAll()
                .map(AddressCupUtilities::addressEntityToDto);
    }

    public Mono<Address> getAddressById(Integer id) {
        return addressReactiveRepository.findById(id.longValue())
                .map(AddressCupUtilities::addressEntityToDto);
    }

    public Mono<Address> saveAddress(Mono<Address> address) {
        return address.map(AddressCupUtilities::dtoToAddressEntity)
                .flatMap(addressReactiveRepository::save)
                .map(AddressCupUtilities::addressEntityToDto);
    }

    public Mono<Address> updateAddress(Mono<Address> address, Integer id) {
        return addressReactiveRepository.findById(id.longValue())
                .flatMap(addressEntity -> address.map(AddressCupUtilities::dtoToAddressEntity))
                .doOnNext(addressEntity -> addressEntity.setAddress_id(id.longValue()))
                .flatMap(addressReactiveRepository::save)
                .map(AddressCupUtilities::addressEntityToDto);
    }

    public Mono<Void> deleteAddress(Integer id) {
        return addressReactiveRepository.deleteById(id.longValue());
    }


}
