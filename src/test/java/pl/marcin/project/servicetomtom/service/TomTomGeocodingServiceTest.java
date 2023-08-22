package pl.marcin.project.servicetomtom.service;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import pl.marcin.project.servicetomtom.geocodingmodel.AddressData;
import pl.marcin.project.servicetomtom.geocodingmodel.Position;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TomTomGeocodingServiceTest {
    private final BasicJsonTester basicJsonTester = new BasicJsonTester(this.getClass());
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
    void getCoordinatesTestSuccessfulResponse() {
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

        //then
        assertEquals(123.456, result.getLat(), 0.0001);
        assertEquals(789.012, result.getLon(), 0.0001);
    }
}