package pl.marcin.project.entityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CustomerEntityRepositoryReactive;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.model.Address;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.utils.AddressCupUtilities;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerEntityReactiveService {
    @Autowired
    private final CustomerEntityRepositoryReactive customerEntityRepositoryReactive;
    @Autowired
    private final AddressEntityReactiveService addressEntityReactiveService;

    public CustomerEntityReactiveService(CustomerEntityRepositoryReactive customerEntityRepositoryReactive, AddressEntityReactiveService addressEntityReactiveService) {
        this.customerEntityRepositoryReactive = customerEntityRepositoryReactive;
        this.addressEntityReactiveService = addressEntityReactiveService;
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

    public Customer customerEntityToDto(CustomerEntity customerEntity) {
        Customer customer = new Customer();
        Long addressId = customerEntity.getAddressId();

        Address address = addressEntityReactiveService.getAddressById(addressId.intValue()).block();
        customer.setAddress(address);
        customer.setName(customerEntity.getName());
        customer.setSurname(customerEntity.getSurname());
        customer.setPurchaseHistory(purchaseEntityListToDto(customerEntity.getPurchaseHistory()));
        return customer;
    }

    public CustomerEntity dtoToCustomerEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId().longValue());
        customerEntity.setName(customer.getName());
        customerEntity.setSurname(customer.getSurname());
        Address address = customer.getAddress();
        customerEntity.setAddressId(address.getAddress_id().longValue());
        customerEntity.setPurchaseHistory(dtoToPurchaseEntityList(customer.getPurchaseHistory()));
        return customerEntity;
    }


    //customer service
    public Purchase purchaseEntityToDto(PurchaseEntity purchaseEntity) {
        Purchase purchase = new Purchase();
        Customer customer = customerEntityToDto(getCustomerById(purchaseEntity.getCustomerId()).block());

        purchase.setPurchaseCost(purchaseEntity.getPurchaseCost());
        purchase.setCups(AddressCupUtilities.cupEntityListToDto(purchaseEntity.getCups()));
        purchase.setCustomer(customer);
        purchase.setId(purchaseEntity.getId().intValue());
        return purchase;
    }

    public PurchaseEntity dtoToPurchaseEntity(Purchase purchase) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setPurchaseCost(purchase.getPurchaseCost());
        purchaseEntity.setId(purchase.getId().longValue());
        purchaseEntity.setCups(AddressCupUtilities.dtoToCupEntityList(purchase.getCups()));
        Long customerId = purchase.getCustomer().getId().longValue();
        purchaseEntity.setCustomerId(customerId);
        return purchaseEntity;
    }

    public List<Purchase> purchaseEntityListToDto(List<PurchaseEntity> purchaseEntityList) {
        List<Purchase> purchases = new ArrayList<>();
        for (var purchase : purchaseEntityList) {
            purchases.add(purchaseEntityToDto(purchase));
        }
        return purchases;
    }

    public List<PurchaseEntity> dtoToPurchaseEntityList(List<Purchase> purchaseList) {
        List<PurchaseEntity> entityPurchases = new ArrayList<>();
        for (var purchase : purchaseList) {
            entityPurchases.add(dtoToPurchaseEntity(purchase));
        }
        return entityPurchases;
    }


}
