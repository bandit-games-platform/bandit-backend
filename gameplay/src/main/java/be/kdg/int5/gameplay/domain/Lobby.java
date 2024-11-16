package be.kdg.int5.gameplay.domain;

import java.util.List;

public class Lobby {
    private LobbyId id;
    private GameId gameId;
    private int maxPlayers;
    private PlayerId ownerId;
    private int currentPlayerCount;
    private boolean closed;
    private List<Invite> inviteList;

    public Lobby(LobbyId id, GameId gameId, int maxPlayers, PlayerId ownerId) {
        this.id = id;
        this.gameId = gameId;
        this.maxPlayers = maxPlayers;
        this.ownerId = ownerId;
    }
}
