package pl.marcin.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.request.CustomerRequest;
import pl.marcin.project.serviceentity.AddressEntityService;
import pl.marcin.project.serviceentity.CustomerEntityService;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerEntityController {
    @Autowired
    private final CustomerEntityService customerEntityService;
    @Autowired
    private final AddressEntityService addressEntityService;


    @PostMapping("/add")
    public CustomerEntity addCustomer(@RequestBody CustomerRequest customerRequest) {
        AddressEntity address = new AddressEntity();
        address.setCode(customerRequest.getCode());
        address.setTown(customerRequest.getTown());
        address.setStreet(customerRequest.getStreet());
        address.setNumber(customerRequest.getNumber());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerRequest.getName());
        customerEntity.setSurname(customerRequest.getSurname());
        customerEntity.setAddress(address);
        return customerEntityService.addCustomer(customerEntity);
    }

    @PutMapping("/{customerId}/update-address")
    public CustomerEntity updateAddress(@PathVariable Long customerId, @RequestBody AddressEntity address) {
        AddressEntity addressEntity = addressEntityService.addAddress(address);
        CustomerEntity customer = customerEntityService.getCustomer(customerId);
        customer.setAddress(addressEntity);
        return customerEntityService.updateCustomer(customer);
    }

    @PutMapping("/{customerId}/update-data")
    public CustomerEntity updateData(@PathVariable Long customerId, @RequestBody CustomerEntity customerEntity) {
        CustomerEntity customer = customerEntityService.getCustomer(customerId);
        customer.setName(customerEntity.getName());
        customer.setSurname(customerEntity.getSurname());
        return customerEntityService.updateCustomer(customer);
    }

    @GetMapping
    public List<CustomerEntity> getCustomers() {
        return customerEntityService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public CustomerEntity getCustomer(@PathVariable Long customerId) {
        return customerEntityService.getCustomer(customerId);
    }

    @DeleteMapping("/delete/{customerId}")
    public Long deleteCustomer(@PathVariable Long customerId) {
        customerEntityService.deleteCustomer(customerId);
        return customerId;
    }
}

