package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.adapters.in.dto.AnswerDto;
import be.kdg.int5.chatbot.adapters.in.dto.LoadQuestionDto;
import be.kdg.int5.chatbot.adapters.in.dto.UpdatePlatformConversationDto;
import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.PlatformConversation;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.in.PlatformConversationCommand;
import be.kdg.int5.chatbot.ports.in.PlatformConversationUseCase;
import be.kdg.int5.chatbot.ports.in.query.PlatformConversationQuery;
import be.kdg.int5.common.exceptions.PythonServiceException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PlatformChatbotRestController {
    private final static Logger logger = LoggerFactory.getLogger(GameChatbotRestController.class);

    private final PlatformConversationUseCase platformConversationUseCase;
    private final PlatformConversationQuery platformConversationQuery;

    public PlatformChatbotRestController(PlatformConversationUseCase platformConversationUseCase, PlatformConversationQuery platformConversationQuery) {
        this.platformConversationUseCase = platformConversationUseCase;
        this.platformConversationQuery = platformConversationQuery;
    }

    @PostMapping("/chatbot/platform/{pageName}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<AnswerDto> continueConversation(
            @PathVariable String pageName,
            @Valid @RequestBody UpdatePlatformConversationDto dto,
            @AuthenticationPrincipal Jwt token
    ) {
        UUID userId = UUID.fromString(token.getClaimAsString("sub"));
        try {
            PlatformConversation platformConversation = platformConversationUseCase.updateConversation(
                    new PlatformConversationCommand(pageName, userId, dto.getQuestionText())
            );

            Answer latestAnswer = platformConversation.getLatestQuestion().getAnswer();

            return ResponseEntity.ok(new AnswerDto(latestAnswer.text()));
        } catch (PythonServiceException e) {
            logger.error("Something went wrong! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    @GetMapping("/chatbot/platform")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<LoadQuestionDto>> getConversationDto(
            @RequestParam boolean lastOnly,
            @AuthenticationPrincipal Jwt token
    ) {
        UUID userId = UUID.fromString(token.getClaimAsString("sub"));
        PlatformConversation platformConversation = platformConversationQuery.getLatestForPlayer(userId);

        if (platformConversation == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if (ChronoUnit.HOURS.between(platformConversation.getLastMessageTime(), LocalDateTime.now()) > 6) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (lastOnly) {
            Question lastQuestion = platformConversation.getLatestQuestion();
            String answerText = null;
            if (lastQuestion.getAnswer() != null) {
                answerText = lastQuestion.getAnswer().text();
            }

            LoadQuestionDto loadedQuestion = new LoadQuestionDto(
                    lastQuestion.getText(),
                    lastQuestion.getSubmittedAt(),
                    new AnswerDto(answerText)
            );
            return ResponseEntity.ok(List.of(loadedQuestion));
        } else {
            List<LoadQuestionDto> loadedQuestions = platformConversation.getQuestions().stream()
                    .map(
                            loadedQuestion -> {
                                String answerText = null;
                                if (loadedQuestion.getAnswer() != null) {
                                    answerText = loadedQuestion.getAnswer().text();
                                }

                                return new LoadQuestionDto(
                                        loadedQuestion.getText(),
                                        loadedQuestion.getSubmittedAt(),
                                        new AnswerDto(answerText)
                                );
                            }
                    ).collect(Collectors.toList());
            return ResponseEntity.ok(loadedQuestions);
        }
    }
}
