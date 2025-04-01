package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.service.impl.DialogflowService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/dialogflow")
public class DialogflowController {

    private final DialogflowService dialogflowService;

    public DialogflowController(DialogflowService dialogflowService) {
        this.dialogflowService = dialogflowService;
    }

    @PostMapping("/query")
    public Map<String, String> detectIntent(@RequestBody Map<String, Object> request) {
        String sessionId = request.getOrDefault("sessionId", UUID.randomUUID().toString()).toString();
        String message = request.get("message").toString();
        Map<String, String> parameters = (Map<String, String>) request.get("parameters");

        String response = dialogflowService.detectIntent(sessionId, message, parameters);
        return Map.of("response", response);
    }
}
