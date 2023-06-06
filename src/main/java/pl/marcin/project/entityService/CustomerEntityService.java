package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityRepository;
import pl.marcin.project.database.CustomerEntityRepository;
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
        Optional<CustomerEntity> customerEntity = customerEntityRepository.findById(id);
        if (customerEntity.isPresent()) {
            return customerEntity.get();
        } else {
            throw new RuntimeException("Customer not found by id: " + id);
        }
    }

    public CustomerEntity addCustomer(CustomerEntity customerEntity) {
        return customerEntityRepository.save(customerEntity);
    }

    public CustomerEntity updateCustomer(CustomerEntity customerEntity) {
        return customerEntityRepository.save(customerEntity);
    }

    public void deleteCustomer(Long id) {
        customerEntityRepository.deleteById(id);
    }

}


