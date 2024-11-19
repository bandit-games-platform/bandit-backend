package be.kdg.int5.player.adapters.in.dto;

import jakarta.validation.constraints.NotNull;

public class NewPlayerRegistrationDto {
    @NotNull
    private String userId;
    @NotNull
    private String username;

    public NewPlayerRegistrationDto() {
    }

    public NewPlayerRegistrationDto(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
