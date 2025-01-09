package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.adapters.in.dto.AnswerDto;
import be.kdg.int5.chatbot.adapters.in.dto.GameQuestionDto;
import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.in.GameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.GameConversationCommand;
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
    private final GameConversationUseCase gameConversationUseCase;

    private final static Logger logger = LoggerFactory.getLogger(GameChatbotRestController.class);

    public GameChatbotRestController(GameConversationUseCase gameConversationUseCase) {
        this.gameConversationUseCase = gameConversationUseCase;
    }

    @PostMapping("/chatbot/game")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<AnswerDto> postGameQuestion(
            @Valid @RequestBody GameQuestionDto gameQuestionDto,
            @AuthenticationPrincipal Jwt token) {
        final UserId userUUID = new UserId(UUID.fromString(token.getClaimAsString("sub")));
        final GameId gameUUID = new GameId(UUID.fromString(gameQuestionDto.getGameId()));
        final String question = gameQuestionDto.getQuestion();

        try {
            final GameConversationCommand gameConversationCommand = new GameConversationCommand(userUUID, gameUUID, question);
            final GameConversation conversation = gameConversationUseCase.updateGameConversation(gameConversationCommand);

            Answer answer = conversation.getLatestQuestion().getAnswer();

            logger.info("Answer in the Controller");

            final AnswerDto answerDto = new AnswerDto(answer.text());
            return ResponseEntity.ok(answerDto);
        } catch (PythonServiceException e) {
            final String errorMessage = "Python service is unavailable at the moment. Please try again later.";
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new AnswerDto(errorMessage));
        }
    }
}
