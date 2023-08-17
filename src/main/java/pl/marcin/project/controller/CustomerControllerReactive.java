
package pl.marcin.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcin.project.entityService.CustomerEntityReactiveService;
import pl.marcin.project.model.Customer;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/customers")
public class CustomerControllerReactive {
    @Autowired
    private CustomerEntityReactiveService customerEntityReactiveService;

    @GetMapping
    private Flux<Customer> getCustomers() {
        return customerEntityReactiveService.getAllCustomers();
    }
}
