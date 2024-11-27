package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.adapters.in.dto.AnswerDto;
import be.kdg.int5.chatbot.adapters.in.dto.QuestionDto;
import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.in.FollowUpGameConversationCommand;
import be.kdg.int5.chatbot.ports.in.FollowUpGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.common.exceptions.PythonServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;


@RestController
public class ChatbotRestController {
    private final StartGameConversationUseCase startGameConversationUseCase;
    private final FollowUpGameConversationUseCase followUpGameConversationUseCase;

    private final static Logger logger = LoggerFactory.getLogger(ChatbotRestController.class);

    public ChatbotRestController(StartGameConversationUseCase startGameConversationUseCase, FollowUpGameConversationUseCase followUpGameConversationUseCase) {
        this.startGameConversationUseCase = startGameConversationUseCase;
        this.followUpGameConversationUseCase = followUpGameConversationUseCase;
    }

    @PostMapping("/initial-question")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<AnswerDto> postInitialQuestion() {
        final UserId userUUID = new UserId(UUID.fromString("e4a40c63-2edf-4592-8d36-46b902db69d7")); // TODO
        final GameId gameUUID = new GameId(UUID.fromString("d77e1d1f-6b46-4c89-9290-3b9cf8a7c001")); // TODO

        try {
            final StartGameConversationCommand startGameConversationCommand = new StartGameConversationCommand(userUUID, gameUUID);
            final Answer answer = startGameConversationUseCase.startGameConversation(startGameConversationCommand);

            logger.info("Answer in the Controller - Initial: {}", answer.toString());

            final AnswerDto answerDto = new AnswerDto(answer.text());
            return ResponseEntity.ok(answerDto);
        } catch (PythonServiceException e) {
            final String errorMessage = "Python service is unavailable at the moment. Please try again later.";
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerDto(errorMessage));
        }
    }

    @PostMapping("/follow-up-question")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<AnswerDto> postFollowUpQuestion(@RequestBody QuestionDto questionDto) {
        final UserId userUUID = new UserId(UUID.fromString("e4a40c63-2edf-4592-8d36-46b902db69d7")); // TODO
        final GameId gameUUID = new GameId(UUID.fromString("d77e1d1f-6b46-4c89-9290-3b9cf8a7c001")); // TODO

        try {
            final FollowUpGameConversationCommand followUpGameConversationCommand = new FollowUpGameConversationCommand(userUUID, gameUUID, questionDto.getText());
            final Answer answer = followUpGameConversationUseCase.followUpGameConversation(followUpGameConversationCommand);

            logger.info("Answer in the Controller - Follow-up: {}", answer.toString());

            final AnswerDto answerDto = new AnswerDto(answer.text());
            return ResponseEntity.ok(answerDto);
        } catch (PythonServiceException e) {
            final String errorMessage = "Python service is unavailable at the moment. Please try again later.";
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerDto(errorMessage));
        }
    }
}
