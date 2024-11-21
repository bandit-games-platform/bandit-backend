package be.kdg.int5.chatbot.adapters.out.python;

import be.kdg.int5.chatbot.adapters.out.python.dto.GameDetailsDto;
import be.kdg.int5.chatbot.adapters.out.python.dto.GameRuleDto;
import be.kdg.int5.chatbot.adapters.out.python.dto.InitialQuestionDto;
import be.kdg.int5.chatbot.adapters.out.python.dto.PythonResponseDto;
import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.out.ConversationStartPort;
import be.kdg.int5.common.exceptions.PythonServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class PythonClientAdapter implements ConversationStartPort {
    @Value("${python.backend.url:http://localhost:8000}")
    private String pythonBackendUrl;

    private final static Logger logger = LoggerFactory.getLogger(PythonClientAdapter.class);

    private final RestTemplate restTemplate;

    public PythonClientAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Answer startGameConversation(GameDetails gameDetails, GameConversation gameConversation, Question question) {
        // create Dtos for Python
        final GameDetailsDto gameDetailsDto = toGameDetailsDto(gameDetails);
        final InitialQuestionDto initialQuestionDto = new InitialQuestionDto(question.getText(), gameDetailsDto);

        // Set Http headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap DTO in HttpEntity for the request
        HttpEntity<InitialQuestionDto> entity = new HttpEntity<>(initialQuestionDto, headers);

        final Answer answer;

        try {
            String response = restTemplate.postForObject(pythonBackendUrl, entity, String.class);

            logger.debug("Response: {}", response);

            answer = new Answer(response);

        } catch (Exception e) {
            throw new PythonServiceException("An error occurred while calling the Python service.", e);
        }

        return answer;
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
