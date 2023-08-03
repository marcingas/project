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

    private GeocodingAnswer getLocationFromAddressReactive(AddressData addressData) throws Exception {

        CompletableFuture<GeocodingAnswer> futureCoordinates = new CompletableFuture<>();
        Mono<GeocodingAnswer> monoCoordinates = mapServiceFacade.getLocationsCoordFromObject(addressData);
        monoCoordinates.subscribe(
                geocodingAnswer -> futureCoordinates.complete(geocodingAnswer),
                throwable -> futureCoordinates.completeExceptionally(throwable)
        );
        return futureCoordinates.join();
    }

    private RouteAnswer findRouteReactive(RouteData routeData) {
        CompletableFuture<RouteAnswer> futureRoute = new CompletableFuture<>();
        Mono<RouteAnswer> monoRoute = mapServiceFacade.getRouteFromObject(routeData);
        monoRoute.subscribe(
                routeAnswer -> futureRoute.complete(routeAnswer),
                throwable -> futureRoute.completeExceptionally(throwable)
        );
        try {
            return futureRoute.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

        RouteData routeData = new RouteData(positionsConverter);
        RouteAnswer routeAnswer = findRoute(routeData);
        return routeAnswer.getRoutes().get(0).getSummary().getLengthInMeters();
    }

    public int countDistanceBetweenClientsReactive(AddressData addressDataStart,
                                                   AddressData addressDataEnd) throws Exception {

        Position positionStart = getLocationFromAddressReactive(addressDataStart).getResults().get(0).getPosition();
        Position positionEnd = getLocationFromAddressReactive(addressDataEnd).getResults().get(0).getPosition();

        PositionsConverter converter = new PositionsConverter(positionStart, positionEnd);

        RouteData routeData = new RouteData(converter);
        return findRouteReactive(routeData).getRoutes().get(0).getSummary().getLengthInMeters();

    }

}
