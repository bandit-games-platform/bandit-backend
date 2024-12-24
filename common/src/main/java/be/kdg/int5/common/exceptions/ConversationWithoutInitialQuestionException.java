package be.kdg.int5.common.exceptions;

public class ConversationWithoutInitialQuestionException extends RuntimeException {
    public ConversationWithoutInitialQuestionException(String message) {
        super(message);
    }
}
