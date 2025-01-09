package be.kdg.int5.chatbot;

import be.kdg.int5.chatbot.domain.GameId;
import be.kdg.int5.chatbot.domain.UserId;

import java.util.UUID;

public class TestIds {
    public static final UserId USER_ID = new UserId(UUID.fromString("e4a40c63-2edf-4592-8d36-46b902db69d7"));
    public static final GameId GAME_ID = new GameId(UUID.fromString("d77e1d1f-6b46-4c89-9290-3b9cf8a7c020"));

    private TestIds() {
    }
}
