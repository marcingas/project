package pl.marcin.project.tomtomgeoservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.Position;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.PositionsConverter;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteAnswer;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteData;
import pl.marcin.project.tomtomgeoservice.routingmodel.RouteType;
import pl.marcin.project.tomtomgeoservice.routingmodel.TravelMode;

@Service
public class GeoSerwis {

    private final MapServiceFacade mapServiceFacade;
    private final ObjectMapperConverter objectMapperConverter;

    @Autowired
    public GeoSerwis(MapServiceFacade mapServiceFacade, ObjectMapperConverter objectMapperConverter) {
        this.mapServiceFacade = mapServiceFacade;
        this.objectMapperConverter = objectMapperConverter;
    }


    private GeocodingAnswer getLocationFromAddress(AddressData addressData) throws Exception {
        String coordinates = mapServiceFacade.getLocationsCoordinates(addressData).block();
        JsonNode node = objectMapperConverter.parser(coordinates);
        return objectMapperConverter.convertFromJsonToObject(node, GeocodingAnswer.class);

    }

    private RouteAnswer findRoute(RouteData routeData) throws Exception {
        String jSonString = mapServiceFacade.getRoute(routeData).block();
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
