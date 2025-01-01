package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.adapters.in.dto.AnswerDto;
import be.kdg.int5.chatbot.adapters.in.dto.FollowUpQuestionDto;
import be.kdg.int5.chatbot.adapters.in.dto.InitialQuestionDto;
import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.in.FollowUpGameConversationCommand;
import be.kdg.int5.chatbot.ports.in.FollowUpGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.common.exceptions.PythonServiceException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;


@RestController
public class GameChatbotRestController {
    private final StartGameConversationUseCase startGameConversationUseCase;
    private final FollowUpGameConversationUseCase followUpGameConversationUseCase;

    private final static Logger logger = LoggerFactory.getLogger(GameChatbotRestController.class);

    public GameChatbotRestController(StartGameConversationUseCase startGameConversationUseCase, FollowUpGameConversationUseCase followUpGameConversationUseCase) {
        this.startGameConversationUseCase = startGameConversationUseCase;
        this.followUpGameConversationUseCase = followUpGameConversationUseCase;
    }

    @PostMapping("/initial-question")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<AnswerDto> postInitialQuestion(
            @Valid @RequestBody InitialQuestionDto initialQuestionDto,
            @AuthenticationPrincipal Jwt token) {
        final UserId userUUID = new UserId(UUID.fromString(token.getClaimAsString("sub")));
        final GameId gameUUID = new GameId(UUID.fromString(initialQuestionDto.getGameId()));

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
    public ResponseEntity<AnswerDto> postFollowUpQuestion(
            @Valid @RequestBody FollowUpQuestionDto followUpQuestionDto,
            @AuthenticationPrincipal Jwt token) {
        final UserId userUUID = new UserId(UUID.fromString(token.getClaimAsString("sub")));
        final GameId gameUUID = new GameId(UUID.fromString(followUpQuestionDto.getGameId()));
        final String question = followUpQuestionDto.getQuestion().getText();

        try {
            final FollowUpGameConversationCommand followUpGameConversationCommand = new FollowUpGameConversationCommand(userUUID, gameUUID, question);
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
