package be.kdg.int5.gameplay.port.in;

import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.domain.PlayerId;

public record CreateGameInviteCommand(LobbyId lobbyId, PlayerId invitedId, PlayerId inviterId) {
}
