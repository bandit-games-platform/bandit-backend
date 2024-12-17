package be.kdg.int5.gameplay.adapters.in.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;
import java.util.UUID;

public class GetGameInviteDto {
    private UUID inviteId;
    private UUID inviterId;
    private UUID gameId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String inviterName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String gameTitle;

    public GetGameInviteDto() {
    }

    public GetGameInviteDto(UUID inviteId, UUID inviterId, UUID gameId) {
        this(inviteId, inviterId, null, gameId, null);
    }

    public GetGameInviteDto(UUID inviteId, UUID inviterId, String inviterName, UUID gameId, String gameTitle) {
        this.inviteId = Objects.requireNonNull(inviteId);
        this.inviterId = Objects.requireNonNull(inviterId);
        this.inviterName = inviterName;
        this.gameId = Objects.requireNonNull(gameId);
        this.gameTitle = gameTitle;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public UUID getInviteId() {
        return inviteId;
    }

    public UUID getInviterId() {
        return inviterId;
    }

    public String getInviterName() {
        return inviterName;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }
}
