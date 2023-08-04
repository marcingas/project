package pl.marcin.project.tomtomgeoservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.Position;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.PositionsConverter;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class GeoService {

    private final MapServiceFacade mapServiceFacade;
    private final ObjectMapperConverter objectMapperConverter;

    @Autowired
    public GeoService(MapServiceFacade mapServiceFacade, ObjectMapperConverter objectMapperConverter) {
        this.mapServiceFacade = mapServiceFacade;
        this.objectMapperConverter = objectMapperConverter;
    }


    private GeocodingAnswer getLocationFromAddress(AddressData addressData) throws Exception {
        String coordinates = mapServiceFacade.getLocationsCoordinates(addressData).block();
        JsonNode node = objectMapperConverter.parser(coordinates);
        return objectMapperConverter.convertFromJsonToObject(node, GeocodingAnswer.class);

    }

    private Mono<GeocodingAnswer> getLocationFromAddressReactive(AddressData addressData) throws Exception {
        return mapServiceFacade.getLocationsCoordFromObject(addressData);
    }

    private Mono<RouteAnswer> findRouteReactive(RouteData routeData) {
        return mapServiceFacade.getRouteFromObject(routeData);
    }

    private RouteAnswer findRoute(RouteData routeData) throws Exception {
        String jSonString = mapServiceFacade.getRoute(routeData).block();
        JsonNode node = objectMapperConverter.parser(jSonString);
        return objectMapperConverter.convertFromJsonToObject(node, RouteAnswer.class);
    }

    public int countDistanceBetweenClients(AddressData addressDataStart, AddressData addressDataEnd) throws Exception {

        Position positionStart = getLocationFromAddress(addressDataStart).getResults().get(0).getPosition();
        Position endPosition = getLocationFromAddress(addressDataEnd).getResults().get(0).getPosition();

        PositionsConverter positionsConverter = new PositionsConverter(positionStart, endPosition);

        RouteData routeData = new RouteData(positionsConverter.toString());
        RouteAnswer routeAnswer = findRoute(routeData);
        return routeAnswer.getRoutes().get(0).getSummary().getLengthInMeters();
    }

    public Mono<Integer> countDistanceBetweenClientsReactive(AddressData addressDataStart,
                                                             AddressData addressDataEnd) throws Exception {

        Mono<Position> positionStart = getLocationFromAddressReactive(addressDataStart)
                .map(geocodingAnswer -> geocodingAnswer.getResults().get(0).getPosition());
        Mono<Position> positionEnd = getLocationFromAddressReactive(addressDataEnd).
                map(geocodingAnswer -> geocodingAnswer.getResults().get(0).getPosition());

        Mono<String> position = positionStart.zipWith(positionEnd, (a, b) -> a.getLat()
                + "," + a.getLon() + ":" + b.getLat() + "," + b.getLon());
//        PositionsConverter converter = new PositionsConverter(positionStart, positionEnd);

        return position.map(p -> new RouteData(p))
                .flatMap(rd -> findRouteReactive(rd))
                .map(routeAnswer -> routeAnswer.getRoutes().get(0).getSummary().getLengthInMeters());

    }

}
