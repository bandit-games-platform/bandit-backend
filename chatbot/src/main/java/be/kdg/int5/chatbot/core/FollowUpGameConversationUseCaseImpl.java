package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.in.FollowUpGameConversationCommand;
import be.kdg.int5.chatbot.ports.in.FollowUpGameConversationUseCase;
import be.kdg.int5.chatbot.ports.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowUpGameConversationUseCaseImpl implements FollowUpGameConversationUseCase {
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final ConversationSavePort conversationSavePort;
    private final ConversationLoadPort conversationLoadPort;
    private final AnswerAskPort answerAskPort;

    public FollowUpGameConversationUseCaseImpl(
            GameDetailsLoadPort gameDetailsLoadPort,
            ConversationSavePort conversationSavePort,
            ConversationLoadPort conversationLoadPort,
            AnswerAskPort answerAskPort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.conversationSavePort = conversationSavePort;
        this.conversationLoadPort = conversationLoadPort;
        this.answerAskPort = answerAskPort;
    }

    @Override
    @Transactional
    public Answer followUpGameConversation(FollowUpGameConversationCommand command) {
        final GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(command.gameId());

        final GameConversation gameConversation = conversationLoadPort.loadGameConversation(command.userId(), command.gameId());
        // TODO: deal with null conversation

        final Question followUpQuestion = gameConversation.addFollowUpQuestion(command.question());
        final List<Question> previousQuestionWindowList = gameConversation.getPreviousQuestionsInWindow();

        final Answer answer = answerAskPort.getAnswerForFollowUpQuestion(gameDetails, previousQuestionWindowList, followUpQuestion);

        followUpQuestion.update(answer);
        gameConversation.update(followUpQuestion);

        conversationSavePort.saveConversation(gameConversation);

        return answer;
    }
}
