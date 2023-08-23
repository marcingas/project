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
import pl.marcin.project.exceptions.RoutingBadRequestException;
import pl.marcin.project.servicetomtom.routingmodel.RouteData;

import java.io.IOException;
import java.net.URLDecoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TomTomRoutingServiceTest {
    private MockWebServer mockWebServer;
    private TomTomRoutingService routingService;

    @BeforeEach
    void setupMockServer() {
        mockWebServer = new MockWebServer();
        String mockUrl = mockWebServer.url("/").toString();
        WebClient webClient = WebClient.builder()
                .baseUrl(mockUrl)
                .build();
        routingService = new TomTomRoutingService(webClient);
    }

    @AfterEach
    void shutdownMockWebServer() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void getRouteSuccessTest() throws Exception {
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{\n" +
                        "  \"routes\": [\n" +
                        "    {\n" +
                        "      \"summary\": {\n" +
                        "        \"lengthInMeters\": 1500\n" +
                        "      }" +
                        "    }" +
                        "  ]" +
                        "}"));
        RouteData routeData = new RouteData("52.50931,42.9363A52:50.27421,43.43872");

        //when
        Integer ruoteLength = routingService.getRoute(routeData);
        RecordedRequest request = mockWebServer.takeRequest();
        String actualPath = request.getPath();
        String decodedPath = URLDecoder.decode(actualPath, "UTF-8");

        //then
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(decodedPath).isEqualTo("/routing/1/calculateRoute/" +
                "52.50931,42.9363A52:50.27421,43.43872/json?maxAlternatives=1&" +
                "routeType=shortest&traffic=true&travelMode=car&key=vctUnSj6acA5V6nLMaZMmSqXpoyPA5xq");
        assertEquals(ruoteLength, 1500);
    }

    @Test
    void getRuteException() throws InterruptedException {
        //given
        String errorMessage = "Bad request";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{\"error\": {\"id\": \"400\", \"description\": \"" + errorMessage + "\"}}"));
        RouteData routeData = new RouteData("52.50931,42.9363A52:50.27421,43.43872");
        //when
        RoutingBadRequestException exception = assertThrows(RoutingBadRequestException.class,
                () -> routingService.getRoute(routeData));
        //then
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertNotNull(recordedRequest);
        String responseBody = exception.getMessage();
        assertEquals(errorMessage, responseBody);

    }

}