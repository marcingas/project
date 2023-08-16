package pl.marcin.project.tomtomgeoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import reactor.core.publisher.Mono;

@Service
public class TomTomServiceFacadeImpl implements TomTomServiceFacade {
    private final TomTomGeocodingService tomTomGeocodingService;
    private final TomTomRoutingService tomTomRoutingService;

    @Autowired
    public TomTomServiceFacadeImpl(TomTomGeocodingService tomTomGeocodingService, TomTomRoutingService tomTomRoutingService) {
        this.tomTomGeocodingService = tomTomGeocodingService;
        this.tomTomRoutingService = tomTomRoutingService;
    }

    @Override
    public GeocodingAnswer getLocationsCoordinates(AddressData addressData) {
        return tomTomGeocodingService.getCoordinates(addressData);
    }

    @Override
    public RouteAnswer getRouteBetweenAddresses(RouteData routeData) {
        return tomTomRoutingService.getRoute(routeData);
    }
}
