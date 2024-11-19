package be.kdg.int5.player.domain;

import be.kdg.int5.common.domain.ImageResource;
import java.time.LocalDate;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class Player {
    private final PlayerId id;
    private String displayName;
    private Gender gender;
    private Country location;
    private LocalDate birthdate;
    private ImageResource avatar;
    private List<Player> friendsList;

    public Player(final PlayerId id, final String displayName) {
        requireNonNull(id);
        requireNonNull(displayName);

        this.id = id;
        this.displayName = displayName;
    }

    public PlayerId getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Country getLocation() {
        return location;
    }

    public void setLocation(Country location) {
        this.location = location;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public ImageResource getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageResource avatar) {
        this.avatar = avatar;
    }

    public List<Player> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<Player> friendsList) {
        this.friendsList = friendsList;
    }
}
