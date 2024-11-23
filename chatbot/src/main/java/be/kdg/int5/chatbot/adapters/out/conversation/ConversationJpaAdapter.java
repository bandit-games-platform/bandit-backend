package be.kdg.int5.chatbot.adapters.out.conversation;

import be.kdg.int5.chatbot.adapters.out.answer.AnswerJpaEntity;
import be.kdg.int5.chatbot.adapters.out.question.QuestionJpaEntity;
import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ConversationJpaAdapter implements ConversationLoadPort, ConversationSavePort {
    private final ConversationJpaRepository conversationJpaRepository;

    public ConversationJpaAdapter(ConversationJpaRepository conversationJpaRepository) {
        this.conversationJpaRepository = conversationJpaRepository;
    }

    @Override
    public GameConversation loadGameConversation(UserId userId, GameId gameId) {
        final GameConversationJpaEntity gameConversationJpa = conversationJpaRepository.findByUserIdAndGameIdWithQuestions(userId.uuid(), gameId.uuid());

        if (gameConversationJpa == null) return null;

        final GameConversation gameConversation = new GameConversation(
                new UserId(gameConversationJpa.getUserId()),
                gameConversationJpa.getStartTime(),
                gameConversationJpa.getLastMessageTime(),
                new GameId(gameConversationJpa.getGameId())
        );

        final List<Question> questionList = gameConversationJpa.getQuestions().stream()
                .map(q -> {
                    final Question question = new Question(q.getText(), q.getSubmittedAt());
                    final Answer answer = new Answer(q.getAnswer().getText());
                    question.setAnswer(answer);
                    return question;
                })
                .collect(Collectors.toCollection(ArrayList::new)); // .toList() would return an immutable list (to which I need to add to in the use case)

        gameConversation.setQuestions(questionList);

        return gameConversation;
    }

    @Override
    public void saveConversation(Conversation conversation) {
        ConversationJpaEntity conversationJpa = conversationJpaRepository.findByUserIdAndStartTimeWithQuestions(conversation.getUserId().uuid(), conversation.getStartTime());

        if (conversationJpa == null) {
            conversationJpa = toConservationJpa(conversation);
        } else {
            conversationJpa.setLastMessageTime(conversation.getLastMessageTime());
        }

        // Use a map to track existing questions by their questionTime for efficient lookup
        Map<LocalDateTime, QuestionJpaEntity> existingQuestionsMap = conversationJpa.getQuestions().stream()
                .collect(Collectors.toMap(QuestionJpaEntity::getSubmittedAt, q -> q));

        // Add new questions or update existing ones based on questionTime
        for (Question question : conversation.getQuestions()) {
            LocalDateTime questionTime = question.getSubmittedAt();
            QuestionJpaEntity questionJpa = existingQuestionsMap.get(questionTime);

            if (questionJpa == null) {
                // New question: create and add it
                questionJpa = new QuestionJpaEntity();
                questionJpa.setText(question.getText());
                questionJpa.setSubmittedAt(questionTime);

                AnswerJpaEntity answerJpa = new AnswerJpaEntity();
                answerJpa.setText(question.getAnswer().text());
                questionJpa.setAnswer(answerJpa);

                questionJpa.setConversation(conversationJpa);
                conversationJpa.getQuestions().add(questionJpa);
            }
        }

        conversationJpaRepository.save(conversationJpa);
    }

    private ConversationJpaEntity toConservationJpa(Conversation conversation) {
        if (conversation instanceof GameConversation) {
            return new GameConversationJpaEntity(
                    conversation.getUserId().uuid(),
                    conversation.getStartTime(),
                    conversation.getLastMessageTime(),
                    ((GameConversation) conversation).getGameId().uuid());
        }

        if (conversation instanceof PlatformConversation) {
            return new PlatformConversationJpaEntity(
                    conversation.getUserId().uuid(),
                    conversation.getStartTime(),
                    conversation.getLastMessageTime(),
                    ((PlatformConversation) conversation).getCurrentPage());
        } else {
            throw new IllegalArgumentException("Unsupported conversation type: " + conversation.getClass().getName());
        }
    }
}
