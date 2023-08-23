package pl.marcin.project.servicetomtom.service;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import pl.marcin.project.exceptions.*;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.Position;

import java.io.IOException;
import java.net.URLDecoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TomTomGeocodingServiceTest {
    private MockWebServer mockWebServer;
    private TomTomGeocodingService tomTomGeocodingService;

    @BeforeEach
    void setupMockWebServer() {
        mockWebServer = new MockWebServer();
        String mockUrl = mockWebServer.url("/").toString();
        WebClient webClient = WebClient.builder()
                .baseUrl(mockUrl)
                .build();
        tomTomGeocodingService = new TomTomGeocodingService(webClient);
    }

    @AfterEach
    void shutdownMockWebServer() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getCoordinatesTestSuccessfulResponse() throws Exception {
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{" +
                        "\"results\": [{" +
                        "\"position\": {" +
                        "\"lat\": 123.456," +
                        "\"lon\": 789.012" +
                        "}" +
                        "}]" +
                        "}"));
        AddressData addressData = new AddressData("34-300", "Żywiec", "Krasinskiego", 1);

        //when
        Position result = tomTomGeocodingService.getCoordinates(addressData);
        RecordedRequest request = mockWebServer.takeRequest();
        String actualPath = request.getPath();
        String decodedPath = URLDecoder.decode(actualPath, "UTF-8");

        //then
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(decodedPath)
                .isEqualTo("/search/2/geocode/34-300 Żywiec, Krasinskiego 1.json" +
                        "?key=vctUnSj6acA5V6nLMaZMmSqXpoyPA5xq");
        assertEquals(123.456, result.getLat(), 0.0001);
        assertEquals(789.012, result.getLon(), 0.0001);
    }

    @Test
    void GeocodingBadRequestException() throws InterruptedException {
        String errorMessage = "Bad Request";
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(400)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"error\": {\"id\": \"400\", \"description\": \"" + errorMessage + "\"}}")
        );
        AddressData addressData = new AddressData("34-300", "Żywiec", "Krasinskiego", 1);

        GeocodingBadRequestException exception = assertThrows(GeocodingBadRequestException.class,
                () -> tomTomGeocodingService.getCoordinates(addressData));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertNotNull(recordedRequest);

        String responseBody = exception.getMessage();
        assertEquals(errorMessage, responseBody);
    }

    @Test
    void GeocodingForbiddenException() throws InterruptedException {
        String errorMessage = "Not authorized";
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(403)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"error\": {\"id\": \"403\", \"description\": \"" + errorMessage + "\"}}")
        );
        AddressData addressData = new AddressData("34-300", "Żywiec", "Krasinskiego", 1);

        GeocodingForbiddenException exception = assertThrows(GeocodingForbiddenException.class,
                () -> tomTomGeocodingService.getCoordinates(addressData));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertNotNull(recordedRequest);

        String responseBody = exception.getMessage();
        assertEquals(errorMessage, responseBody);
    }

    @Test
    void GeocodingNotFoundException() throws InterruptedException {
        String errorMessage = "Not Found: the HTTP request method (GET, POST, etc) or path is incorrect.";
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"error\": {\"id\": \"404\", \"description\": \"" + errorMessage + "\"}}")
        );
        AddressData addressData = new AddressData("34-300", "Żywiec", "Krasinskiego", 1);

        GeocodingNotFoundException exception = assertThrows(GeocodingNotFoundException.class,
                () -> tomTomGeocodingService.getCoordinates(addressData));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertNotNull(recordedRequest);

        String responseBody = exception.getMessage();
        assertEquals(errorMessage, responseBody);
    }

    @Test
    void GeocodingNotAllowedException() throws InterruptedException {
        String errorMessage = "Not Allowed method";
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(405)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"error\": {\"id\": \"405\", \"description\": \"" + errorMessage + "\"}}")
        );
        AddressData addressData = new AddressData("34-300", "Żywiec", "Krasinskiego", 1);

        GeocodingMethodNotAllowedException exception = assertThrows(GeocodingMethodNotAllowedException.class,
                () -> tomTomGeocodingService.getCoordinates(addressData));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertNotNull(recordedRequest);

        String responseBody = exception.getMessage();
        assertEquals(errorMessage, responseBody);
    }

    @Test
    void GeocodingTooManyException() throws InterruptedException {
        String errorMessage = "Too Many Requests";
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(429)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"error\": {\"id\": \"429\", \"description\": \"" + errorMessage + "\"}}")
        );
        AddressData addressData = new AddressData("34-300", "Żywiec", "Krasinskiego", 1);

        GeocodingTooManyRequestsException exception = assertThrows(GeocodingTooManyRequestsException.class,
                () -> tomTomGeocodingService.getCoordinates(addressData));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertNotNull(recordedRequest);

        String responseBody = exception.getMessage();
        assertEquals(errorMessage, responseBody);
    }

    @Test
    void GeocodingInternalServerErrorException() throws InterruptedException {
        String errorMessage = "error occured while processing the request";
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"error\": {\"id\": \"500\", \"description\": \"" + errorMessage + "\"}}")
        );
        AddressData addressData = new AddressData("34-300", "Żywiec", "Krasinskiego", 1);

        GeocodingInternalServerErrorException exception = assertThrows(GeocodingInternalServerErrorException.class,
                () -> tomTomGeocodingService.getCoordinates(addressData));

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertNotNull(recordedRequest);

        String responseBody = exception.getMessage();
        assertEquals(errorMessage, responseBody);
    }
}