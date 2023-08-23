package pl.marcin.project.servicetomtom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.marcin.project.exceptions.*;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.servicetomtom.geocodingmodel.Position;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

import static pl.marcin.project.servicetomtom.constants.RouteSearchConstants.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class TomTomGeocodingService {
    private final WebClient webClient;

    public Position getCoordinates(AddressData addressData) {
        String query = addressData.getPostCode() + " " + addressData.getTown() + ", "
                + addressData.getStreet() + " " + addressData.getNumber();

        String uri = UriComponentsBuilder.fromUriString(GET_LAT_LONG)
                .buildAndExpand(VERSION_NUMBER, query, EXT)
                .toUriString();
        uri += "?key=" + KEY;

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GeocodingAnswer.class)
                .onErrorResume(throwable -> {
                    if (throwable instanceof WebClientResponseException) {
                        WebClientResponseException exception = (WebClientResponseException) throwable;
                        int statusCode = exception.getStatusCode().value();
                        switch (statusCode) {
                            case 400:
                                throw new GeocodingBadRequestException("Bad Request");
                            case 403:
                                throw new GeocodingForbiddenException("Not authorized");
                            case 404:
                                throw new GeocodingNotFoundException("Not Found: the HTTP request method" +
                                        " (GET, POST, etc) or path is incorrect.");
                            case 405:
                                throw new GeocodingMethodNotAllowedException("Not Allowed method");
                            case 429:
                                throw new GeocodingTooManyRequestsException("Too Many Requests");
                            case 596:
                                throw new GeocodingNotFoundException("Not found HTTP request method");
                            default:
                                throw new GeocodingInternalServerErrorException("error occured " +
                                        "while processing the request");
                        }
                    }
                    return Mono.error(throwable);
                })
                .map(response -> response.getResults().get(0).getPosition())
                .blockOptional()
                .orElseThrow(() -> new NoSuchElementException("No Data from TomTom geocoding service"));
    }
}
