package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.in.FollowUpGameConversationCommand;
import be.kdg.int5.chatbot.ports.in.FollowUpGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.chatbot.ports.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class FollowUpGameConversationUseCaseImpl implements FollowUpGameConversationUseCase {
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final ConversationSavePort conversationSavePort;
    private final ConversationLoadPort conversationLoadPort;
    private final ConversationFollowUpPort conversationFollowUpPort;


    public FollowUpGameConversationUseCaseImpl(
            GameDetailsLoadPort gameDetailsLoadPort,
            ConversationSavePort conversationSavePort,
            ConversationLoadPort conversationLoadPort,
            ConversationFollowUpPort conversationFollowUpPort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.conversationSavePort = conversationSavePort;
        this.conversationLoadPort = conversationLoadPort;
        this.conversationFollowUpPort = conversationFollowUpPort;
    }

    @Override
    @Transactional
    public Answer followUpGameConversation(FollowUpGameConversationCommand command) {
        // load GameDetails
        final GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(command.gameId());

        // load conversation
        final GameConversation gameConversation = conversationLoadPort.loadGameConversation(command.userId(), command.gameId());

        // create a new question
        final Question followUpQuestion = new Question(command.question(), LocalDateTime.now());
        final Answer answer = conversationFollowUpPort.followUpOnGameConversation(gameDetails, gameConversation, followUpQuestion);

        // add question-answer pair to the conversation
        followUpQuestion.setAnswer(answer);
        gameConversation.addQuestion(followUpQuestion);

        // save conservation
        conversationSavePort.saveConversation(gameConversation);

        // return conversation
        return answer;
    }
}
