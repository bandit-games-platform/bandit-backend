package be.kdg.int5.gameplay.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class NewGameInviteDto {
    @NotNull
    private UUID lobbyId;
    @NotNull
    private UUID invitedId;

    public NewGameInviteDto() {
    }

    public NewGameInviteDto(UUID lobbyId, UUID invitedId) {
        this.lobbyId = lobbyId;
        this.invitedId = invitedId;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public UUID getInvitedId() {
        return invitedId;
    }
}
