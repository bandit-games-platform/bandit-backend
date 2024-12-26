package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.adapters.in.dto.AnswerDto;
import be.kdg.int5.chatbot.adapters.in.dto.QuestionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlatformChatbotRestController {
    private final static Logger logger = LoggerFactory.getLogger(GameChatbotRestController.class);

    @PostMapping("/chatbot/platform/{pageName}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<AnswerDto> continueConversation(
            @PathVariable String pageName,
            @AuthenticationPrincipal Jwt token
    ) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @GetMapping("/chatbot/platform")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<QuestionDto> getConversationDto(
            @RequestParam boolean lastOnly
    ) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
