package pl.marcin.project.entityService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityRepository;
import pl.marcin.project.database.CustomerEntityRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerEntityService {

    private final CustomerEntityRepository customerEntityRepository;


    public List<CustomerEntity> getAllCustomers() {
        return customerEntityRepository.findAll();
    }

    public CustomerEntity getCustomer(Long id) {
        return customerEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found by id " + id));
    }

    public CustomerEntity addCustomer(CustomerEntity customerEntity) {
        return customerEntityRepository.save(customerEntity);
    }

    public CustomerEntity updateCustomer(CustomerEntity customerEntity) {
        CustomerEntity existingCustomer = customerEntityRepository.findById(customerEntity.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        existingCustomer.setName(customerEntity.getName());
        existingCustomer.setSurname(customerEntity.getSurname());
        existingCustomer.setAddress(customerEntity.getAddress());
        return customerEntityRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        CustomerEntity existingCustomer = customerEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coustomer not Found"));
        customerEntityRepository.delete(existingCustomer);
    }

}


