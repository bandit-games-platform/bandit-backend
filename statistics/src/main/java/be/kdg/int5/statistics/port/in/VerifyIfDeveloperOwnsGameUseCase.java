package be.kdg.int5.statistics.port.in;

import be.kdg.int5.statistics.domain.GameId;

import java.util.UUID;

public interface VerifyIfDeveloperOwnsGameUseCase {
    boolean doesDeveloperOwnGame(VerifyIfDeveloperOwnsGameCommand command);
}
