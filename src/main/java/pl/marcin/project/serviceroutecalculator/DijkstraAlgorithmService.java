package pl.marcin.project.serviceroutecalculator;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DijkstraAlgorithmService {
    public int[] dijkstraImplFindingShortestWay(List<List<Client>> adjecencyList, int clientId) {
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

    public List<Integer> printFullPath(List<List<Client>> adjList, int shopId, int destinationClientId) {
        int[] distanceAnswers = dijkstraImplFindingShortestWay(adjList, shopId);
        List<Integer> path = new ArrayList<>();
        path.add(destinationClientId);
        int tempDestClient = destinationClientId;

        while (tempDestClient != shopId) {
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
        System.out.println("Full path for shortest way (:" + distanceAnswers[destinationClientId] + "km) from shop" +
                "(shop=0, and accordingly client's numbers) to client: " + destinationClientId + " is: "
                + path);
        return path;
    }
}
