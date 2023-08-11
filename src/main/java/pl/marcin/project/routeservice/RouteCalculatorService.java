package pl.marcin.project.routeservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.service.GeoService;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Getter
@Setter
public class RouteCalculatorService {
    private final GeoService geoService;

    @Autowired
    public RouteCalculatorService(GeoService geoService) {
        this.geoService = geoService;
    }

    public int[] calculateBestRouteToClient(List<List<Client>> adjacencyList) {
        return dijkstraImplFindingShortestWay(adjacencyList, 0);
    }

    private int[] dijkstraImplFindingShortestWay(List<List<Client>> adjecencyList, int clientId) {
        int[] distanceAns = new int[adjecencyList.size()];
        Arrays.fill(distanceAns, Integer.MAX_VALUE);
        distanceAns[clientId] = 0;

        PriorityQueue<Client> minDistanceStorage = new PriorityQueue<>((x, y) -> x.getDistance() - y.getDistance());
        minDistanceStorage.add(new Client(clientId, 0));

        while (minDistanceStorage.size() != 0) {
            int currentClient = minDistanceStorage.peek().getClientId();
            int totalDistance = minDistanceStorage.peek().getDistance();
            minDistanceStorage.poll();

            for (int neighbour = 0; neighbour < adjecencyList.get((int) currentClient).size(); neighbour++) {
                int neighbourClient = adjecencyList.get(currentClient).get(neighbour).getClientId();
                int distance = adjecencyList.get(currentClient).get(neighbour).getDistance();
                if (distance + totalDistance < distanceAns[neighbourClient]) {
                    distanceAns[neighbourClient] = distance + totalDistance;
                    minDistanceStorage.add(new Client(neighbourClient, distanceAns[neighbourClient]));
                }
            }
        }
        return distanceAns;
    }

    public List<Integer> printFullPath(List<List<Client>> adjList, int shopAdj, int destinationClient) {
        int[] distanceAnswers = dijkstraImplFindingShortestWay(adjList, shopAdj);
        List<Integer> path = new ArrayList<>();
        path.add(destinationClient);
        int tempDestClient = destinationClient;

        while (tempDestClient != shopAdj) {
            for (Client client : adjList.get(tempDestClient)) {
                long previousClient = client.getClientId();
                int distanceToPrevious = client.getDistance();
                if (distanceAnswers[tempDestClient] == distanceAnswers[(int) previousClient] + distanceToPrevious) {
                    path.add((int) previousClient);
                    tempDestClient = (int) previousClient;
                    break;
                }
            }
        }
        Collections.reverse(path);
        System.out.println("Full path for shortest way (:" + distanceAnswers[destinationClient] + "km) from shop" +
                "(shop=0, and accordingly client's numbers) to client: " + destinationClient + " is: "
                + path);
        return path;
    }

    public Mono<AddressData> addressDataGenerator(CustomerEntity customerToVisit) {
        return geoService.generateAddressDataReactive(customerToVisit);
    }

    public Mono<Integer> distance(AddressData baseCustomerData, AddressData neighbourCustomerData) {
        return geoService.countDistanceBetweenClientsReactive(baseCustomerData, neighbourCustomerData);
    }
}
