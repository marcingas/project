package pl.marcin.project.entityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CustomerEntityRepositoryReactive;
import pl.marcin.project.entity.CustomerEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerEntityReactiveService {
    @Autowired
    private final CustomerEntityRepositoryReactive customerEntityRepositoryReactive;

    public CustomerEntityReactiveService(CustomerEntityRepositoryReactive customerEntityRepositoryReactive) {
        this.customerEntityRepositoryReactive = customerEntityRepositoryReactive;
    }

    public Flux<CustomerEntity> getAllCustomers() {
        return customerEntityRepositoryReactive.findAll();
    }

    public Mono<CustomerEntity> getCustomerById(long id) {
        return customerEntityRepositoryReactive.findById(id);
    }

    public Mono<CustomerEntity> saveCustomer(Mono<CustomerEntity> customerEntityMono) {
        return customerEntityMono.flatMap(customerEntityRepositoryReactive::save);
    }

    public Mono<CustomerEntity> updateCustomer(Mono<CustomerEntity> customerEntityMono) {
        return customerEntityMono.flatMap(customerEntityRepositoryReactive::save);
    }

    public Mono<Void> deleteCustomer(Long id) {
        return customerEntityRepositoryReactive.deleteById(id);
    }

}
