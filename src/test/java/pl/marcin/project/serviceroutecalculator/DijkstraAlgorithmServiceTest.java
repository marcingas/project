package pl.marcin.project.serviceroutecalculator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraAlgorithmServiceTest {
    DijkstraAlgorithmService dijkstraAlgorithmService = new DijkstraAlgorithmService();

    @Test
    void dijkstraImplFindingShortestWay() {
        //given
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

        List<List<Client>> adjacencyList = new ArrayList<>();
        adjacencyList.add(shopAdj);
        adjacencyList.add(client1);
        adjacencyList.add(client2);
        adjacencyList.add(client3);
        adjacencyList.add(client4);
        adjacencyList.add(client5);
        adjacencyList.add(client6);
        Integer shopId = 0;
        //when
        int[] distances = dijkstraAlgorithmService.dijkstraImplFindingShortestWay(adjacencyList, shopId);

        //then
        assertNotNull(distances);
        assertEquals(7, distances.length);
        assertEquals(0, distances[0]);
        assertNotEquals(0, distances[1]);
        assertNotEquals(0, distances[2]);
        assertNotEquals(0, distances[3]);
        assertNotEquals(0, distances[4]);
        assertNotEquals(0, distances[5]);
        assertNotEquals(0, distances[6]);
    }

    @Test
    void printFullPath() {
        //given
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

        List<List<Client>> adjacencyList = new ArrayList<>();
        adjacencyList.add(shopAdj);
        adjacencyList.add(client1);
        adjacencyList.add(client2);
        adjacencyList.add(client3);
        adjacencyList.add(client4);
        adjacencyList.add(client5);
        adjacencyList.add(client6);
        Integer shopId = 0;
        Integer clientId = 4;

        //when
        List<Integer> fullPathResult = dijkstraAlgorithmService.printFullPath(adjacencyList, shopId, clientId);

        //then
        assertNotNull(fullPathResult);
        assertEquals(0, fullPathResult.get(0));
        assertNotEquals(0, fullPathResult.get(1));
        assertEquals(4, fullPathResult.get(2));
    }
}