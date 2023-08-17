package pl.marcin.project.tomtomgeoservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.Position;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final TomTomServiceFacade tomTomServiceFacade;
    private GeocodingAnswer getLocationFromAddress(AddressData addressData) {
        return tomTomServiceFacade.getLocationsCoordinates(addressData);
    }

    private RouteAnswer findRoute(RouteData routeData) {
        return tomTomServiceFacade.getRouteBetweenAddresses(routeData);
    }

    public AddressData generateAddressData(CustomerEntity customerToVisit) {
        AddressEntity address = customerToVisit.getAddress();
        AddressData addressData = new AddressData();
        addressData.setPostCode(address.getCode());
        addressData.setTown(address.getTown());
        addressData.setStreet(address.getStreet());
        addressData.setNumber(address.getNumber());
        return addressData;
    }


    public Integer countDistanceBetweenClients(AddressData addressDataStart,
                                               AddressData addressDataEnd) {
        Position positionStart = getLocationFromAddress(addressDataStart).getResults().get(0).getPosition();

        Position positionEnd = getLocationFromAddress(addressDataEnd).getResults().get(0).getPosition();

        String position = positionStart.getLat() + "," + positionStart.getLon() + ":" + positionEnd.getLat() + "," + positionEnd.getLon();

        RouteData routeData1 = new RouteData(position);
        return findRoute(routeData1).getRoutes().get(0).getSummary().getLengthInMeters();

    }
}
