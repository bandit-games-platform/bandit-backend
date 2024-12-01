package be.kdg.int5.chatbot.ports.in.query;

import be.kdg.int5.chatbot.domain.GameId;

import java.util.Objects;

public record GetGameDetailsCommand(GameId gameId) {
    public GetGameDetailsCommand {
        Objects.requireNonNull(gameId);
    }
}
