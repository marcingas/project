package pl.marcin.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entityService.AddressEntityService;
import pl.marcin.project.entityService.CustomerEntityService;
import pl.marcin.project.request.CustomerRequest;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerEntityController {
    @Autowired
    private final CustomerEntityService customerEntityService;

    @PostMapping("/add")
    public CustomerEntity addCustomer(@RequestBody CustomerRequest customer) {
        AddressEntity address = new AddressEntity();
        address.setCode(customer.getCode());
        address.setTown(customer.getTown());
        address.setStreet(customer.getStreet());
        address.setNumber(customer.getNumber());

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setSurname(customer.getSurname());
        customerEntity.setAddress(address);
        return customerEntityService.addCustomer(customerEntity);
    }

    @GetMapping
    public List<CustomerEntity> getCustomers() {
        return customerEntityService.getAllCustomers();
    }
}
