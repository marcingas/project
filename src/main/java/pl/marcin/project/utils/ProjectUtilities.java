package pl.marcin.project.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.entityService.AddressEntityReactiveService;
import pl.marcin.project.entityService.CustomerEntityReactiveService;
import pl.marcin.project.model.Address;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class ProjectUtilities {
    @Autowired
    private final AddressEntityReactiveService addressEntityReactiveService;
    private final CustomerEntityReactiveService customerEntityReactiveService;

    public ProjectUtilities(AddressEntityReactiveService addressEntityReactiveService, CustomerEntityReactiveService customerEntityReactiveService) {
        this.addressEntityReactiveService = addressEntityReactiveService;
        this.customerEntityReactiveService = customerEntityReactiveService;
    }


    public Address addressEntityToDto(AddressEntity addressEntity) {
        Address address = new Address();
        address.setAddress_id(addressEntity.getAddress_id().intValue());
        address.setCode(addressEntity.getCode());
        address.setTown(addressEntity.getTown());
        address.setStreet(addressEntity.getStreet());
        address.setNumber(addressEntity.getNumber());
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
        cup.setPrice(cupEntity.getPrice());
        cup.setCup_id(cupEntity.getCup_id().intValue());
        cup.setColor(cupEntity.getColor());
        cup.setShape(cupEntity.getShape());
        return cup;
    }

    public List<Cup> cupEntityListToDto(List<CupEntity> cupEntities) {
        List<Cup> cups = new ArrayList<>();
        for (var cup : cupEntities) cups.add(cupEntityToDto(cup));
        return cups;
    }

    public List<CupEntity> dtoToCupEntityList(List<Cup> cups) {
        List<CupEntity> cupEntities = new ArrayList<>();
        for (var cup : cups) cupEntities.add(dtoToCupEntity(cup));
        return cupEntities;
    }

    public Purchase purchaseEntityToDto(PurchaseEntity purchaseEntity) {
        Purchase purchase = new Purchase();
        Customer customer = customerEntityToDto(customerEntityReactiveService
                .getCustomerById(purchaseEntity.getCustomerId()).block());

        purchase.setPurchaseCost(purchaseEntity.getPurchaseCost());
        purchase.setCups(cupEntityListToDto(purchaseEntity.getCups()));
        purchase.setCustomer(customer);
        purchase.setId(purchaseEntity.getId().intValue());
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
        addressEntity.setAddress_id(address.getAddress_id().longValue());
        addressEntity.setTown(address.getTown());
        addressEntity.setNumber(address.getNumber());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setCode(address.getCode());
        return addressEntity;
    }

    public CupEntity dtoToCupEntity(Cup cup) {
        CupEntity cupEntity = new CupEntity();
        cupEntity.setPrice(cup.getPrice());
        cupEntity.setCup_id(cup.getCup_id().longValue());
        cupEntity.setColor(cup.getColor());
        cupEntity.setShape(cup.getShape());
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
        purchaseEntity.setPurchaseCost(purchase.getPurchaseCost());
        purchaseEntity.setId(purchase.getId().longValue());
        purchaseEntity.setCups(dtoToCupEntityList(purchase.getCups()));
        Long customerId = purchase.getCustomer().getId().longValue();
        purchaseEntity.setCustomerId(customerId);
        return purchaseEntity;
    }
}
