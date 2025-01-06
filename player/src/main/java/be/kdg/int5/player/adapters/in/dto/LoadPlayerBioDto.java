package be.kdg.int5.player.adapters.in.dto;

public class LoadPlayerBioDto {
    private final String id;
    private final String username;
    private final String avatar;

    public LoadPlayerBioDto(String id, String username, String avatar) {
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
