package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.adapters.in.dto.GameDetailsDto;
import be.kdg.int5.chatbot.adapters.in.dto.GameRuleDto;
import be.kdg.int5.chatbot.adapters.in.dto.InitialQuestionDto;
import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.in.ContinueConversationUseCase;
import be.kdg.int5.chatbot.ports.in.ContinueGameConversationCommand;
import be.kdg.int5.chatbot.ports.in.StartConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.chatbot.ports.in.query.GameDetailsQuery;
import be.kdg.int5.chatbot.ports.in.query.GetGameDetailsCommand;
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
    private final StartConversationUseCase startConversationUseCase;
    private final ContinueConversationUseCase continueConversationUseCase;

    public ChatbotRestController(
            GameDetailsQuery gameDetailsQuery,
            StartConversationUseCase startConversationUseCase,
            ContinueConversationUseCase continueConversationUseCase) {
        this.gameDetailsQuery = gameDetailsQuery;
        this.startConversationUseCase = startConversationUseCase;
        this.continueConversationUseCase = continueConversationUseCase;
    }

    @PostMapping("/initial-question")
    public ResponseEntity<String> postInitialQuestion() {
        final UserId userUUID = new UserId(UUID.randomUUID()); // TODO
        final GameId gameUUID = new GameId(UUID.fromString("d77e1d1f-6b46-4c89-9290-3b9cf8a7c001")); // TODO

        final GetGameDetailsCommand gameDetailsCommand = new GetGameDetailsCommand(gameUUID);
        final GameDetails gameDetails = gameDetailsQuery.loadGameDetails(gameDetailsCommand);

        if (gameDetails == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        final GameDetailsDto gameDetailsDto = toGameDetailsDto(gameDetails);

        final StartGameConversationCommand startGameConversationCommand = new StartGameConversationCommand(userUUID, gameUUID);
        final GameConversation gameConversation = startConversationUseCase.startGameConversation(startGameConversationCommand);

        final String initialPrompt = GameConversation.initialPrompt;
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
            System.out.println("Body: " + response.getBody());

            // Create new question-answer pair
            final Question initialQuestion = new Question(initialPrompt, new Answer(response.getBody()));
            final ContinueGameConversationCommand continueGameConversationCommand = new ContinueGameConversationCommand(gameConversation, initialQuestion);
            continueConversationUseCase.continueGameConversation(continueGameConversationCommand);

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
