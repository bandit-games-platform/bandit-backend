package be.kdg.int5.statistics;

import java.time.LocalDateTime;
import java.util.UUID;

public class Variables {
    public static final UUID GAME_ID = UUID.fromString("71ed375a-5915-4a0c-a428-de805dc447f6");
    public static final UUID PLAYER_ONE_UUID = UUID.fromString("7c77e2cd-226b-4f71-97bc-fdbb31e327a7");
    public static final UUID PLAYER_TWO_UUID = UUID.fromString("33ad93d0-35c2-490a-b5a3-397684023bca");
    public static final UUID SAMPLE_SESSION_ID = UUID.fromString("1dba54bd-02f4-4de0-917e-5d50aef34f4b");
    public static final LocalDateTime SAMPLE_SESSION_START_TIME = LocalDateTime.now().minusHours(2);
    public static final LocalDateTime SAMPLE_SESSION_END_TIME = LocalDateTime.now().minusHours(1);
    public static final LocalDateTime NEW_SESSION_START_TIME = LocalDateTime.now().minusHours(1).minusMinutes(45);
    public static final LocalDateTime NEW_SESSION_END_TIME = LocalDateTime.now().minusMinutes(45);
}
