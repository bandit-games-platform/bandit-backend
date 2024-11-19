package be.kdg.int5.chatbot.ports.out;

import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.GameId;

import java.util.Optional;

public interface GameDetailsLoadPort {
    Optional<GameDetails> loadGameDetailsByGameId(GameId gameId);
}
