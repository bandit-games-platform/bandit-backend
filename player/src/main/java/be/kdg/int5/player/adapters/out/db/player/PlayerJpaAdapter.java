package be.kdg.int5.player.adapters.out.db.player;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.out.CreatePlayerPort;
import be.kdg.int5.player.port.out.LoadPlayerPort;
import be.kdg.int5.player.port.out.UpdatePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PlayerJpaAdapter implements CreatePlayerPort, LoadPlayerPort, UpdatePlayerPort {
    private final PlayerJpaRepository playerJpaRepository;

    public PlayerJpaAdapter(PlayerJpaRepository playerJpaRepository) {
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public void createPlayer(Player player) {
        PlayerJpaEntity playerJpaEntity = new PlayerJpaEntity(player.getId().uuid(), player.getDisplayName());
        playerJpaRepository.save(playerJpaEntity);
    }

    @Override
    public Player loadPlayerById(UUID playerId) {
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findById(playerId).orElse(null);
        if (playerJpaEntity == null) return null;
        return new Player(new PlayerId(playerJpaEntity.getId()), playerJpaEntity.getDisplayName());
    }

    @Override
    @Transactional
    public void updatePlayerDisplayName(Player player) {
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findById(player.getId().uuid()).orElse(null);
        if (playerJpaEntity == null) return;
        playerJpaEntity.setDisplayName(player.getDisplayName());
        playerJpaRepository.save(playerJpaEntity);
    }
}
