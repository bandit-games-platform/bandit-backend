package be.kdg.int5.statistics.port.in;

public interface VerifyIfAchievementBelongsToGameUseCase {
    boolean doesAchievementBelongToGame(VerifyIfAchievementBelongsToGameCommand command);
}
