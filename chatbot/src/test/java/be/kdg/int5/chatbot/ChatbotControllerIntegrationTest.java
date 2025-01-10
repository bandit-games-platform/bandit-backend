package be.kdg.int5.chatbot;

import be.kdg.int5.chatbot.adapters.in.dto.GameQuestionDto;
import be.kdg.int5.chatbot.adapters.out.db.answer.AnswerJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.conversation.ConversationJpaRepository;
import be.kdg.int5.chatbot.adapters.out.db.conversation.GameConversationJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.gameDetails.GameDetailsJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.gameDetails.GameDetailsJpaRepository;
import be.kdg.int5.chatbot.adapters.out.python.PythonClientAdapter;
import be.kdg.int5.chatbot.adapters.out.db.question.QuestionJpaEntity;
import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.GameRule;
import be.kdg.int5.common.exceptions.PythonServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ChatbotControllerIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConversationJpaRepository conversationJpaRepository;

    @MockBean
    private GameDetailsJpaRepository gameDetailsJpaRepository;

    @MockBean
    private PythonClientAdapter pythonClientAdapter;

    private UUID userId;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        userId = TestIds.USER_ID.uuid();
        gameId = TestIds.GAME_ID.uuid();
    }

    @Test
    void shouldReturnAnswerWhenUserStartsAConversation() throws Exception {

        // Arrange
        GameDetailsJpaEntity gameDetailsJpa = new GameDetailsJpaEntity(UUID.randomUUID(), "My Game", "My Game Description", new HashSet<>(), null);
        GameQuestionDto initialQuestionDto = new GameQuestionDto(String.valueOf(gameId), "");
        String expectedAnswerText = "Here are the main rules of the game...";

        when(gameDetailsJpaRepository.findByIdWithRelationships(any())).thenReturn(gameDetailsJpa);
        when(conversationJpaRepository.findGameConversationByUserIdAndGameIdAndLatestStartTime(userId, gameId))
                .thenReturn(null);
        when(pythonClientAdapter.getAnswerForInitialGameQuestion(any(), any())).thenReturn(new Answer(expectedAnswerText));

        // Act
        final ResultActions result = mockMvc.perform(post("/chatbot/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(initialQuestionDto))
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(expectedAnswerText));

        verify(pythonClientAdapter).getAnswerForInitialGameQuestion(any(), any());
    }

    @Test
    void shouldReturnAnswerWhenUserContinuesAConversation() throws Exception {

        // Arrange
        final LocalDateTime submittedAt = LocalDateTime.now();
        final GameConversationJpaEntity existingConversation = getGameConversationJpaEntity(submittedAt);

        String question = "Tell me more about the game.";
        GameQuestionDto gameQuestionDto = new GameQuestionDto(String.valueOf(gameId), question);

        final String expectedAnswerText = "This is additional information about the game.";

        when(conversationJpaRepository.findGameConversationByUserIdAndGameIdAndLatestStartTime(userId, gameId))
                .thenReturn(existingConversation);
        when(pythonClientAdapter.getAnswerForFollowUpGameQuestion(any(), any(), any())).thenReturn(new Answer(expectedAnswerText));

        // Act
        final ResultActions result = mockMvc.perform(post("/chatbot/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameQuestionDto))
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(expectedAnswerText));

        verify(pythonClientAdapter).getAnswerForFollowUpGameQuestion(any(), any(), any());
    }

    @Test
    void shouldReturnServiceUnavailableWhenPythonServiceIsDown() throws Exception {

        // Arrange
        final LocalDateTime submittedAt = LocalDateTime.now();
        final GameConversationJpaEntity existingConversation = getGameConversationJpaEntity(submittedAt);

        String question = "Tell me more about the game.";
        GameQuestionDto gameQuestionDto = new GameQuestionDto(String.valueOf(gameId), question);

        when(conversationJpaRepository.findGameConversationByUserIdAndGameIdAndLatestStartTime(userId, gameId))
                .thenReturn(existingConversation);
        when(pythonClientAdapter.getAnswerForFollowUpGameQuestion(any(), any(), any()))
                .thenThrow(new PythonServiceException("An error occurred while calling the Python service."));

        // Act
        final ResultActions result = mockMvc.perform(post("/chatbot/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameQuestionDto))
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.text").value("Python service is unavailable at the moment. Please try again later."));
    }

    @Test
    void shouldReturnForbiddenWhenUserHasNoAuthority() throws Exception {

        // Arrange
        GameQuestionDto initialQuestionDto = new GameQuestionDto(gameId.toString(), "");

        // Act
        final ResultActions result = mockMvc.perform(post("/chatbot/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(initialQuestionDto))
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))));

        // Assert
        result.andExpect(status().isForbidden());
    }

    private GameConversationJpaEntity getGameConversationJpaEntity(LocalDateTime submittedAt) {
        final GameConversationJpaEntity existingConversation = new GameConversationJpaEntity();
        final AnswerJpaEntity initialAnswer = new AnswerJpaEntity("Initial Answer.");
        final QuestionJpaEntity initialQuestion = new QuestionJpaEntity("Initial Question", submittedAt, true, existingConversation, initialAnswer);

        existingConversation.setUserId(userId);
        existingConversation.setGameId(gameId);
        existingConversation.setStartTime(submittedAt);
        existingConversation.setLastMessageTime(submittedAt);
        existingConversation.getQuestions().add(initialQuestion);

        return existingConversation;
    }
}