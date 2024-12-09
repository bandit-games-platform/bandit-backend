package be.kdg.int5.player.adapters.in.dto;

public class PlayerFriendBioDto {
    private final String id;
    private final String username;
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
