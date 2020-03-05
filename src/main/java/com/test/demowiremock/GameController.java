package com.test.demowiremock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final WebClient todoWebClient;

    public GameController(WebClient todoWebClient) {
        this.todoWebClient = todoWebClient;
    }


    @GetMapping()
    public ResponseEntity<String> getAllGames() {
//        this.todoWebClient
//                .get()
//                .uri(url)
//                .exchange()
//                .expectStatus()
//                .is2xxSuccessful()
//                .expectBody()
//                .jsonPath("$[0].title")
//                .isEqualTo("Diablo 1")
//                .jsonPath("$.length()")
//                .isEqualTo(2);
        return ResponseEntity.ok("{\"game\":\"Diablo_2\"}");
    }
}
