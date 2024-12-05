package be.kdg.int5.common.exceptions;

public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException(final String message) {
        super(message);
    }
}
