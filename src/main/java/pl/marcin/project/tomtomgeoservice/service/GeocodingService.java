package pl.marcin.project.tomtomgeoservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import pl.marcin.project.tomtomgeoservice.geocodingmodel.GeocodingAnswer;
import reactor.core.publisher.Mono;

import static pl.marcin.project.tomtomgeoservice.constants.RouteSearchConstants.*;


@Slf4j
@Service
public class GeocodingService {
    private final WebClient webClient;

    public GeocodingService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getCoordinates(AddressData addressData) {

        String query = addressData.getPostCode() + " " + addressData.getTown() + ", "
                + addressData.getStreet() + " " + addressData.getNumber();

        String uri = UriComponentsBuilder.fromUriString(GET_LAT_LONG)
                .buildAndExpand(VERSION_NUMBER, query, EXT)
                .toUriString();
        uri += "?key=" + KEY;

        Mono<String> responseMono = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);

        return responseMono;
    }

    public Mono<GeocodingAnswer> getCoordinatesAsObject(AddressData addressData) {
        String query = addressData.getPostCode() + " " + addressData.getTown() + ", "
                + addressData.getStreet() + " " + addressData.getNumber();

        String uri = UriComponentsBuilder.fromUriString(GET_LAT_LONG)
                .buildAndExpand(VERSION_NUMBER, query, EXT)
                .toUriString();
        uri += "?key=" + KEY;

        Mono<GeocodingAnswer> responseMono = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GeocodingAnswer.class);
        return responseMono;

    }
}
