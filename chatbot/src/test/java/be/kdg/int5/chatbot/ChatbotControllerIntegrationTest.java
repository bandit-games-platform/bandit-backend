package be.kdg.int5.chatbot;

import be.kdg.int5.chatbot.adapters.out.db.answer.AnswerJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.conversation.ConversationJpaRepository;
import be.kdg.int5.chatbot.adapters.out.db.conversation.GameConversationJpaEntity;
import be.kdg.int5.chatbot.adapters.out.python.PythonClientAdapter;
import be.kdg.int5.chatbot.adapters.out.db.question.QuestionJpaEntity;
import be.kdg.int5.chatbot.domain.Answer;
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
    private PythonClientAdapter pythonClientAdapter;

    private UUID userId;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        userId = TestIds.USER_ID.uuid();
        gameId = TestIds.GAME_ID.uuid();
    }

//    @Test
//    void shouldReturnAnswerWhenUserStartsAConversation() throws Exception {
//
//        // Arrange
//        InitialQuestionDto initialQuestionDto = new InitialQuestionDto(String.valueOf(gameId));
//        String expectedAnswerText = "Here are the main rules of the game...";
//
//        when(pythonClientAdapter.getAnswerForInitialQuestion(any(), any())).thenReturn(new Answer(expectedAnswerText));
//
//        // Act
//        final ResultActions result = mockMvc.perform(post("/initial-question")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(initialQuestionDto))
//                .with(jwt()
//                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
//                        .authorities(new SimpleGrantedAuthority("player"))));
//
//        // Assert
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.text").value(expectedAnswerText));
//
//        verify(pythonClientAdapter).getAnswerForInitialQuestion(any(), any());
//    }
//
//    @Test
//    void shouldReturnAnswerWhenConversationWithInitialQuestionAlreadyExists() throws Exception {
//        // Arrange
//        LocalDateTime submittedAt = LocalDateTime.now();
//        GameConversationJpaEntity existingConversation = getGameConversationJpaEntity(submittedAt);
//
//        when(conversationJpaRepository.findByUserIdAndGameIdWithQuestions(userId, gameId))
//                .thenReturn(existingConversation);
//
//        InitialQuestionDto initialQuestionDto = new InitialQuestionDto(String.valueOf(gameId));
//
//        // Act
//        final ResultActions result = mockMvc.perform(post("/initial-question")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(initialQuestionDto))
//                .with(jwt()
//                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
//                        .authorities(new SimpleGrantedAuthority("player"))));
//
//        // Assert
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.text").value("Initial Answer."));
//
//        verify(conversationJpaRepository).findByUserIdAndGameIdWithQuestions(userId, gameId);
//        verify(pythonClientAdapter, times(0)).getAnswerForInitialQuestion(any(), any());
//    }
//
//    @Test
//    void shouldReturnAnswerWhenUserContinuesAConversation() throws Exception {
//
//        // Arrange
//        final LocalDateTime submittedAt = LocalDateTime.now();
//        final GameConversationJpaEntity existingConversation = getGameConversationJpaEntity(submittedAt);
//
//        when(conversationJpaRepository.findByUserIdAndGameIdWithQuestions(userId, gameId))
//                .thenReturn(existingConversation);
//
//        QuestionDto questionDto = new QuestionDto("Tell me more about the game.");
//        FollowUpQuestionDto followUpQuestionDto = new FollowUpQuestionDto(String.valueOf(gameId), questionDto);
//
//        final String expectedAnswerText = "This is additional information about the game.";
//
//        when(pythonClientAdapter.getAnswerForFollowUpQuestion(any(), any(), any())).thenReturn(new Answer(expectedAnswerText));
//
//        // Act
//        final ResultActions result = mockMvc.perform(post("/follow-up-question")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(followUpQuestionDto))
//                .with(jwt()
//                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
//                        .authorities(new SimpleGrantedAuthority("player"))));
//
//        // Assert
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.text").value(expectedAnswerText));
//
//        verify(pythonClientAdapter).getAnswerForFollowUpQuestion(any(), any(), any());
//    }
//
//    @Test
//    void shouldReturnServiceUnavailableWhenPythonServiceFailsForFollowUpQuestion() throws Exception {
//
//        // Arrange
////        InitialQuestionDto initialQuestionDto = new InitialQuestionDto(gameId.toString());
//
//        final LocalDateTime submittedAt = LocalDateTime.now();
//        final GameConversationJpaEntity existingConversation = getGameConversationJpaEntity(submittedAt);
//
//        when(conversationJpaRepository.findByUserIdAndGameIdWithQuestions(userId, gameId))
//                .thenReturn(existingConversation);
//
//        QuestionDto questionDto = new QuestionDto("Tell me more about the game.");
//        FollowUpQuestionDto followUpQuestionDto = new FollowUpQuestionDto(String.valueOf(gameId), questionDto);
//
//        when(pythonClientAdapter.getAnswerForFollowUpQuestion(any(), any(), any()))
//                .thenThrow(new PythonServiceException("An error occurred while calling the Python service."));
//
//        // Act
//        final ResultActions result = mockMvc.perform(post("/follow-up-question")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(followUpQuestionDto))
//                .with(jwt()
//                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
//                        .authorities(new SimpleGrantedAuthority("player"))));
//
//        // Assert
//        result.andExpect(status().isServiceUnavailable())
//                .andExpect(jsonPath("$.text").value("Python service is unavailable at the moment. Please try again later."));
//    }
//
//    @Test
//    void shouldReturnForbiddenWhenUserHasNoAuthority() throws Exception {
//
//        // Arrange
//        InitialQuestionDto initialQuestionDto = new InitialQuestionDto(gameId.toString());
//
//        // Act
//        final ResultActions result = mockMvc.perform(post("/initial-question")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(initialQuestionDto))
//                .with(jwt()
//                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))));
//
//        // Assert
//        result.andExpect(status().isForbidden());
//    }

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