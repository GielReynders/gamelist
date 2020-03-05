package com.test.demowiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockInitializer.class})
class GameControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WireMockServer wireMockServer;

    @LocalServerPort
    private Integer port;

    @AfterEach
    void afterEach() {
        this.wireMockServer.resetAll();
    }


    @Test
    void getAllGames() {
        this.wireMockServer.stubFor(
                WireMock.get("/games")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("[{\"gameId\": 1,\"id\": 1,\"title\": \"Diablo 1\", \"completed\": false}," +
                                        "{\"gameId\": 1,\"id\": 2,\"title\": \"Diablo 2\", \"completed\": true}]"))
        );

        final String url = "http://localhost:8081/games";

        this.webTestClient
                .get()
                .uri(url)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$[0].title")
                .isEqualTo("Diablo 1")
                .jsonPath("$.length()")
                .isEqualTo(2);
    }

    @Test
    void getAllGamesCallLocalController_ReturnsListOfGames() {
        this.wireMockServer.stubFor(
                WireMock.get("/game-list")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("[{\"gameId\": 1,\"id\": 1,\"title\": \"Diablo 1\", \"completed\": false}," +
                                        "{\"gameId\": 1,\"id\": 2,\"title\": \"Diablo 2\", \"completed\": true}]"))
        );

        final String url = "http://localhost:" + port + "/api/games";

        this.webTestClient
                .get()
                .uri(url)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$[0].title")
                .isEqualTo("Diablo 1")
                .jsonPath("$.length()")
                .isEqualTo(2);
    }
}