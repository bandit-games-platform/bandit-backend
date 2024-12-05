package be.kdg.int5.chatbot.ports.in.query;

import be.kdg.int5.chatbot.domain.GameDetails;

public interface GameDetailsQuery {
    GameDetails loadGameDetails(GetGameDetailsCommand getGameDetailsCommand);
}
