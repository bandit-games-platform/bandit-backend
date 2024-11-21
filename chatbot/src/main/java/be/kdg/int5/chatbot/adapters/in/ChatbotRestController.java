package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.adapters.in.dto.GameDetailsDto;
import be.kdg.int5.chatbot.adapters.in.dto.GameRuleDto;
import be.kdg.int5.chatbot.adapters.in.dto.InitialQuestionDto;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.ports.in.query.GameDetailsQuery;
import be.kdg.int5.chatbot.ports.in.query.GetGameDetailsCommand;
import be.kdg.int5.chatbot.ports.in.query.InitialQuestionForConversation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;


@RestController
public class ChatbotRestController {
    @Value("${python.backend.url:http://localhost:8000}")
    private String pythonBackendUrl;

    private final GameDetailsQuery gameDetailsQuery;
    private final InitialQuestionForConversation initialQuestionForConversation;

    public ChatbotRestController(GameDetailsQuery gameDetailsQuery, InitialQuestionForConversation initialQuestionForConversation) {
        this.gameDetailsQuery = gameDetailsQuery;
        this.initialQuestionForConversation = initialQuestionForConversation;
    }

    @PostMapping("/initial-question")
    public ResponseEntity<String> postInitialQuestion() {
        final GameId gameUUID = new GameId(UUID.fromString("d77e1d1f-6b46-4c89-9290-3b9cf8a7c001"));
        final GetGameDetailsCommand gameDetailsCommand = new GetGameDetailsCommand(gameUUID);

        final GameDetails gameDetails = gameDetailsQuery.loadGameDetails(gameDetailsCommand);

        if (gameDetails == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        final GameDetailsDto gameDetailsDto = toGameDetailsDto(gameDetails);
        final String initialPrompt = initialQuestionForConversation.getInitialQuestionForGameConversation();

        final InitialQuestionDto initialQuestionDto = new InitialQuestionDto(initialPrompt, gameDetailsDto);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Convert DTO to HTTP entity
        HttpEntity<InitialQuestionDto> entity = new HttpEntity<>(initialQuestionDto, headers);

        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(pythonBackendUrl, entity, String.class);
            System.out.println(response);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while sending POST request to Python backend: " + e.getMessage());
        }
    }

    private GameDetailsDto toGameDetailsDto(GameDetails gameDetails) {
        final List<GameRuleDto> gameRuleDtos = gameDetails.getRules().stream()
                .map(rule -> new GameRuleDto(rule.stepNumber(), rule.rule())).toList();

        return new GameDetailsDto(
                gameDetails.getId().uuid(),
                gameDetails.getTitle(),
                gameDetails.getDescription(),
                gameRuleDtos
        );
    }
}
