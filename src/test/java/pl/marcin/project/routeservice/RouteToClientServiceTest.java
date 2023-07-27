package pl.marcin.project.routeservice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteToClientServiceTest {
    RouteToClientService routeToClientService = new RouteToClientService();

    @Test
    void findShortestRoadToEachClient() {
        //given
        int[] expectedRoute = new int[]{0, 2, 3, 5, 5, 6, 6};
        List<List<Client>> adjecencyList = routeToClientService.loadListOfAdjecencyClients();
        int shop = 0;
        //when
        int[] route = routeToClientService.findShortestRoadToEachClient(adjecencyList, shop);
        //then:
        System.out.println(Arrays.toString(route));
        assertArrayEquals(expectedRoute, route);


    }

    @Test
    void printFullPath() {
        //given
        int[] expectedRoute = new int[]{0, 2, 3, 5, 5, 6, 6};
        List<List<Client>> adjecencyList = routeToClientService.loadListOfAdjecencyClients();
        int shop = 0;
        int targetClient = 3;
        List<Integer> expectedAns = new ArrayList<>(List.of(0, 2, 3));
        int[] route = routeToClientService.findShortestRoadToEachClient(adjecencyList, shop);
        //when
        List<Integer> ans = routeToClientService.printFullPath(adjecencyList, shop, targetClient);
        //then
        assertEquals(expectedAns, ans);

    }
}