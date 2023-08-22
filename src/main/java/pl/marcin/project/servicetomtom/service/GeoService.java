package pl.marcin.project.servicetomtom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.servicetomtom.geocodingmodel.Position;
import pl.marcin.project.servicetomtom.routingmodel.RouteAnswer;
import pl.marcin.project.servicetomtom.routingmodel.RouteData;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final TomTomServiceFacade tomTomServiceFacade;

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
        Position positionStart = tomTomServiceFacade.getLocationsCoordinates(addressDataStart);

        Position positionEnd = tomTomServiceFacade.getLocationsCoordinates(addressDataEnd);

        String position = positionStart.getLat() + "," + positionStart.getLon() + ":"
                + positionEnd.getLat() + "," + positionEnd.getLon();

        RouteData routeData1 = new RouteData(position);
        return tomTomServiceFacade.getRouteBetweenAddresses(routeData1);
    }
}
