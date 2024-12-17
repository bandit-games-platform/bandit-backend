package be.kdg.int5.gameplay.port.in;

import be.kdg.int5.gameplay.domain.LobbyJoinInfo;

public interface AcceptInviteUseCase {
    LobbyJoinInfo acceptInviteAndGetJoinInfo(AcceptInviteCommand command) throws NoSuchInviteException, LobbyClosedException;

    class NoSuchInviteException extends Exception {}
    class LobbyClosedException extends Exception {}
}
