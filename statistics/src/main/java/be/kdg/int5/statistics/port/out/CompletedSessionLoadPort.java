package be.kdg.int5.statistics.port.out;

import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.domain.PlayerId;

import java.util.List;

public interface CompletedSessionLoadPort {
    List<CompletedSession> loadAllCompletedSessionsForPlayer(PlayerId playerId);
}
