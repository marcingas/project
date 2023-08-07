package pl.marcin.project.tomtomgeoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.Position;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import reactor.core.publisher.Mono;

@Service
public class GeoService {

    private final TomTomServiceFacade tomTomServiceFacade;

    @Autowired
    public GeoService(TomTomServiceFacade tomTomServiceFacade) {
        this.tomTomServiceFacade = tomTomServiceFacade;

    }

    private Mono<GeocodingAnswer> getLocationFromAddressReactive(AddressData addressData) {
        return tomTomServiceFacade.getLocationsCoordinates(addressData);
    }

    private Mono<RouteAnswer> findRouteReactive(RouteData routeData) {
        return tomTomServiceFacade.getRouteBetweenAddresses(routeData);
    }
//    public Mono<AddressData> generateAddressData(CustomerEntity customerToVisit){
//
//    }


    public Mono<Integer> countDistanceBetweenClientsReactive(AddressData addressDataStart,
                                                             AddressData addressDataEnd) {
        Mono<Position> positionStart = getLocationFromAddressReactive(addressDataStart)
                .map(geocodingAnswer -> geocodingAnswer.getResults().get(0).getPosition());
        Mono<Position> positionEnd = getLocationFromAddressReactive(addressDataEnd)
                .map(geocodingAnswer -> geocodingAnswer.getResults().get(0).getPosition());

        Mono<String> position = positionStart.zipWith(positionEnd, (a, b) -> a.getLat()
                + "," + a.getLon() + ":" + b.getLat() + "," + b.getLon());

        return position.map(positions -> new RouteData(positions))
                .flatMap(routeData -> findRouteReactive(routeData))
                .map(routeAnswer -> routeAnswer.getRoutes().get(0).getSummary().getLengthInMeters());
    }
}
