package com.catpaw.catpawchat.controller.v1.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping("/heartbeat")
    public ResponseEntity<String> heartBeatCheck() {
        return ResponseEntity.ok()
                .body("hello");
    }
}
