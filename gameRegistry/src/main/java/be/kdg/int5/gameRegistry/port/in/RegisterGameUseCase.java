package be.kdg.int5.gameRegistry.port.in;

import be.kdg.int5.gameRegistry.domain.GameId;

public interface RegisterGameUseCase {
    GameId registerGame(RegisterGameCommand command);
}
