package be.kdg.int5.player;

import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;

import java.util.UUID;

public class Variables {
    public static final UUID PLAYER_ONE_UUID = UUID.fromString("7c77e2cd-226b-4f71-97bc-fdbb31e327a7");
    public static final UUID PLAYER_TWO_UUID = UUID.fromString("33ad93d0-35c2-490a-b5a3-397684023bca");
    public static final String PLAYER_ONE_USERNAME = "Bobby Tables";
    public static final String PLAYER_TWO_USERNAME = "User Strikes Back";
    public static final String PLAYER_TWO_NEW_USERNAME = "Bobby Strikes Back";

    //player friends test variables
    public static final UUID PLAYER_ID = UUID.randomUUID();
    public static final Player player = new Player(new PlayerId(PLAYER_ID), "TestPlayer");
    public static final PlayerJpaEntity playerJpaEntity = new PlayerJpaEntity(PLAYER_ID, "TestPlayer");
    public static final UUID FRIEND_ID = UUID.randomUUID();
    public static final UUID friendInviteId = UUID.randomUUID();
    public static final UUID INVITER_ID = UUID.randomUUID();

    //player library favorites
    public static final UUID GAME_ID = UUID.randomUUID();
}
