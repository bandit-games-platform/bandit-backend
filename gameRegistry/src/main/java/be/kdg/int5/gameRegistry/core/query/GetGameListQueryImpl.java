package be.kdg.int5.gameRegistry.core.query;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GetGameListQuery;
import be.kdg.int5.gameRegistry.port.out.LoadGamesPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GetGameListQueryImpl implements GetGameListQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetGameListQueryImpl.class);

    private final LoadGamesPort loadGamesPort;

    public GetGameListQueryImpl(LoadGamesPort loadGamesPort) {
        this.loadGamesPort = loadGamesPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> retrieveGamesWithIcon() {
        LOGGER.info("Retrieving all games...");
        return loadGamesPort.loadAllGamesWithIcon();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> retrieveGamesByTitleLikeAndPriceBelowWithIcon(String title, BigDecimal price) {
        LOGGER.info("Retrieving all games with title and price like...");
        return loadGamesPort.loadAllGamesByTitleLikeAndPriceBelowWithIcon(title,price);
    }
}
