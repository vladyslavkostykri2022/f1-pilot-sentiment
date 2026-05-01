package ua.lviv.polytech.f1.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ua.lviv.polytech.f1.model.SentimentResult;
import ua.lviv.polytech.f1.service.AudioProcessingService;

import java.time.Instant;

@Controller
public class WebSocketController {

    private final AudioProcessingService processingService;

    public WebSocketController(AudioProcessingService processingService) {
        this.processingService = processingService;
    }

    // Симуляція радіообміну пілота
    @MessageMapping("/radio")
    @SendTo("/topic/results")
    public SentimentResult simulateRadio(String message) {
        // message = текст від пілота
        SentimentResult result = processingService.processSample("SIM-" + Instant.now().getNano(), "en", message);
        result.setPilotId("ПІЛОТ (реальний час)");
        return result;
    }
}