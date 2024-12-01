package be.kdg.int5.statistics.core.query;

import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.port.in.query.PlayerTotalPlayTimeCommand;
import be.kdg.int5.statistics.port.in.query.PlayerTotalPlayTimeQuery;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerTotalPlayTimeQueryImpl implements PlayerTotalPlayTimeQuery {
    private final CompletedSessionLoadPort completedSessionLoadPort;

    public PlayerTotalPlayTimeQueryImpl(CompletedSessionLoadPort completedSessionLoadPort) {
        this.completedSessionLoadPort = completedSessionLoadPort;
    }

    @Override
    public long getTotalPlayTimeSecondsForPlayer(PlayerTotalPlayTimeCommand command) {
        List<CompletedSession> completedSessions = completedSessionLoadPort.loadAllCompletedSessionsForPlayer(command.playerId());

        long totalPlayTime = 0;

        for (CompletedSession completedSession: completedSessions) {
            totalPlayTime += completedSession.getPlayDurationInSeconds();
        }

        return totalPlayTime;
    }
}
