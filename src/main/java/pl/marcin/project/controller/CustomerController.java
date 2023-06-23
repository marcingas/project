package pl.marcin.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.database.AddressEntityRepository;
import pl.marcin.project.database.CustomerEntityRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.entityService.CustomerEntityService;
import pl.marcin.project.request.CustomerRequest;
import pl.marcin.project.request.PurchaseRequest;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerEntityService customerEntityService;
    private final AddressEntityRepository addressEntityRepository;
    private final CustomerEntityRepository customerEntityRepository;

    @GetMapping("/customers")
    public List<CustomerEntity> getCustomers() {
        return customerEntityService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerEntity getCustomer(@PathVariable Long id) {
        return customerEntityService.getCustomer(id);
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerEntity> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        AddressEntity address = new AddressEntity();
        address.setStreet(customerRequest.getStreet());
        address.setTown(customerRequest.getTown());
        address.setNumber(customerRequest.getNumber());
        address.setCode(customerRequest.getCode());
        address = addressEntityRepository.save(address);

        CustomerEntity customer = new CustomerEntity();
        customer.setName((customerRequest.getName()));
        customer.setSurname(customerRequest.getSurname());
        customer.setAddress(address);
        customer = customerEntityRepository.save(customer);

        customerEntityService.addCustomer(customer);
            return new ResponseEntity<CustomerEntity>(customer, HttpStatus.CREATED);
    }


    @PutMapping("customers/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(
            @PathVariable Long id, @Valid @RequestBody CustomerRequest customerRequest) {
        AddressEntity address = new AddressEntity();
        address.setAddress_id(id);
        address.setStreet(customerRequest.getStreet());
        address.setTown(customerRequest.getTown());
        address.setNumber(customerRequest.getNumber());
        address.setCode(customerRequest.getCode());
        address = addressEntityRepository.save(address);

        CustomerEntity customer = new CustomerEntity();
        customer.setCustomer_id(id);
        customer.setName((customerRequest.getName()));
        customer.setSurname(customerRequest.getSurname());
        customer.setAddress(address);
        customer = customerEntityRepository.save(customer);


        return new ResponseEntity<CustomerEntity>(customerEntityService.updateCustomer(customer), HttpStatus.OK);
    }

    @DeleteMapping("/customers")
    public ResponseEntity<HttpStatus> deleteCustomer(@RequestParam Long id) {
        customerEntityService.deleteCustomer(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
