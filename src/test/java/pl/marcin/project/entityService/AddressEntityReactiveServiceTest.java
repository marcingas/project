package pl.marcin.project.entityService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.AutoConfigureDataR2dbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import pl.marcin.project.database.AddressEntityReactiveRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.model.Address;
import pl.marcin.project.utils.AddressCupUtilities;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureDataR2dbc
@ActiveProfiles("test")
class AddressEntityReactiveServiceTest {
    @Autowired
    private AddressEntityReactiveService addressService;
    @Autowired
    private CustomerEntityReactiveService customerService;
    @Autowired
    private AddressCupUtilities utilities;

    @Autowired
    private AddressEntityReactiveRepository repository;

    @Autowired
    private ConnectionFactoryInitializer initializer;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://localhost:5432/testdb");
        registry.add("spring.r2dbc.username", () -> "postgres");
        registry.add("spring.r2dbc.password", () -> "postgres");
    }

    @Test
    void getAllAddresses() {
    }

    @Test
    void getAddressById() {
    }

    @Test
    void saveAddress() {
        Address address = new Address(1, "Krakowska", 1, "Kraków", "30-333");
        AddressEntity addressEntity = utilities.dtoToAddressEntity(address);
        Mono<AddressEntity> monoAddressEntity = Mono.just(addressEntity);
        addressService.saveAddress(monoAddressEntity);

        Mono<AddressEntity> expectedMonoAddressEntity = repository.findById(address.getAddress_id().longValue());
        AddressEntity addressGiven = monoAddressEntity.block();
        AddressEntity addressExpected = monoAddressEntity.block();
        Assertions.assertEquals(addressGiven.getAddress_id(), addressExpected.getAddress_id());
    }

    @Test
    void updateAddress() {
    }

    @Test
    void deleteAddress() {
    }
}