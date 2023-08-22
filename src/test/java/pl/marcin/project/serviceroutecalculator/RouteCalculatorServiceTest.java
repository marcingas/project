package pl.marcin.project.serviceroutecalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.service.GeoService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteCalculatorServiceTest {
    @Mock
    GeoService geoService;
    @Mock
    DijkstraAlgorithmService dijkstraAlgorithmService;
    @InjectMocks
    RouteCalculatorService routeCalculatorService;


    @Test
    void addressDataGeneratorTest() {
        //given
        CustomerEntity customer = new CustomerEntity(1L, "John", "Dowe", new AddressEntity());
        AddressData addressData = new AddressData("30-300", "Krakow", "Krakowska", 1);
        when(geoService.generateAddressData(customer)).thenReturn(addressData);

        //when
        AddressData addressDataResult = routeCalculatorService.addressDataGenerator(customer);

        //then
        assertEquals(addressData, addressDataResult);
        verify(geoService, times(1)).generateAddressData(customer);
    }

    @Test
    void distanceTest() {
        //given
        AddressData addressDataStart = new AddressData("30-300", "Krakow", "Krakowska", 1);
        AddressData addressDataEnd = new AddressData("30-300", "Krakow", "Malinowska", 1);
        when(geoService.countDistanceBetweenClients(addressDataStart, addressDataEnd)).thenReturn(5000);

        //when
        Integer distanceResult = routeCalculatorService.distance(addressDataStart, addressDataEnd);

        //then
        assertEquals(5000, distanceResult);
        verify(geoService, times(1)).countDistanceBetweenClients(addressDataStart, addressDataEnd);
    }

    @Test
    void calculateBestRouteToClientTest() {
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

        List<List<Client>> adjecencyList = new ArrayList<>();
        adjecencyList.add(shopAdj);
        adjecencyList.add(client1);
        adjecencyList.add(client2);
        adjecencyList.add(client3);
        adjecencyList.add(client4);
        adjecencyList.add(client5);
        adjecencyList.add(client6);

        when(dijkstraAlgorithmService.dijkstraImplFindingShortestWay(adjecencyList, 0))
                .thenReturn(new int[]{0, 2, 3, 5, 5, 6, 6});

        //when
        int[] resultRoutes = routeCalculatorService.calculateBestRouteToClient(adjecencyList);

        //then
        assertArrayEquals(new int[]{0, 2, 3, 5, 5, 6, 6}, resultRoutes);
        verify(dijkstraAlgorithmService, times(1))
                .dijkstraImplFindingShortestWay(adjecencyList, 0);
    }
}