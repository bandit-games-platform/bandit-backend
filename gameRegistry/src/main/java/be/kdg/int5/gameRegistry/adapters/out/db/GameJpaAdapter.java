package be.kdg.int5.gameRegistry.adapters.out.db;

import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.out.LoadGamesPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameJpaAdapter implements LoadGamesPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameJpaAdapter.class);



    @Override
    public Optional<List<Game>> loadAllGames() {
        return Optional.empty();
    }
}
