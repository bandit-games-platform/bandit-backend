package be.kdg.int5.chatbot.adapters.out.python;

import be.kdg.int5.chatbot.adapters.out.python.dto.*;
import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.out.AnswerAskPort;
import be.kdg.int5.common.exceptions.PythonServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PythonClientAdapter implements AnswerAskPort {
    @Value("${python.backend.url:http://localhost:8000}")
    private String pythonBackendUrl;
    private static final String INITIAL_QUESTION = "/initial-question";
    private static final String FOLLOW_UP_QUESTION = "/follow-up-question";

    private final static Logger logger = LoggerFactory.getLogger(PythonClientAdapter.class);

    private final RestTemplate restTemplate;

    public PythonClientAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Answer getAnswerForInitialQuestion(GameDetails gameDetails, GameConversation gameConversation, Question question) {
        // create Dtos for Python
        final GameDetailsDto gameDetailsDto = toGameDetailsDto(gameDetails);
        final InitialQuestionDto initialQuestionDto = new InitialQuestionDto(question.getText(), gameDetailsDto);

        // Set Http headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap DTO in HttpEntity for the request
        HttpEntity<InitialQuestionDto> entity = new HttpEntity<>(initialQuestionDto, headers);

        try {
            String response = restTemplate.postForObject(pythonBackendUrl + INITIAL_QUESTION, entity, String.class);
            logger.debug("Response Out Adapter - Inital: {}", response);

            String extractedAnswer = parseResponse(response);
            return new Answer(extractedAnswer);

        } catch (Exception e) {
            throw new PythonServiceException("An error occurred while calling the Python service.", e);
        }
    }

    @Override
    public Answer getAnswerForFollowUpQuestion(GameDetails gameDetails, List<Question> previousQuestionWindowList, Question question) {
        // create Dtos for Python
        final GameDetailsDto gameDetailsDto = toGameDetailsDto(gameDetails);
        final List<QuestionAnswerDto> questionAnswerDtoList = toQuestionAnswerDtoList(previousQuestionWindowList);
        final FollowUpQuestionDto followUpQuestionDto = new FollowUpQuestionDto(question.getText(), gameDetailsDto, questionAnswerDtoList);

        // Set Http headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap DTO in HttpEntity for the request
        HttpEntity<FollowUpQuestionDto> entity = new HttpEntity<>(followUpQuestionDto, headers);

        try {
            String response = restTemplate.postForObject(pythonBackendUrl + FOLLOW_UP_QUESTION, entity, String.class);
            logger.debug("Response Out Adapter - FollowUp: {}", response);

            String extractedResponse = parseResponse(response);
            return new Answer(extractedResponse);

        } catch (Exception e) {
            throw new PythonServiceException("An error occurred while calling the Python service.", e);
        }
    }

    private String parseResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);

            // Extract the "response" field
            if (jsonNode.has("response")) {
                String rawResponse = jsonNode.get("response").toString();

                String cleanResponse = rawResponse.trim();
                if (cleanResponse.startsWith("\"") && cleanResponse.endsWith("\"")) {
                    cleanResponse = cleanResponse.substring(1, cleanResponse.length() - 1);
                }

                return cleanResponse;
            } else {
                throw new IllegalArgumentException("Response JSON does not contain 'response' field");
            }
        } catch (Exception e) {
            logger.error("Error while parsing the Python service response", e);
            throw new PythonServiceException("Failed to parse Python service response.", e);
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

    private List<QuestionAnswerDto> toQuestionAnswerDtoList(List<Question> previousQuestionWindowList) {
        return previousQuestionWindowList.stream()
                .filter(q -> q.getAnswer() != null)
                .map(q -> {
                    QuestionAnswerDto qaDto = new QuestionAnswerDto();
                    qaDto.setQuestion(q.getText());
                    qaDto.setAnswer(q.getAnswer().text());
                    return qaDto;
                }).toList();
    }
}
