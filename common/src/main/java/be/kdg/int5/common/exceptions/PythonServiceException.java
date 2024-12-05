package be.kdg.int5.common.exceptions;

public class PythonServiceException extends RuntimeException {
    public PythonServiceException(String message) {
        super(message);
    }

    public PythonServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
