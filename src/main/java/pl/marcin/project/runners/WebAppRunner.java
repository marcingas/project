package pl.marcin.project.runners;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.marcin.project.ProjectApplication;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.entityService.CustomerEntityService;

import java.util.ArrayList;
import java.util.List;

public class WebAppRunner implements AppRunner {

    @Override
    public void runApplication() {
        ConfigurableApplicationContext context = SpringApplication.run(ProjectApplication.class);
        var service = context.getBean(CustomerEntityService.class);
        createCustomer(service);
        updateCustomer(service, 1L);
        getAllCustomers(service);
        getCustomerById(service, 2L);
        deleteCustomerWithId(service, 1L);


    }

    private void deleteCustomerWithId(CustomerEntityService service, long id) {
        service.deleteCustomer(id);
        System.out.println("Customer with id: " + id + " deleted from database");
    }

    private void getCustomerById(CustomerEntityService service, Long id) {
        CustomerEntity customer = service.getCustomer(id);
        System.out.println("Searched customer: \n" + customer);
    }

    private void getAllCustomers(CustomerEntityService service) {
        List<CustomerEntity> customers = service.getAllCustomers();
        for (var cust : customers) {
            System.out.println(cust);
        }
    }

    private void updateCustomer(CustomerEntityService service, Long id) {
        CustomerEntity customer = service.getCustomer(id);
        customer.setName("Joe");
        customer.setSurname("Biden");
        service.updateCustomer(customer);
        System.out.println("Customer " + customer.getName() + " " + customer.getSurname() + " updated");
    }

    private void createCustomer(CustomerEntityService service) {
        CustomerEntity customer1 = new CustomerEntity("Donald", "Trump",
                new AddressEntity("Tower Bridge", 12, "New York", "001212"),
                new ArrayList<PurchaseEntity>());
        service.addCustomer(customer1);
        System.out.println("Customer with id: " + customer1.getCustomer_id() + " saved");
    }
}
