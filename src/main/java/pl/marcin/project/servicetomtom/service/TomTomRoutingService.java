package pl.marcin.project.servicetomtom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.marcin.project.servicetomtom.routingmodel.RouteAnswer;
import pl.marcin.project.servicetomtom.routingmodel.RouteData;

import static pl.marcin.project.servicetomtom.constants.RouteSearchConstants.FIND_ROUTE;
import static pl.marcin.project.servicetomtom.constants.RouteSearchConstants.KEY;

@Service
public class TomTomRoutingService {
    private final WebClient webClient;

    @Autowired
    public TomTomRoutingService(WebClient webClient) {
        this.webClient = webClient;
    }

    public RouteAnswer getRoute(RouteData routeData) {
        String uri = UriComponentsBuilder.fromUriString(FIND_ROUTE)
                .buildAndExpand(routeData.getPositions(), routeData.getAlternativeRoutes(),
                        routeData.getRouteType(), routeData.isTraffic(), routeData.getTravelMode())
                .toUriString();
        uri += "&key=" + KEY;

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(RouteAnswer.class).block();

    }


}
