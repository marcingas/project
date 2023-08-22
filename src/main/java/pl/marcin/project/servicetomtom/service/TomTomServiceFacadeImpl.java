package pl.marcin.project.servicetomtom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.servicetomtom.geocodingmodel.Position;
import pl.marcin.project.servicetomtom.routingmodel.RouteAnswer;
import pl.marcin.project.servicetomtom.routingmodel.RouteData;

@Service
@RequiredArgsConstructor
public class TomTomServiceFacadeImpl implements TomTomServiceFacade {
    private final TomTomGeocodingService tomTomGeocodingService;
    private final TomTomRoutingService tomTomRoutingService;

    @Override
    public Position getLocationsCoordinates(AddressData addressData) {
        return tomTomGeocodingService.getCoordinates(addressData);
    }

    @Override
    public Integer getRouteBetweenAddresses(RouteData routeData) {
        return tomTomRoutingService.getRoute(routeData);
    }
}
