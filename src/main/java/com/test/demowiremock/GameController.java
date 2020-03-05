package com.test.demowiremock;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @GetMapping()
    public ResponseEntity<String> getAllGames() {
        final String url = "http://localhost:8081/game-list";
        final WebClient webClient = WebClient.create("http://localhost:8081");

        final WebClient.RequestBodySpec uri = webClient.method(HttpMethod.GET).uri("/game-list");

        final Flux<String> stringFlux = uri.exchange().block().bodyToFlux(String.class);

        return ResponseEntity.ok(stringFlux.blockFirst());
    }
}
