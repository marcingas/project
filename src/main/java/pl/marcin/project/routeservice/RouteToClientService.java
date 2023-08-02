package pl.marcin.project.routeservice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.model.Customer;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteType;
import pl.marcin.project.tomtomgeoservice.routingmodel.TravelMode;
import pl.marcin.project.tomtomgeoservice.service.GeoService;

import java.util.*;

@Service
@Getter
@Setter
public class RouteToClientService {
    private final GeoService geoService;
    private List<List<Client>> adjacencyList;
    CustomerEntity shop;

    @Autowired
    public RouteToClientService(GeoService geoService) {

        this.geoService = geoService;
        adjacencyList = new ArrayList<>();
        shop = new CustomerEntity("shop", "Żywiec",
                new AddressEntity("Sienkiewicza", 1, "Żywiec", "34-300"));

    }

    public int[] calculate(List<List<Client>> adjacencyList) {
        return findShortestRoadToEachClient(adjacencyList,
                0);
    }


    public List<CustomerEntity> addNeighbour(CustomerEntity neighbour, List<CustomerEntity> neighbours) {
        neighbours.add(neighbour);
        return neighbours;
    }

    public List<List<Client>> addCustomerWithNeighbours(CustomerEntity customer,
                                                        List<CustomerEntity> neighbours,
                                                        List<List<Client>> adjacencyList) throws Exception {

        if (adjacencyList == null) adjacencyList = new ArrayList<>();
        adjacencyList.add(customerAdjecencyListGenerator(customer, neighbours));
        return adjacencyList;

    }


    private List<Client> customerAdjecencyListGenerator(CustomerEntity customer,
                                                        List<CustomerEntity> neighbours) throws Exception {
        List<Client> adjecencyList = new ArrayList<>();
        AddressData addressData = addressDataGenerator(customer);
        for (CustomerEntity neighbour : neighbours) {
            AddressData neighbourAddressData = addressDataGenerator(neighbour);
            adjecencyList.add(new Client(customer.getCustomer_id(), distance(addressData, neighbourAddressData)));

        }
        return adjecencyList;
    }

    private int distance(AddressData baseCustomerData, AddressData neighbourCustomerData) throws Exception {
        return geoService.countDistanceBetweenClients(baseCustomerData, neighbourCustomerData);

    }

    private AddressData addressDataGenerator(CustomerEntity customerToVisit) {

        Long id = customerToVisit.getCustomer_id();
        String code = customerToVisit.getAddress().getCode();
        String town = customerToVisit.getAddress().getTown();
        String street = customerToVisit.getAddress().getStreet();
        int number = customerToVisit.getAddress().getNumber();
        return new AddressData(code, town, street, number);
    }


    public int[] findShortestRoadToEachClient(List<List<Client>> adjecencyList, long shopAdj) {
        int[] distanceAns = new int[adjecencyList.size()];
        Arrays.fill(distanceAns, Integer.MAX_VALUE);
        distanceAns[(int) shopAdj] = 0;


        PriorityQueue<Client> minDistanceStorage = new PriorityQueue<>((x, y) -> x.getDistance() - y.getDistance());
        minDistanceStorage.add(new Client(shopAdj, 0));

        while (minDistanceStorage.size() != 0) {
            long currentClient = minDistanceStorage.peek().getClientNr();
            int totalDistance = minDistanceStorage.peek().getDistance();
            minDistanceStorage.poll();

            for (int neighbour = 0; neighbour < adjecencyList.get((int) currentClient).size(); neighbour++) {
                long neighbourClient = adjecencyList.get((int) currentClient).get(neighbour).getClientNr();
                int distance = adjecencyList.get((int) currentClient).get(neighbour).getDistance();
                if (distance + totalDistance < distanceAns[(int) neighbourClient]) {
                    distanceAns[(int) neighbourClient] = distance + totalDistance;
                    minDistanceStorage.add(new Client(neighbourClient, distanceAns[(int) neighbourClient]));
                }
            }
        }
        return distanceAns;
    }

    public List<Integer> printFullPath(List<List<Client>> adjList, int shopAdj, int destinationClient) {

        int[] distanceAnswers = findShortestRoadToEachClient(adjList, shopAdj);

        List<Integer> path = new ArrayList<>();
        path.add(destinationClient);
        int tempDestClient = destinationClient;

        while (tempDestClient != shopAdj) {
            for (Client client : adjList.get(tempDestClient)) {
                long previousClient = client.getClientNr();
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

}
