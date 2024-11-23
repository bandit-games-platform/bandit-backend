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
        // load gameDetails
        final GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(command.gameId());

        // load conversation
        final GameConversation gameConversation = conversationLoadPort.loadGameConversation(command.userId(), command.gameId());

        // add a new question
        final Question followUpQuestion = gameConversation.addFollowUpQuestion(command.question());
        final List<Question> previousQuestionWindowList = gameConversation.getPreviousQuestionsInWindow();
        final Answer answer = answerAskPort.getAnswerForFollowUpQuestion(gameDetails, previousQuestionWindowList, followUpQuestion);

        // update question and conversation
        followUpQuestion.update(answer);
        gameConversation.update(followUpQuestion);

        // save conservation
        conversationSavePort.saveConversation(gameConversation);

        // return answer
        return answer;
    }
}
