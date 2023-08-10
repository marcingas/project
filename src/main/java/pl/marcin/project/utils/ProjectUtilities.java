package pl.marcin.project.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.entityService.AddressEntityReactiveService;
import pl.marcin.project.model.Address;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class ProjectUtilities {
    @Autowired
    private final AddressEntityReactiveService addressEntityReactiveService;

    public ProjectUtilities(AddressEntityReactiveService addressEntityReactiveService) {
        this.addressEntityReactiveService = addressEntityReactiveService;
    }

    //customer and address ok check rest!
    public Address addressEntityToDto(AddressEntity addressEntity) {
        Address address = new Address();
        BeanUtils.copyProperties(addressEntity, address);
        return address;
    }

    public Customer customerEntityToDto(CustomerEntity customerEntity) {
        Customer customer = new Customer();
        Long addressId = customerEntity.getAddressId();
        AddressEntity addressMono = addressEntityReactiveService.getAddressById(addressId).block();

        customer.setAddress(addressEntityToDto(addressMono));
        customer.setName(customerEntity.getName());
        customer.setSurname(customerEntity.getSurname());
        customer.setPurchaseHistory(purchaseEntityListToDto(customerEntity.getPurchaseHistory()));
        return customer;
    }

    public Cup cupEntityToDto(CupEntity cupEntity) {
        Cup cup = new Cup();
        BeanUtils.copyProperties(cupEntity, cup);
        return cup;
    }

    public Purchase purchaseEntityToDto(PurchaseEntity purchaseEntity) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseEntity, purchase);
        return purchase;
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

    public AddressEntity dtoToAddressEntity(Address address) {
        AddressEntity addressEntity = new AddressEntity();
        BeanUtils.copyProperties(address, addressEntity);
        return addressEntity;
    }

    public CupEntity dtoToCupEntity(Cup cup) {
        CupEntity cupEntity = new CupEntity();
        BeanUtils.copyProperties(cup, cupEntity);
        return cupEntity;
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

    public PurchaseEntity dtoToPurchaseEntity(Purchase purchase) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        BeanUtils.copyProperties(purchase, purchaseEntity);
        return purchaseEntity;
    }
}
