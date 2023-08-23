package pl.marcin.project.servicetomtom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.marcin.project.exceptions.RoutingBadRequestException;
import pl.marcin.project.servicetomtom.routingmodel.RouteAnswer;
import pl.marcin.project.servicetomtom.routingmodel.RouteData;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

import static pl.marcin.project.servicetomtom.constants.RouteSearchConstants.FIND_ROUTE;
import static pl.marcin.project.servicetomtom.constants.RouteSearchConstants.KEY;

@Service
public class TomTomRoutingService {
    private final WebClient webClient;

    @Autowired
    public TomTomRoutingService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Integer getRoute(RouteData routeData) {
        String uri = UriComponentsBuilder.fromUriString(FIND_ROUTE)
                .buildAndExpand(routeData.getPositions(), routeData.getAlternativeRoutes(),
                        routeData.getRouteType(), routeData.isTraffic(), routeData.getTravelMode())
                .toUriString();
        uri += "&key=" + KEY;

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(RouteAnswer.class)
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebClientResponseException) {
                        WebClientResponseException exception = (WebClientResponseException) throwable;
                        if (exception.getStatusCode().value() == 400) {
                            throw new RoutingBadRequestException("Bad request");
                        }
                    }
                    return Mono.error(throwable);
                })
                .map(response -> response.getRoutes().get(0).getSummary().getLengthInMeters())
                .blockOptional()
                .orElseThrow(() -> new NoSuchElementException("No data from TomTomRoutingService"));
    }
}
