package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.PlayerGameStats;

import java.util.List;

public interface PlayerAchievementProgressForPlayedGamesQuery {
    List<PlayerGameStats> getAllPlayerAchievementProgressForGamesTheyPlayed(
            PlayerAchievementProgressForPlayedGamesCommand command
    );
}
