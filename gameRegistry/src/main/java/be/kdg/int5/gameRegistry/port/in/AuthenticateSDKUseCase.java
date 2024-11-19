package be.kdg.int5.gameRegistry.port.in;

public interface AuthenticateSDKUseCase {

    String authenticate(AuthenticateSDKCommand command) throws InvalidApiKeyException;

    class InvalidApiKeyException extends Exception {}
    class ImpersonationFailedException extends RuntimeException {}
}
