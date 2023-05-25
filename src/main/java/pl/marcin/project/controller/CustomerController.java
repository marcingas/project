package pl.marcin.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entityService.CustomerEntityService;
import pl.marcin.project.model.Customer;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerEntityService customerEntityService;

    @GetMapping("/customers")
    public List<CustomerEntity> getCustomers() {
        return customerEntityService.getAllCustomers();
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerEntity> addCustomer(@RequestBody CustomerEntity customer) {
        customerEntityService.addCustomerEntity(customer);
        return new ResponseEntity<CustomerEntity>(customer, HttpStatus.CREATED);
    }
}
