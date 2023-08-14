package pl.marcin.project.entityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CustomerEntityRepositoryReactive;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.model.Address;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerEntityReactiveService {
    @Autowired
    private final CustomerEntityRepositoryReactive customerEntityRepositoryReactive;
    @Autowired
    private final AddressEntityReactiveService addressEntityReactiveService;

    public CustomerEntityReactiveService(CustomerEntityRepositoryReactive customerEntityRepositoryReactive,
                                         AddressEntityReactiveService addressEntityReactiveService) {
        this.customerEntityRepositoryReactive = customerEntityRepositoryReactive;
        this.addressEntityReactiveService = addressEntityReactiveService;
    }

    public Flux<Customer> getAllCustomers() {
        return customerEntityRepositoryReactive.findAll()
                .map(this::customerEntityToDto);
    }

    public Mono<Customer> getCustomerById(Integer id) {
        return customerEntityRepositoryReactive.findById(id.longValue())
                .map(this::customerEntityToDto);
    }

    public Mono<Customer> saveCustomer(Mono<Customer> customerMono) {
        return customerMono.map(this::dtoToCustomerEntity)
                .flatMap(customerEntityRepositoryReactive::save)
                .map(this::customerEntityToDto);
    }

    public Mono<Customer> updateCustomer(Mono<Customer> customerMono, Integer id) {
        return customerEntityRepositoryReactive.findById(id.longValue())
                .flatMap(customerEntity -> customerMono.map(this::dtoToCustomerEntity))
                .doOnNext(customerEntity -> customerEntity.setId(id.longValue()))
                .flatMap(customerEntityRepositoryReactive::save)
                .map(this::customerEntityToDto);
    }

    public Mono<Void> deleteCustomer(Integer id) {
        return customerEntityRepositoryReactive.deleteById(id.longValue());
    }

    public Customer customerEntityToDto(CustomerEntity customerEntity) {
        Customer customer = new Customer();
        Long addressId = customerEntity.getAddressId();

        Address address = addressEntityReactiveService.getAddressById(addressId.intValue()).block();
        customer.setAddress(address);
        customer.setName(customerEntity.getName());
        customer.setSurname(customerEntity.getSurname());
        return customer;
    }

    public CustomerEntity dtoToCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId().longValue());
        customerEntity.setName(customer.getName());
        customerEntity.setSurname(customer.getSurname());
        Address address = customer.getAddress();
        customerEntity.setAddressId(address.getAddress_id().longValue());
        return customerEntity;
    }

    public Purchase purchaseEntityToDto(PurchaseEntity purchaseEntity) {
        Purchase purchase = new Purchase();
        Customer customer = getCustomerById(purchaseEntity.getCustomerId().intValue()).block();

        purchase.setPurchaseCost(purchaseEntity.getPurchaseCost());
        purchase.setCups(purchaseEntity.getCups());
        purchase.setCustomer(customer);
        purchase.setId(purchaseEntity.getId().intValue());
        return purchase;
    }

    public PurchaseEntity dtoToPurchaseEntity(Purchase purchase) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setPurchaseCost(purchase.getPurchaseCost());
        purchaseEntity.setId(purchase.getId().longValue());
        purchaseEntity.setCups(purchase.getCups());
        Long customerId = purchase.getCustomer().getId().longValue();
        purchaseEntity.setCustomerId(customerId);
        return purchaseEntity;
    }
}
