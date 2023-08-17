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
                .flatMap(this::customerEntityToDto);
    }

    public Mono<Customer> getCustomerById(Integer id) {
        return customerEntityRepositoryReactive.findById(id.longValue())
                .flatMap(this::customerEntityToDto);
    }

    public Mono<Customer> saveCustomer(Mono<Customer> customerMono) {
        return customerMono.flatMap(this::dtoToCustomerEntity)
                .flatMap(customerEntityRepositoryReactive::save)
                .flatMap(this::customerEntityToDto);
    }

    public Mono<Customer> updateCustomer(Mono<Customer> customerMono, Integer customerId) {
        return customerEntityRepositoryReactive.findById(customerId.longValue())
                .flatMap(existingCustomerEntity -> {
                    return customerMono.flatMap(updatedCustomer -> {
                        return dtoToCustomerEntity(updatedCustomer)
                                .map(updatedEntity -> {
                                    updatedEntity.setId(customerId.longValue());
                                    return updatedEntity;
                                })
                                .flatMap(customerEntityRepositoryReactive::save)
                                .thenReturn(updatedCustomer);
                    });
                });
    }
//
//    public Mono<Customer> updateCustomersAddressId(Mono<Customer> customerMono, Integer id, Integer addressId) {
//        return customerEntityRepositoryReactive.findById(id.longValue())
//                .flatMap(existingCustomerEntity -> dtoToCustomerEntity(existingCustomerEntity))
//                .doOnNext(customerEntity -> customerEntity.setId(id.longValue()))
//                .doOnNext(customerEntity -> customerEntity.setAddressId(addressId.longValue()))
//                .flatMap(customerEntityRepositoryReactive::save)
//                .map(this::customerEntityToDto);
//    }

    public Mono<Void> deleteCustomer(Integer id) {
        return customerEntityRepositoryReactive.findById(id.longValue())
                .flatMap(
                        customerEntity -> {
                            Mono<Void> deleteAddress = addressEntityReactiveService.deleteAddress(customerEntity
                                    .getAddressId().intValue());
                            Mono<Void> deleteCustomer = customerEntityRepositoryReactive.deleteById(id.longValue());
                            return deleteAddress.then(deleteCustomer);
                        });
    }


    public Mono<Customer> customerEntityToDto(CustomerEntity customerEntity) {
        Customer customer = new Customer();
        Long addressId = customerEntity.getAddressId();

        return addressEntityReactiveService.getAddressById(addressId.intValue())
                .map(address -> {
                    customer.setAddress(address);
                    customer.setName(customerEntity.getName());
                    customer.setSurname(customerEntity.getSurname());
                    return customer;
                });


    }

    public Mono<CustomerEntity> dtoToCustomerEntity(Customer customer) {
        return addressEntityReactiveService.getAddressById(customer.getAddress().getAddressId().intValue())
                .map(address -> {
                    CustomerEntity customerEntity = new CustomerEntity();
                    customerEntity.setName(customer.getName());
                    customerEntity.setSurname(customer.getSurname());
                    customerEntity.setAddressId(address.getAddressId().longValue());
                    return customerEntity;
                });
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
