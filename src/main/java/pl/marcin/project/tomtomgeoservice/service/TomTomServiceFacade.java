package pl.marcin.project.tomtomgeoservice.service;

import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import reactor.core.publisher.Mono;

public interface TomTomServiceFacade {

    Mono<GeocodingAnswer> getLocationsCoordinates(AddressData addressData);

    Mono<RouteAnswer> getRouteBetweenAddresses(RouteData routeData);
}
