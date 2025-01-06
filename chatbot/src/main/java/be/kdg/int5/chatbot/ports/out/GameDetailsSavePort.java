package be.kdg.int5.chatbot.ports.out;

import be.kdg.int5.chatbot.domain.GameDetails;

public interface GameDetailsSavePort {
    void saveNewGameDetails(GameDetails gameDetails);
    void updateGameDetails(GameDetails gameDetails);
}
