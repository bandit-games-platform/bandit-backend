package be.kdg.int5.player.adapters.out.db.player;

import be.kdg.int5.player.adapters.out.db.playerFriend.PlayerFriendsJpaEntity;
import be.kdg.int5.player.domain.Country;
import be.kdg.int5.player.domain.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "player", name = "player")
public class PlayerJpaEntity {
    @Id
    private UUID id;
    private LocalDateTime joinDate;
    private String displayName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Country location;
    private LocalDate birthdate;
    @Embedded
    private ImageResourceJpaEmbed avatar;
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayerFriendsJpaEntity> friendsList;

    public PlayerJpaEntity() {
    }

    public PlayerJpaEntity(UUID id, LocalDateTime joinDate, String displayName) {
        this(id, joinDate, displayName, null, null, null, null, null);
    }

    public PlayerJpaEntity(UUID id, LocalDateTime joinDate, String displayName, Gender gender, Country location, LocalDate birthdate, ImageResourceJpaEmbed avatar, List<PlayerFriendsJpaEntity> friendsList) {
        this.id = id;
        this.joinDate = joinDate;
        this.displayName = displayName;
        this.gender = gender;
        this.location = location;
        this.birthdate = birthdate;
        this.avatar = avatar;
        this.friendsList = friendsList;
    }

    public PlayerJpaEntity(UUID id, String displayName, ImageResourceJpaEmbed avatar) {
        this(id, null, displayName, null, null, null, avatar, null);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
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

    public ImageResourceJpaEmbed getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageResourceJpaEmbed avatar) {
        this.avatar = avatar;
    }

    public List<PlayerFriendsJpaEntity> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<PlayerFriendsJpaEntity> friendsList) {
        this.friendsList = friendsList;
    }
}
