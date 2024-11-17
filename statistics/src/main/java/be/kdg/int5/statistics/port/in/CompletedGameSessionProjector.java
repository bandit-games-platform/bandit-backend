package be.kdg.int5.statistics.port.in;

@FunctionalInterface
public interface CompletedGameSessionProjector {
    void completedGameSession(CompletedGameSessionCommand command);
}
