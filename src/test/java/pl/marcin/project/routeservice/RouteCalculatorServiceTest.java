package pl.marcin.project.routeservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marcin.project.tomtomgeoservice.service.GeoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class RouteCalculatorServiceTest {
    @Autowired
    GeoService geoService;
    RouteCalculatorService routeCalculatorService = new RouteCalculatorService(geoService);

    public List<List<Client>> loadListOfAdjecencyClients() {

        List<Client> shopAdj = new ArrayList<>(List.of(new Client(1, 2), new Client(2, 3)));
        List<Client> client1 = new ArrayList<>(List.of(new Client(0, 2), new Client(2, 4),
                new Client(4, 3), new Client(5, 4)));
        List<Client> client2 = new ArrayList<>(List.of(new Client(0, 3), new Client(1, 4),
                new Client(3, 2), new Client(4, 2)));
        List<Client> client3 = new ArrayList<>(List.of(new Client(2, 2), new Client(6, 1)));
        List<Client> client4 = new ArrayList<>(List.of(new Client(2, 2), new Client(6, 1),
                new Client(5, 1), new Client(1, 3)));
        List<Client> client5 = new ArrayList<>(List.of(new Client(1, 4), new Client(6, 2),
                new Client(4, 1)));
        List<Client> client6 = new ArrayList<>(List.of(new Client(3, 1), new Client(4, 1),
                new Client(5, 2), new Client(2, 3)));

        List<List<Client>> adjecencyList = new ArrayList<>();
        adjecencyList.add(shopAdj);
        adjecencyList.add(client1);
        adjecencyList.add(client2);
        adjecencyList.add(client3);
        adjecencyList.add(client4);
        adjecencyList.add(client5);
        adjecencyList.add(client6);

        return adjecencyList;
    }

    @Test
    void findShortestRoadToEachClient() {
        //given
        int[] expectedRoute = new int[]{0, 2, 3, 5, 5, 6, 6};
        List<List<Client>> adjecencyList = loadListOfAdjecencyClients();
        int shop = 0;
        //when
        int[] route = routeCalculatorService.calculateBestRouteToClient(adjecencyList);
        //then:
        System.out.println(Arrays.toString(route));
        assertArrayEquals(expectedRoute, route);


    }

//    @Test
//    void printFullPath() {
//        //given
//        int[] expectedRoute = new int[]{0, 2, 3, 5, 5, 6, 6};
//        List<List<Client>> adjecencyList = loadListOfAdjecencyClients();
//        int shop = 0;
//        int targetClient = 3;
//        List<Integer> expectedAns = new ArrayList<>(List.of(0, 2, 3));
//        int[] route = routeToClientService.findShortestRoadToEachClient(adjecencyList, shop);
//        //when
//        List<Integer> ans = routeToClientService.printFullPath(adjecencyList, shop, targetClient);
//        //then
//        assertEquals(expectedAns, ans);
//
//    }

    @Test
    void calculate() {
    }

//    @Test
//    void addNeighbour() {
//        //given
//        CustomerEntity customer1 = new CustomerEntity("Jan", "Kowalski",
//                new AddressEntity("Komonieckiego", 10, "Żywiec", "34-300"));
//        List<CustomerEntity> neighboursOfShop = new ArrayList<>();
//        routeToClientService.addNeighbour(customer1, neighboursOfShop);
//    }

//    @Test
//    void addCustomerWithNeighbours() throws Exception {
//        //given
//        CustomerEntity customer1 = new CustomerEntity("Jan", "Kowalski",
//                new AddressEntity("Komonieckiego", 10, "Żywiec", "34-300"));
//        List<CustomerEntity> neighboursOfShop = new ArrayList<>();
//        routeToClientService.addNeighbour(customer1, neighboursOfShop);
//        ;
//        //when:
//        routeToClientService.addCustomerWithNeighbours(routeToClientService.shop,
//                neighboursOfShop, routeToClientService.getAdjacencyList());
//    }
}