package be.kdg.int5.gameRegistry.core.query;

import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.GameId;
import be.kdg.int5.gameRegistry.port.in.query.GetGameAchievementsQuery;
import be.kdg.int5.gameRegistry.port.out.AchievementsLoadPort;
import be.kdg.int5.gameRegistry.port.out.GamesLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetGameAchievementsQueryImpl implements GetGameAchievementsQuery {
    private final AchievementsLoadPort achievementsLoadPort;

    public GetGameAchievementsQueryImpl(AchievementsLoadPort achievementsLoadPort) {
        this.achievementsLoadPort = achievementsLoadPort;
    }


    @Override
    public List<Achievement> getAchievementsForGameFromId(UUID gameId) {
        return achievementsLoadPort.loadGameAchievements(new GameId(gameId));
    }
}
