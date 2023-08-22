package pl.marcin.project.serviceroutecalculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.service.GeoService;

import java.util.*;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class RouteCalculatorService {
    private final GeoService geoService;
    private final DijkstraAlgorithmService dijkstraAlgorithmService;

    public int[] calculateBestRouteToClient(List<List<Client>> adjacencyList) {
        return dijkstraAlgorithmService.dijkstraImplFindingShortestWay(adjacencyList, 0);
    }

    public AddressData addressDataGenerator(CustomerEntity customerToVisit) {
        return geoService.generateAddressData(customerToVisit);
    }

    public Integer distance(AddressData baseCustomerData, AddressData neighbourCustomerData) {
        return geoService.countDistanceBetweenClients(baseCustomerData, neighbourCustomerData);
    }
}
