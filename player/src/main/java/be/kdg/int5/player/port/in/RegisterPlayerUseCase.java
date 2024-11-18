package be.kdg.int5.player.port.in;

import be.kdg.int5.player.domain.Player;

public interface RegisterPlayerUseCase {
    void registerPlayer(RegisterPlayerCommand command);
}
