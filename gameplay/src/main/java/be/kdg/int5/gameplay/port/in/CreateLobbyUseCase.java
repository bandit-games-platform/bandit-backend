package be.kdg.int5.gameplay.port.in;

import be.kdg.int5.gameplay.domain.LobbyId;

public interface CreateLobbyUseCase {
    LobbyId create(CreateLobbyCommand command);
}
