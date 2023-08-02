package pl.marcin.project.tomtomgeoservice.service;

import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import reactor.core.publisher.Mono;

public interface MapServiceFacade {
    Mono<String> getLocationsCoordinates(AddressData addressData);

    Mono<GeocodingAnswer> getLocationsCoordFromObject(AddressData addressData);

    Mono<String> getRoute(RouteData routeData);

    Mono<RouteAnswer> getRouteFromObject(RouteData routeData);
}
