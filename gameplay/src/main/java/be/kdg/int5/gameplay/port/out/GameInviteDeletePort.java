package be.kdg.int5.gameplay.port.out;

import be.kdg.int5.gameplay.domain.GameInviteId;

public interface GameInviteDeletePort {
    void deleteById(GameInviteId id);
}
