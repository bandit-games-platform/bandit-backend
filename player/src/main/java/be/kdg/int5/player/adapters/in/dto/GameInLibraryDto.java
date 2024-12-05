package be.kdg.int5.player.adapters.in.dto;

import java.util.UUID;

public class GameInLibraryDto {
    private UUID gameId;
    private boolean favourite;
    private boolean hidden;

    public GameInLibraryDto() {
    }

    public GameInLibraryDto(UUID gameId, boolean favourite, boolean hidden) {
        this.gameId = gameId;
        this.favourite = favourite;
        this.hidden = hidden;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
