package be.kdg.int5.chatbot.core;

import be.kdg.int5.chatbot.domain.Answer;
import be.kdg.int5.chatbot.domain.GameConversation;
import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.Question;
import be.kdg.int5.chatbot.ports.in.StartGameConversationUseCase;
import be.kdg.int5.chatbot.ports.in.StartGameConversationCommand;
import be.kdg.int5.chatbot.ports.out.ConversationLoadPort;
import be.kdg.int5.chatbot.ports.out.ConversationSavePort;
import be.kdg.int5.chatbot.ports.out.AnswerAskPort;
import be.kdg.int5.chatbot.ports.out.GameDetailsLoadPort;
import be.kdg.int5.chatbot.util.LockManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;

@Service
public class StartGameConversationUseCaseImpl implements StartGameConversationUseCase {
    private final GameDetailsLoadPort gameDetailsLoadPort;
    private final ConversationLoadPort conversationLoadPort;
    private final ConversationSavePort conversationSavePort;
    private final AnswerAskPort answerAskPort;

    private final LockManager lockManager = new LockManager();

    public StartGameConversationUseCaseImpl(
            GameDetailsLoadPort gameDetailsLoadPort,
            ConversationLoadPort conversationLoadPort,
            ConversationSavePort conversationSavePort,
            AnswerAskPort answerAskPort) {
        this.gameDetailsLoadPort = gameDetailsLoadPort;
        this.conversationLoadPort = conversationLoadPort;
        this.conversationSavePort = conversationSavePort;
        this.answerAskPort = answerAskPort;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Answer startGameConversation(StartGameConversationCommand command) {
        String lockKey = command.userId() + ":" + command.gameId();
        Lock lock = lockManager.getLock(lockKey);

        lock.lock();
        System.out.println("Thread " + Thread.currentThread().getName() + " acquired lock for key " + lockKey);

        try {
            System.out.println("Inside try block\n");
            final GameDetails gameDetails = gameDetailsLoadPort.loadGameDetailsByGameId(command.gameId());

            final GameConversation existingGameConversation = conversationLoadPort.loadGameConversation(command.userId(), command.gameId());

            if (existingGameConversation != null) {
                System.out.println("Inside existing conversation\n");
                Question initialQuestion = existingGameConversation.getInitialQuestion();

                if (initialQuestion == null) {
                    throw new IllegalStateException("Conversation exists but has no initial question.");
                }
                System.out.println("Returning previous answer\n");
                return initialQuestion.getAnswer();
            }

            final GameConversation gameConversation = new GameConversation(command.userId(), command.gameId());

            final Question initialQuestion = gameConversation.start();
            final Answer answer = answerAskPort.getAnswerForInitialQuestion(gameDetails, initialQuestion);
            System.out.println("Answer: " + answer);

            initialQuestion.update(answer);
            gameConversation.update(initialQuestion);

            conversationSavePort.saveConversation(gameConversation);

            return answer;
        } finally {
            System.out.println("Inside finally block\n");
            System.out.println("Thread " + Thread.currentThread().getName() + " releasing lock for key " + lockKey);
            lock.unlock();
        }
    }
}
