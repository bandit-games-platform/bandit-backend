package be.kdg.int5.chatbot.adapters.out.conversation;

import be.kdg.int5.chatbot.adapters.out.answer.AnswerJpaEntity;
import be.kdg.int5.chatbot.adapters.out.question.QuestionJpaEntity;
import be.kdg.int5.chatbot.domain.*;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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
                    final Question question = new Question(q.getText());
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
        ConversationJpaEntity conversationJpa = conversationJpaRepository.findByUserIdAndStartTime(conversation.getUserId().uuid(), conversation.getStartTime());

        if (conversationJpa == null) {
            conversationJpa = toConservationJpa(conversation);
        } else {
            conversationJpa.setLastMessageTime(conversation.getLastMessageTime());
        }

        final ConversationJpaEntity finalConversationJpa = conversationJpa; // apparently you can't use temp variables inside a lambda (see below)
        final List<QuestionJpaEntity> questionJpaList = conversation.getQuestions().stream()
                .map(q -> {
                    AnswerJpaEntity answerJpa = new AnswerJpaEntity();
                    answerJpa.setText(q.getAnswer().text());

                    QuestionJpaEntity questionJpa = new QuestionJpaEntity();
                    questionJpa.setText(q.getText());
                    questionJpa.setConversation(finalConversationJpa); // here
                    questionJpa.setAnswer(answerJpa);

                    return questionJpa;
                })
                .collect(Collectors.toCollection(ArrayList::new)); // .toList() would return an immutable list (to which I need to add to in the use case)

        conversationJpa.setQuestions(questionJpaList);

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
