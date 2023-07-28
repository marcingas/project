package pl.marcin.project.tomtomgeoservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import pl.marcin.project.tomtomgeoservice.geocodingmodel.AddressData;
import reactor.core.publisher.Mono;

import static pl.marcin.project.tomtomgeoservice.constants.RouteSearchConstants.*;


@Slf4j
public class geocodingService {
    private final WebClient webClient;

    public geocodingService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getCoordinates(AddressData addressData) {
        //    https://{baseURL}/search/{versionNumber}/geocode/{query}.{ext}?key={Your_API_Key}
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
}
