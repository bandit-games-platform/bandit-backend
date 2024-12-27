package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.PlatformConversation;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.domain.UserId;
import be.kdg.int5.chatbot.ports.in.PlatformConversationCommand;
import be.kdg.int5.chatbot.ports.in.PlatformConversationUseCase;
import be.kdg.int5.chatbot.ports.out.AnswerAskPort;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PlatformConversationUseCaseImpl implements PlatformConversationUseCase {
    private final ConversationSavePort conversationSavePort;
    private final ConversationLoadPort conversationLoadPort;
    private final AnswerAskPort answerAskPort;

    public PlatformConversationUseCaseImpl(
            ConversationSavePort conversationSavePort,
            ConversationLoadPort conversationLoadPort,
            AnswerAskPort answerAskPort
    ) {
        this.conversationSavePort = conversationSavePort;
        this.conversationLoadPort = conversationLoadPort;
        this.answerAskPort = answerAskPort;
    }

    @Override
    @Transactional
    public PlatformConversation updateConversation(PlatformConversationCommand command) {
        PlatformConversation platformConversation = conversationLoadPort.loadPlatformConversation(new UserId(command.userId()));

        if (platformConversation == null) {
            platformConversation = new PlatformConversation(new UserId(command.userId()), command.page());
            Question startQuestion = platformConversation.start();
            platformConversation.update(startQuestion);
            conversationSavePort.saveConversation(platformConversation);
        } else {
            if (ChronoUnit.HOURS.between(platformConversation.getLastMessageTime(), LocalDateTime.now()) > 6) {
                platformConversation = new PlatformConversation(new UserId(command.userId()), command.page());
                platformConversation.start();
                conversationSavePort.saveConversation(platformConversation);
            } else {
                platformConversation.setCurrentPage(command.page());
                Question newQuestion = platformConversation.addFollowUpQuestion(command.question());
                List<Question> previousQuestionWindowList = platformConversation.getPreviousQuestionsInWindow();

                Answer answer = answerAskPort.getAnswerForPlatformQuestion(
                        platformConversation.getCurrentPage(),
                        previousQuestionWindowList,
                        newQuestion
                );

                newQuestion.update(answer);
                platformConversation.update(newQuestion);
                conversationSavePort.saveConversation(platformConversation);
            }
        }

        return platformConversation;
    }
}
