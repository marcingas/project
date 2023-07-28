package pl.marcin.project.tomtomgeoservice.routingmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.PositionsConverter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteData {
    //   base url=  https://api.tomtom.com
//    /routing/1/calculateRoute/52.50931%2C13.42936%3A52.50274%2C13.43872/json?maxAlternatives=3&routeType=shortest&traffic=true&travelMode=car&key=*****
    //        /routing/1/calculateRoute/{routePlanningLocations}/json?maxAlternatives={alternativeRoutes}&routeType={routeType}&traffic={boolean}&travelMode={travelMode}&key={Your_API_Key}
//https://api.tomtom.com/routing/1/calculateRoute/49.70537%2519.23082%2549.82226%2519.05514/json?maxAlternatives=1&routeType=fastest&traffic=true&travelMode=car&key=vctUnSj6acA5V6nLMaZMmSqXpoyPA5xq
    private TravelMode travelMode;
    private RouteType routeType;
    private PositionsConverter positions;
    private Integer alternativeRoutes;
    private boolean traffic;


}
