package pl.marcin.project.routeservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marcin.project.tomtomgeoservice.service.GeoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class RouteToClientServiceTest {
    @Autowired
    GeoService geoService;
    RouteToClientService routeToClientService = new RouteToClientService(geoService);

    public List<List<Client>> loadListOfAdjecencyClients() {

        List<Client> shopAdj = new ArrayList<>(List.of(new Client(1L, 2), new Client(2L, 3)));
        List<Client> client1 = new ArrayList<>(List.of(new Client(0L, 2), new Client(2L, 4),
                new Client(4L, 3), new Client(5L, 4)));
        List<Client> client2 = new ArrayList<>(List.of(new Client(0L, 3), new Client(1L, 4),
                new Client(3L, 2), new Client(4L, 2)));
        List<Client> client3 = new ArrayList<>(List.of(new Client(2L, 2), new Client(6L, 1)));
        List<Client> client4 = new ArrayList<>(List.of(new Client(2L, 2), new Client(6L, 1),
                new Client(5L, 1), new Client(1L, 3)));
        List<Client> client5 = new ArrayList<>(List.of(new Client(1L, 4), new Client(6L, 2),
                new Client(4L, 1)));
        List<Client> client6 = new ArrayList<>(List.of(new Client(3L, 1), new Client(4L, 1),
                new Client(5L, 2), new Client(2L, 3)));

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
        int[] route = routeToClientService.calculateBestRouteToClient(adjecencyList);
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