package be.kdg.int5.chatbot.ports.out;

import be.kdg.int5.chatbot.domain.GameDetails;
import be.kdg.int5.chatbot.domain.GameId;

public interface GameDetailsLoadPort {
    GameDetails loadGameDetailsByGameId(GameId gameId);
}
