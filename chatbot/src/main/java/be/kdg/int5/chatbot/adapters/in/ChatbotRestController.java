package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.in.StartConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.common.exceptions.PythonServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
public class ChatbotRestController {
    private final StartConversationUseCase startConversationUseCase;

    private final static Logger logger = LoggerFactory.getLogger(ChatbotRestController.class);

    public ChatbotRestController(StartConversationUseCase startConversationUseCase) {
        this.startConversationUseCase = startConversationUseCase;
    }

    @PostMapping("/initial-question")
    public ResponseEntity<Answer> postInitialQuestion() {
        final UserId userUUID = new UserId(UUID.randomUUID()); // TODO
        final GameId gameUUID = new GameId(UUID.fromString("d77e1d1f-6b46-4c89-9290-3b9cf8a7c001")); // TODO

        try {
            final StartGameConversationCommand startGameConversationCommand = new StartGameConversationCommand(userUUID, gameUUID);
            final Answer answer = startConversationUseCase.startGameConversation(startGameConversationCommand);

            logger.info("Answer in the Controller: {}", answer.toString());

            return ResponseEntity.ok(answer);
        } catch (PythonServiceException e) {
            final String errorMessage = "Python service is unavailable at the moment. Please try again later.";
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Answer(errorMessage));
        }
    }
}
