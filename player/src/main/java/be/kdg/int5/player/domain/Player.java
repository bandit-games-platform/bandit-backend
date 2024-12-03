package be.kdg.int5.player.domain;

import be.kdg.int5.common.domain.ImageResource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Player {
    private final PlayerId id;
    private final LocalDateTime joinDate;
    private String displayName;
    private Gender gender;
    private Country location;
    private LocalDate birthdate;
    private ImageResource avatar;
    private List<Player> friendsList;

    public Player(final PlayerId id, final LocalDateTime joinDate, final String displayName, ImageResource avatar) {
        requireNonNull(id);
        requireNonNull(joinDate);
        requireNonNull(displayName);

        this.id = id;
        this.joinDate = joinDate;
        this.displayName = displayName;
        this.avatar = avatar;
        friendsList = new ArrayList<>();
    }

    public Player(final PlayerId id, final LocalDateTime joinDate, final String displayName) {
        this(id, joinDate, displayName, null);
    }

    public Player(final PlayerId id, final String displayName, ImageResource avatar) {
        this(id, LocalDateTime.now(), displayName, avatar);
    }

    public Player(final PlayerId id, final String displayName) {
        this(id, displayName, null);
    }


    public FriendInvite sendFriendInvite(final PlayerId invited){
        requireNonNull(invited);
        return new FriendInvite(new FriendInviteId(UUID.randomUUID()), this.id, invited, InviteStatus.PENDING, LocalDateTime.now());
    }

    public PlayerId getId() {
        return id;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
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

    public void addToFriendList(Player player) {
        Objects.requireNonNull(player);
        this.friendsList.add(player);
    }
}
