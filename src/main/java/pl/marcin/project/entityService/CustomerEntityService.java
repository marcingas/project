package pl.marcin.project.entityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CustomerEntityRepository;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;

import java.util.List;

@Service
public class CustomerEntityService {
    @Autowired
    CustomerEntityRepository customerEntityRepository;

    public CustomerEntityService(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }

    public String addCustomerEntity(CustomerEntity customerEntity) {
        customerEntityRepository.save(customerEntity);
        return "Customer " + customerEntity + " saved";
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerEntityRepository.findAll();
    }
}
