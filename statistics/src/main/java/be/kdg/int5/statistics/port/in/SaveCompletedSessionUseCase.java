package be.kdg.int5.statistics.port.in;

public interface SaveCompletedSessionUseCase {
    void saveCompletedGameSession(SaveCompletedSessionCommand command);
}
