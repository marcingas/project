package pl.marcin.project.tomtomgeoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import reactor.core.publisher.Mono;

@Service
public class MapServiceFacadeImpl implements MapServiceFacade {
    private final GeocodingService geocodingService;
    private final RouteService routeService;

    @Autowired
    public MapServiceFacadeImpl(GeocodingService geocodingService, RouteService routeService) {
        this.geocodingService = geocodingService;
        this.routeService = routeService;
    }

    @Override
    public Mono<String> getLocationsCoordinates(AddressData addressData) {
        return geocodingService.getCoordinates(addressData);
    }

    @Override
    public Mono<GeocodingAnswer> getLocationsCoordFromObject(AddressData addressData) {
        return geocodingService.getCoordinatesAsObject(addressData);
    }

    @Override
    public Mono<String> getRoute(RouteData routeData) {
        return routeService.getRoute(routeData);
    }

    @Override
    public Mono<RouteAnswer> getRouteFromObject(RouteData routeData) {
        return routeService.getRouteAsObject(routeData);
    }
}
