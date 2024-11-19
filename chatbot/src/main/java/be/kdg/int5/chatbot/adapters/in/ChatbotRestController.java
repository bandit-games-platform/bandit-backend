package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.adapters.in.dto.GameDetailsDto;
import be.kdg.int5.chatbot.adapters.in.dto.GameRuleDto;
import be.kdg.int5.chatbot.adapters.in.dto.InitialQuestionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;


@RestController
public class ChatbotRestController {
    @Value("${python.backend.url:http://localhost:8000}")
    private String pythonBackendUrl;

    @PostMapping("/initial-question")
    public ResponseEntity<String> postInitialQuestion() {
        System.out.println("Hi there Chatbot!");

        // Create mock data for DTOs
        GameRuleDto rule1 = new GameRuleDto(1, "Collect all the keys to unlock doors.");
        GameRuleDto rule2 = new GameRuleDto(2, "Avoid traps and enemies.");
        GameRuleDto rule3 = new GameRuleDto(3, "Solve puzzles to progress.");
        List<GameRuleDto> rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);

        GameDetailsDto gameDetails = new GameDetailsDto(
                UUID.randomUUID(), // Generate a unique ID
                "Adventure Quest", // Game title
                "A thrilling adventure game full of puzzles and challenges.", // Game description
                rules
        );

        InitialQuestionDto initialQuestionDto = new InitialQuestionDto(
                " Provide a very short paragraph to describe the game. Additionally, can you summarize the rules for this game in as few words as possible?", // Initial prompt
                gameDetails
        );

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Convert DTO to HTTP entity
        HttpEntity<InitialQuestionDto> entity = new HttpEntity<>(initialQuestionDto, headers);

        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(pythonBackendUrl, initialQuestionDto, String.class);
            System.out.println(response);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while sending POST request to Python backend: " + e.getMessage());
        }
    }
}
