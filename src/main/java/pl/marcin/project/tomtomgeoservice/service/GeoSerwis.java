package pl.marcin.project.tomtomgeoservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.reactive.function.client.WebClient;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.Position;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.PositionsConverter;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteType;
import pl.marcin.project.tomtomgeoservice.routingmodel.TravelMode;


public class GeoSerwis {
    private final String baseUrl = "https://api.tomtom.com";
    private WebClient webClient = WebClient.create(baseUrl);
    GeocodingService geocodingService = new GeocodingService(webClient);
    RouteService routeService = new RouteService(webClient);
    ObjectMapperConverter objectMapperConverter = new ObjectMapperConverter();

    public GeocodingAnswer getLocationFromAddress(AddressData addressData) throws Exception {
        String coordinates = geocodingService.getCoordinates(addressData).block();
        JsonNode node = objectMapperConverter.parser(coordinates);
        return objectMapperConverter.convertFromJsonToObject(node, GeocodingAnswer.class);

    }

    public RouteAnswer findRoute(RouteData routeData) throws Exception {
        String jSonString = routeService.getRoute(routeData).block();
        JsonNode node = objectMapperConverter.parser(jSonString);
        return objectMapperConverter.convertFromJsonToObject(node, RouteAnswer.class);
    }

    public int countDistanceBetweenClients(AddressData addressDataStart, AddressData addressDataEnd,
                                           TravelMode travelMode, RouteType routeType,
                                           int maxAlternativeRoutes, boolean traffic) throws Exception {

        Position positionStart = getLocationFromAddress(addressDataStart).getResults().get(0).getPosition();
        Position endPosition = getLocationFromAddress(addressDataEnd).getResults().get(0).getPosition();

        PositionsConverter positionsConverter = new PositionsConverter(positionStart, endPosition);

        RouteData routeData = new RouteData(travelMode, routeType, positionsConverter, maxAlternativeRoutes, traffic);
        RouteAnswer routeAnswer = findRoute(routeData);
        return routeAnswer.getRoutes().get(0).getSummary().getLengthInMeters();
    }

}
