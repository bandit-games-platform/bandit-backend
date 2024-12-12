package be.kdg.int5.statistics.core.query;

import be.kdg.int5.statistics.domain.AchievementProgress;
import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.port.in.query.GetStatisticsForGameCommand;
import be.kdg.int5.statistics.port.in.query.StatisticsForGameQuery;
import be.kdg.int5.statistics.port.out.AchievementProgressForGameLoadPort;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsForGameQueryImpl implements StatisticsForGameQuery {
    private final AchievementProgressForGameLoadPort achievementProgressForGameLoadPort;
    private final CompletedSessionLoadPort completedSessionLoadPort;


    public StatisticsForGameQueryImpl(AchievementProgressForGameLoadPort achievementProgressForGameLoadPort, CompletedSessionLoadPort completedSessionLoadPort) {
        this.achievementProgressForGameLoadPort = achievementProgressForGameLoadPort;
        this.completedSessionLoadPort = completedSessionLoadPort;
    }

    @Override
    public List<CompletedSession> getAllCompletedSessionsForGame(GetStatisticsForGameCommand command) {
        return completedSessionLoadPort.loadAllCompletedSessionsForGame(command.gameId());
    }

    @Override
    public List<AchievementProgress> getAllAchievementProgressForGame(GetStatisticsForGameCommand command) {
        return achievementProgressForGameLoadPort.loadAllAchievementProgressForGame(command.gameId());
    }
}
