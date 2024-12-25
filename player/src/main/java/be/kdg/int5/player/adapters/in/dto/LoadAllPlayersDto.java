package be.kdg.int5.player.adapters.in.dto;
import jakarta.validation.constraints.NotNull;

public class LoadAllPlayersDto {
    private final String id;
    private final String username;
    private final String avatar;

    public LoadAllPlayersDto(String id, String username, String avatar) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

}
