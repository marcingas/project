package pl.marcin.project.servicetomtom.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.servicetomtom.geocodingmodel.Position;
import pl.marcin.project.servicetomtom.geocodingmodel.Result;
import pl.marcin.project.servicetomtom.routingmodel.Route;
import pl.marcin.project.servicetomtom.routingmodel.RouteAnswer;
import pl.marcin.project.servicetomtom.routingmodel.RouteData;
import pl.marcin.project.servicetomtom.routingmodel.Summary;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeoServiceTest {
    @Mock
    TomTomServiceFacade tomTomServiceFacade;
    @InjectMocks
    GeoService geoService;

    @Test
    void generateAddressData() {
        //given
        AddressEntity addressEntity = new AddressEntity(1L, "Krakowska", 1,
                "Kraków", "30-300");
        CustomerEntity customer = new CustomerEntity(1L, "John", "Dowe", addressEntity);
        AddressData addressData = new AddressData("30-300", "Kraków", "Krakowska", 1);

        //when
        AddressData addressDataResult = geoService.generateAddressData(customer);

        //then
        assertEquals(addressData, addressDataResult);
    }

    @Test
    void countDistanceBetweenClients() {
        //given
        AddressData addressDataStart = new AddressData();
        AddressData addressDataEnd = new AddressData();
        GeocodingAnswer positionsStart = new GeocodingAnswer();
        GeocodingAnswer positionsEnd = new GeocodingAnswer();
        List<Result> resulEndtList = new ArrayList<>();
        resulEndtList.add(new Result(new Position(3456, 4567)));
        positionsEnd.setResults(resulEndtList);
        List<Result> resulStartList = new ArrayList<>();
        resulStartList.add(new Result(new Position(123123, 234234)));
        positionsStart.setResults(resulEndtList);
        List<Route> routes = new ArrayList<>();
        routes.add(new Route(new Summary(5000)));

        RouteAnswer routeAnswer = new RouteAnswer();
        routeAnswer.setRoutes(routes);

        when(tomTomServiceFacade.getLocationsCoordinates(addressDataStart)).thenReturn(positionsStart);
        when(tomTomServiceFacade.getLocationsCoordinates(addressDataEnd)).thenReturn(positionsEnd);
        when(tomTomServiceFacade.getRouteBetweenAddresses(any(RouteData.class))).thenReturn(routeAnswer);

        //when
        Integer distanceResult = geoService.countDistanceBetweenClients(addressDataStart, addressDataEnd);

        //then
        assertEquals(5000, distanceResult);
        verify(tomTomServiceFacade, times(2)).getLocationsCoordinates(addressDataStart);
        verify(tomTomServiceFacade, times(2)).getLocationsCoordinates(addressDataEnd);
        verify(tomTomServiceFacade).getRouteBetweenAddresses(any(RouteData.class));
    }
}