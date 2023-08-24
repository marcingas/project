package pl.marcin.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.serviceentity.CustomerEntityService;
import pl.marcin.project.serviceroutecalculator.RouteCalculatorService;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    @Autowired
    RouteCalculatorService routeCalculatorService;
    @Autowired
    CustomerEntityService customerEntityService;

    @PostMapping("/{customerStartId}/{customerEndId}")
    public String sendDataForCountingDistance(@PathVariable Long customerStartId, @PathVariable Long customerEndId) {
        CustomerEntity customerStartFrom = customerEntityService.getCustomer(customerStartId);
        CustomerEntity customerToVisit = customerEntityService.getCustomer(customerEndId);
        AddressData addressDataStart = routeCalculatorService.addressDataGenerator(customerStartFrom);
        AddressData addressDataEnd = routeCalculatorService.addressDataGenerator(customerToVisit);

        Integer distance = routeCalculatorService.distance(addressDataStart, addressDataEnd);
        return "Shortest distance from Customer " + customerStartId + "  to Customer: " + customerEndId +
                " is " + (distance / 1000) + " km";
    }

}
