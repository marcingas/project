package pl.marcin.project.servicetomtom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.GeocodingAnswer;
import pl.marcin.project.servicetomtom.geocodingmodel.Position;

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
                .blockOptional()
                .map(response -> response.getResults().get(0).getPosition())
                .orElseThrow(() -> new NoSuchElementException("No Data from TomTom geocoding service"));
    }
}
