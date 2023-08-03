package pl.marcin.project.runners;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.marcin.project.ProjectApplication;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.entityService.CupEntityService;
import pl.marcin.project.entityService.CustomerEntityService;
import pl.marcin.project.routeservice.Client;


import pl.marcin.project.routeservice.RouteToClientService;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.service.GeoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebAppRunner implements AppRunner {

    @Override
    public void runApplication() {
        ConfigurableApplicationContext context = SpringApplication.run(ProjectApplication.class);
        var customerService = context.getBean(CustomerEntityService.class);
        var mapService = context.getBean(GeoService.class);
        var routeService = context.getBean(RouteToClientService.class);

        try {
            findRoute(routeService);
//            findLocation(mapService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        createCustomer(customerService);
//        updateCustomer(customerService, 5L);
//        getAllCustomers(customerService);
//        getCustomerById(customerService, 5L);
//        deleteCustomerWithId(customerService, 6L);

        var cupService = context.getBean(CupEntityService.class);
//        createCup(cupService);
//        updateCup(cupService, 1L);
//        getAllCup(cupService);
//        getCupById(cupService, 1L);
//        deleteCupWithId(cupService, 1L);

    }

    private void findRoute(RouteToClientService routeService) throws Exception {

        List<CustomerEntity> shopNeighbors = new ArrayList<>();


        CustomerEntity customer1 = new CustomerEntity("Jan", "Kowalski",
                new AddressEntity("Wyzwolenia", 1, "Bielsko Biała", "43-300"));
        CustomerEntity customerPizzeria = new CustomerEntity("Piotr", "pizzeria",
                new AddressEntity("Beskidzka", 43, "Rychwałd", "34-322"));
        CustomerEntity customerZgwozdziami = new CustomerEntity("sklep", "gwozdzie",
                new AddressEntity("Plebańska", 1, "Jeleśnia", "34-340"));

        AddressData customerAddressData = routeService.addressDataGenerator(customer1);
        AddressData shopAdrData = routeService.addressDataGenerator(routeService.getShop());
        AddressData pizzeriaAddressData = routeService.addressDataGenerator(customerPizzeria);
        AddressData gwozdzieAddressData = routeService.addressDataGenerator(customerZgwozdziami);

        List<Client> shopsClients = new ArrayList<>(List.of(
                new Client(customer1.getCustomer_id(), routeService.distance(shopAdrData, customerAddressData)),
                new Client(customerPizzeria.getCustomer_id(), routeService.distance(shopAdrData, pizzeriaAddressData)),
                new Client(customerZgwozdziami.getCustomer_id(), routeService.distance(shopAdrData, gwozdzieAddressData))));

        List<Client> shopAdj = new ArrayList<>();
        List<List<Client>> adjList = new ArrayList<>();
        adjList.add(shopAdj);


        System.out.println(Arrays.toString(routeService.calculateBestRouteToClient(adjList)));
    }

    private void findLocation(GeoService mapService) throws Exception {
        AddressData addressDataStart = new AddressData("34-300", "Żywiec", "Komonieckieg", 1);
        AddressData addressDataEnd = new AddressData("43-300", "Bielsko-Biała", "Wyzwolenia", 1);
        int distance = mapService.countDistanceBetweenClientsReactive(addressDataStart,
                addressDataEnd);
        int distance2 = mapService.countDistanceBetweenClientsReactive(addressDataStart,
                addressDataEnd);
        int distance3 = mapService.countDistanceBetweenClientsReactive(addressDataStart,
                addressDataEnd);


        System.out.println("distance between " + addressDataStart + " and: " + addressDataEnd + " is: " + distance);
        System.out.println(distance2);
        System.out.println(distance3);

    }

    private void deleteCupWithId(CupEntityService cupService, long id) {
        cupService.deleteCup(id);
        System.out.println("Cup with id: " + id + " deleted from database");
    }

    private void getCupById(CupEntityService cupService, long id) {
        CupEntity cup = cupService.getCups(id);
        System.out.println("Searched cup: \n" + cup);
    }

    private void getAllCup(CupEntityService cupService) {
        List<CupEntity> cups = cupService.getAllCups();
        for (var cup : cups) {
            System.out.println(cup);
        }
    }

    private void updateCup(CupEntityService cupService, long id) {
        CupEntity cup = cupService.getCups(id);
        cup.setColor("orange");
        cup.setShape("circle");
        cup.setPrice(BigDecimal.valueOf(12.44));
        cupService.updateCup(cup);
        System.out.println("Cup " + cup.getColor() + " " + cup.getShape() + " updated");
    }

    private void createCup(CupEntityService cupService) {
        CupEntity cup1 = new CupEntity("white", "circle", BigDecimal.valueOf(12.34), new ArrayList<>());
        cupService.addCup(cup1);
        System.out.println("Cup with id: " + cup1.getCup_id() + " saved");
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
