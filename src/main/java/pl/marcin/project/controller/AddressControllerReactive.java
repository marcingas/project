package pl.marcin.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcin.project.entityService.AddressEntityReactiveService;
import pl.marcin.project.model.Address;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/addresses")
public class AddressControllerReactive {
    @Autowired
    private AddressEntityReactiveService addressEntityReactiveService;

    @GetMapping
    public Flux<Address> getAddresses() {
        return addressEntityReactiveService.getAllAddresses();
    }
}
