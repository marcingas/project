package pl.marcin.project.routeservice;

import java.util.*;

public class RouteToClientService {




    public int[] findShortestRoadToEachClient(List<List<Client>> adjecencyList, int shopAdj) {
        int[] distanceAns = new int[adjecencyList.size()];
        Arrays.fill(distanceAns, Integer.MAX_VALUE);
        distanceAns[shopAdj] = 0;


        PriorityQueue<Client> minDistanceStorage = new PriorityQueue<>((x, y) -> x.getDistance() - y.getDistance());
        minDistanceStorage.add(new Client(shopAdj, 0));

        while (minDistanceStorage.size() != 0) {
            int currentClient = minDistanceStorage.peek().getClientNr();
            int totalDistance = minDistanceStorage.peek().getDistance();
            minDistanceStorage.poll();

            for (int neighbour = 0; neighbour < adjecencyList.get(currentClient).size(); neighbour++) {
                int neighbourClient = adjecencyList.get(currentClient).get(neighbour).getClientNr();
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

        int[] distanceAnswers = findShortestRoadToEachClient(adjList, shopAdj);

        List<Integer> path = new ArrayList<>();
        path.add(destinationClient);
        int tempDestClient = destinationClient;

        while (tempDestClient != shopAdj) {
            for (Client client : adjList.get(tempDestClient)) {
                int previousClient = client.getClientNr();
                int distanceToPrevious = client.getDistance();
                if (distanceAnswers[tempDestClient] == distanceAnswers[previousClient] + distanceToPrevious) {
                    path.add(previousClient);
                    tempDestClient = previousClient;
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
