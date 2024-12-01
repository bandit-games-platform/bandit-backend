package be.kdg.int5.player.adapters.in.dto;
import jakarta.validation.constraints.NotNull;

public class PlayerFriendBioDto {
    private final String id;
    @NotNull
    private final String username;
    @NotNull
    private final String avatar;
    private final boolean isExistingFriend;

    public PlayerFriendBioDto(String id, String username, String avatar, boolean isExistingFriend) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.isExistingFriend = isExistingFriend;
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

    public boolean isExistingFriend() {
        return isExistingFriend;
    }
}
