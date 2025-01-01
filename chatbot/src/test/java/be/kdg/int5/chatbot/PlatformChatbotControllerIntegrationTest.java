package be.kdg.int5.chatbot;

import be.kdg.int5.chatbot.adapters.in.dto.UpdatePlatformConversationDto;
import be.kdg.int5.chatbot.adapters.out.db.answer.AnswerJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.conversation.ConversationJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.conversation.ConversationJpaRepository;
import be.kdg.int5.chatbot.adapters.out.db.conversation.PlatformConversationJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.question.QuestionJpaEntity;
import be.kdg.int5.chatbot.adapters.out.python.PythonClientAdapter;
import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlatformChatbotControllerIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ConversationJpaRepository conversationJpaRepository;
    @MockBean
    private PythonClientAdapter pythonClientAdapter;

    private UUID userId;
    private LocalDateTime specificTime;

    @BeforeEach
    void setUp() {
        userId = TestIds.USER_ID.uuid();
        specificTime = LocalDateTime.now().minusHours(3);
    }

    @Test
    void shouldReturnDefaultAnswerWhenStartingNewConversation() throws Exception {
        // Arrange
        UpdatePlatformConversationDto updatePlatformConversationDto = new UpdatePlatformConversationDto("Initial Question");
        String expectedAnswer = "Hello and welcome to the platform, I am here to help you navigate around!";
        LocalDateTime now = LocalDateTime.now();

        when(conversationJpaRepository.save(any())).thenReturn(new ConversationJpaEntity());
        when(conversationJpaRepository.findPlatformConversationByUserIdAndLatestStartTime(userId))
                .thenReturn(null);
        when(conversationJpaRepository.findByUserIdAndStartTimeWithQuestions(any(), any()))
                .thenReturn(null);

        // Act
        final ResultActions result = mockMvc.perform(post("/chatbot/platform/home")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePlatformConversationDto))
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isOk()).andExpect(jsonPath("$.text").value(expectedAnswer));

        verify(conversationJpaRepository).saveAndFlush(any());
        verify(conversationJpaRepository).findByUserIdAndStartTimeWithQuestions(any(), any());
        verify(conversationJpaRepository).findPlatformConversationByUserIdAndLatestStartTime(userId);
    }

    @Test
    void shouldReturnChatbotAnswerWhenContinuingConversation() throws Exception {
        // Arrange
        UpdatePlatformConversationDto updatePlatformConversationDto = new UpdatePlatformConversationDto("What can I do here?");
        String expectedAnswer = "You can view your owned games, play games and buy new games...";

        PlatformConversationJpaEntity platformConversationJpaEntity = new PlatformConversationJpaEntity(
                userId,
                specificTime,
                specificTime,
                "home"
        );
        platformConversationJpaEntity.setQuestions(Stream.of((new QuestionJpaEntity(
                "Welcome to the chatroom!",
                specificTime,
                true,
                platformConversationJpaEntity,
                new AnswerJpaEntity("Hello and welcome to the platform, I am here to help you navigate around!")))
                ).collect(Collectors.toList())
        );

        when(conversationJpaRepository.save(any())).thenReturn(new ConversationJpaEntity());
        when(conversationJpaRepository.findPlatformConversationByUserIdAndLatestStartTime(userId))
                .thenReturn(platformConversationJpaEntity);
        when(conversationJpaRepository.findByUserIdAndStartTimeWithQuestions(any(), any()))
                .thenReturn(platformConversationJpaEntity);

        when(pythonClientAdapter.getAnswerForPlatformQuestion(
                eq("home"),
                any(),
                any())
        ).thenReturn(new Answer(expectedAnswer));

        // Act
        final ResultActions result = mockMvc.perform(post("/chatbot/platform/home")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePlatformConversationDto))
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(userId)))
                        .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isOk()).andExpect(jsonPath("$.text").value(expectedAnswer));

        verify(conversationJpaRepository, times(2)).saveAndFlush(any());
        verify(conversationJpaRepository, times(2)).findByUserIdAndStartTimeWithQuestions(any(), any());
        verify(conversationJpaRepository).findPlatformConversationByUserIdAndLatestStartTime(userId);

    }

}
