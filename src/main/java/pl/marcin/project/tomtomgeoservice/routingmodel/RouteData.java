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

    private TravelMode travelMode;
    private RouteType routeType;
    private String positions;
    private Integer alternativeRoutes;
    private boolean traffic;

    public RouteData(String positions) {
        travelMode = TravelMode.car;
        routeType = RouteType.shortest;
        this.positions = positions;
        alternativeRoutes = 1;
        traffic = true;
    }
}
