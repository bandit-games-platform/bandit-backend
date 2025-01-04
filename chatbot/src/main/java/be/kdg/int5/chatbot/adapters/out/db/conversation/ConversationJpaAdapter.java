package be.kdg.int5.chatbot.adapters.out.db.conversation;

import be.kdg.int5.chatbot.adapters.out.db.answer.AnswerJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.question.QuestionJpaEntity;
import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return toGameConversation(gameConversationJpa);
    }

    @Override
    public PlatformConversation loadPlatformConversation(UserId userId) {
        PlatformConversationJpaEntity platformConversationJpaEntity = conversationJpaRepository.findPlatformConversationByUserIdAndLatestStartTime(userId.uuid());
        if (platformConversationJpaEntity == null) return null;
        return toPlatformConversation(platformConversationJpaEntity);
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
            LocalDateTime questionTime = question.getSubmittedAt().withNano(0);
            QuestionJpaEntity questionJpa = existingQuestionsMap.get(questionTime);

            if (questionJpa == null) {
                // New question: create and add it
                questionJpa = new QuestionJpaEntity();
                questionJpa.setText(question.getText());
                questionJpa.setSubmittedAt(questionTime);
                questionJpa.setInitial(question.isInitial());

                if (question.getAnswer() != null) {
                    AnswerJpaEntity answerJpa = new AnswerJpaEntity();
                    answerJpa.setText(question.getAnswer().text());
                    questionJpa.setAnswer(answerJpa);
                }

                questionJpa.setConversation(conversationJpa);
                conversationJpa.getQuestions().add(questionJpa);
            } else if (questionJpa.getAnswer() == null) {
                AnswerJpaEntity answerJpa = new AnswerJpaEntity();
                answerJpa.setText(question.getAnswer().text());
                questionJpa.setAnswer(answerJpa);
            }
        }

        if (conversation instanceof PlatformConversation) {
            PlatformConversationJpaEntity platformConversationJpaEntity = (PlatformConversationJpaEntity) conversationJpa;
            platformConversationJpaEntity.setCurrentPage(((PlatformConversation) conversation).getCurrentPage());
            conversationJpaRepository.saveAndFlush(platformConversationJpaEntity);
        } else {
            conversationJpaRepository.save(conversationJpa);
        }
    }

    private GameConversation toGameConversation(GameConversationJpaEntity gameConversationJpa) {
        final GameConversation gameConversation = new GameConversation(
                new UserId(gameConversationJpa.getUserId()),
                gameConversationJpa.getStartTime(),
                gameConversationJpa.getLastMessageTime(),
                new GameId(gameConversationJpa.getGameId())
        );

        final List<Question> questionList = gameConversationJpa.getQuestions().stream()
                .map(q -> {
                    final Question question = new Question(q.getText(), q.getSubmittedAt(), q.getInitial());
                    final Answer answer = new Answer(q.getAnswer().getText());
                    question.setAnswer(answer);
                    return question;
                })
                .collect(Collectors.toList());

        gameConversation.setQuestions(questionList);
        return gameConversation;
    }

    private PlatformConversation toPlatformConversation(PlatformConversationJpaEntity platformConversationJpa) {
        PlatformConversation platformConversation = new PlatformConversation(
                new UserId(platformConversationJpa.getUserId()),
                platformConversationJpa.getStartTime(),
                platformConversationJpa.getLastMessageTime(),
                platformConversationJpa.getCurrentPage()
        );

        final List<Question> questionList = platformConversationJpa.getQuestions().stream()
                .map(q -> {
                    final Question question = new Question(q.getText(), q.getSubmittedAt(), q.getInitial());
                    if (q.getAnswer() != null) {
                        final Answer answer = new Answer(q.getAnswer().getText());
                        question.setAnswer(answer);
                    }
                    return question;
                })
                .collect(Collectors.toList());

        platformConversation.setQuestions(questionList);
        return platformConversation;
    }

    private ConversationJpaEntity toConservationJpa(Conversation conversation) {
        if (conversation instanceof GameConversation) {
            return new GameConversationJpaEntity(
                    conversation.getUserId().uuid(),
                    conversation.getStartTime(),
                    conversation.getLastMessageTime(),
                    ((GameConversation) conversation).getGameId().uuid());
        }else if (conversation instanceof PlatformConversation) {
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
