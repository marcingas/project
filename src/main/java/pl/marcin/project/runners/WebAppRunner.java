package pl.marcin.project.runners;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.marcin.project.ProjectApplication;

public class WebAppRunner implements AppRunner {

    @Override
    public void runApplication() {
        ConfigurableApplicationContext context = SpringApplication.run(ProjectApplication.class);

//        var customerService = context.getBean(CustomerEntityReactiveService.class);
//        var mapService = context.getBean(GeoService.class);
//        var routeService = context.getBean(RouteCalculatorService.class);


//        try {
//            findRoute(routeService);
////            findLocation(mapService);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        createCustomer(customerService);
//        updateCustomer(customerService, 5L);
//        getAllCustomers(customerService);
//        getCustomerById(customerService, 5L);
//        deleteCustomerWithId(customerService, 6L);

//        var cupService = context.getBean(CupEntityReactiveService.class);
//        createCup(cupService);
//        updateCup(cupService, 1L);
//        getAllCup(cupService);
//        getCupById(cupService, 1L);
//        deleteCupWithId(cupService, 1L);

//    }
//
//    private void findRoute(RouteCalculatorService routeService) throws Exception {
//        List<CustomerEntity> shopNeighbors = new ArrayList<>();
//
//
//
//
//        AddressData customerAddressData = routeService.addressDataGenerator().block();
//        AddressData shopAdrData = routeService.addressDataGenerator(routeService.getShop()).block();
//
//        int distanceShopCustomer = routeService.distance(shopAdrData, customerAddressData).block();
//
//
//        List<Client> shopsNeighbours = new ArrayList<>(List.of(
//                new Client(customer1.getCustomer_id().intValue(), distanceShopCustomer)
//        ));
//        List<Client> customer1Neigh = new ArrayList<>(List.of(new Client(routeService.getShop()
//                .getCustomer_id().intValue(), distanceShopCustomer)));
//
//        List<Client> shopAdj = new ArrayList<>();
//        List<List<Client>> adjList = new ArrayList<>();
//        adjList.add(shopAdj);
//        adjList.add(customer1Neigh);
//
//
//        System.out.println(Arrays.toString(routeService.calculateBestRouteToClient(adjList)));
//    }
//
//    private void findLocation(GeoService mapService) throws Exception {
//        AddressData addressDataStart = new AddressData("34-300", "Żywiec", "Komonieckieg", 1);
//        AddressData addressDataEnd = new AddressData("43-300", "Bielsko-Biała", "Wyzwolenia", 1);
//        int distance = mapService.countDistanceBetweenClientsReactive(addressDataStart,
//                addressDataEnd);
//        int distance2 = mapService.countDistanceBetweenClientsReactive(addressDataStart,
//                addressDataEnd);
//        int distance3 = mapService.countDistanceBetweenClientsReactive(addressDataStart,
//                addressDataEnd);


//        System.out.println("distance between " + addressDataStart + " and: " + addressDataEnd + " is: " + distance);
//        System.out.println(distance2);
//        System.out.println(distance3);

//    }
//
//    private void deleteCupWithId(CupEntityService cupService, long id) {
//        cupService.deleteCup(id);
//        System.out.println("Cup with id: " + id + " deleted from database");
//    }
//
//    private void getCupById(CupEntityService cupService, long id) {
//        CupEntity cup = cupService.getCups(id);
//        System.out.println("Searched cup: \n" + cup);
//    }
//
//    private void getAllCups(CupEntityService cupService) {
//        List<CupEntity> cups = cupService.getAllCups();
//        for (var cup : cups) {
//            System.out.println(cup);
//        }
//    }
//
//    private void updateCup(CupEntityService cupService, long id) {
//        CupEntity cup = cupService.getCups(id);
//        cup.setColor("orange");
//        cup.setShape("circle");
//        cup.setPrice(BigDecimal.valueOf(12.44));
//        cupService.updateCup(cup);
//        System.out.println("Cup " + cup.getColor() + " " + cup.getShape() + " updated");
//    }
//
//    private void createCup(CupEntityService cupService) {
//        CupEntity cup1 = new CupEntity("white", "circle", BigDecimal.valueOf(12.34), new ArrayList<>());
//        cupService.addCup(cup1);
//        System.out.println("Cup with id: " + cup1.getCup_id() + " saved");
//    }
//
//    private void deleteCustomerWithId(CustomerEntityService service, long id) {
//        service.deleteCustomer(id);
//        System.out.println("Customer with id: " + id + " deleted from database");
//    }
//
//    private void getCustomerById(CustomerEntityService service, Long id) {
//        CustomerEntity customer = service.getCustomer(id);
//        System.out.println("Searched customer: \n" + customer);
//    }
//
//    private void getAllCustomers(CustomerEntityService service) {
//        List<CustomerEntity> customers = service.getAllCustomers();
//        for (var cust : customers) {
//            System.out.println(cust);
//        }
//    }
//
//    private void updateCustomer(CustomerEntityService service, Long id) {
//        CustomerEntity customer = service.getCustomer(id);
//        customer.setName("Joe");
//        customer.setSurname("Biden");
//        service.updateCustomer(customer);
//        System.out.println("Customer " + customer.getName() + " " + customer.getSurname() + " updated");
//    }
//
//    private void createCustomer(CustomerEntityService service) {
//        CustomerEntity customer1 = new CustomerEntity("Donald", "Trump",
//                new AddressEntity("Tower Bridge", 12, "New York", "001212"),
//                new ArrayList<PurchaseEntity>());
//        service.addCustomer(customer1);
//        CustomerEntity customer2 = new CustomerEntity("John", "Trump",
//                new AddressEntity("Tower Bridge", 1, "Chicago", "0012"),
//                new ArrayList<PurchaseEntity>());
//        service.addCustomer(customer1);
//        service.addCustomer(customer2);
//        System.out.println("Customer with id: " + customer1.getCustomer_id() + " saved");
    }
}
