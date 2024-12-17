package be.kdg.int5.player;

import be.kdg.int5.player.adapters.out.db.playerLibrary.PlayerLibraryItemEmbeddable;
import be.kdg.int5.player.adapters.out.db.playerLibrary.PlayerLibraryJpaRepository;
import be.kdg.int5.player.adapters.out.db.playerLibrary.PlayerLibraryJpaEntity;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.HashSet;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class FavoriteGameControllerIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerLibraryJpaRepository playerLibraryJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
     void shouldUpdateGameFavoriteStatusWhenInputIsValid() throws Exception {
        // Arrange
        UUID playerId = Variables.PLAYER_ID;
        UUID gameId = Variables.GAME_ID;
        boolean newFavoriteStatus = true;

        PlayerLibraryJpaEntity playerLibraryJpaEntity = new PlayerLibraryJpaEntity();
        playerLibraryJpaEntity.setPlayerId(playerId);
        playerLibraryJpaEntity.setPlayerLibraryItems(new HashSet<>()); // Initialize the set here
        PlayerLibraryItemEmbeddable libraryItemEmbeddable = new PlayerLibraryItemEmbeddable(gameId, false, false);
        playerLibraryJpaEntity.addNewLibraryItem(libraryItemEmbeddable);

        // Mock the behavior of loading player library
        when(playerLibraryJpaRepository.findByPlayerIdWithLibraryItems(playerId)).thenReturn(playerLibraryJpaEntity);

        // Mock the update method
        ArgumentCaptor<PlayerLibraryJpaEntity> updateCaptor = ArgumentCaptor.forClass(PlayerLibraryJpaEntity.class);
        when(playerLibraryJpaRepository.save(updateCaptor.capture())).thenReturn(playerLibraryJpaEntity);

        // Act
        ResultActions result = mockMvc.perform(
                patch("/players/library/{gameId}/favourites", gameId)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", playerId.toString()))
                                .authorities(new SimpleGrantedAuthority("player")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"favourite\": true}")
        );

        // Assert
        result.andExpect(status().isNoContent());

        // Assert that the favorite status is updated
        PlayerLibraryJpaEntity updatedPlayerLibrary = updateCaptor.getValue();
        PlayerLibraryItemEmbeddable updatedLibraryItem = updatedPlayerLibrary.getPlayerLibraryItems()
                .stream()
                .filter(item -> item.getGameId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Game not found in player library"));

        assertTrue(updatedLibraryItem.isFavourite());
        verify(playerLibraryJpaRepository, times(1)).save(updatedPlayerLibrary);
    }

    @Test
    void shouldReturnBadRequestWhenNoFavouriteStatusProvided() throws Exception {
        // Arrange
        UUID playerId = Variables.PLAYER_ID;
        UUID gameId = Variables.GAME_ID;

        // Act & Assert
        mockMvc.perform(
                        patch("/players/library/{gameId}/favourites", gameId)
                                .with(jwt()
                                        .jwt(jwt -> jwt.claim("sub", playerId.toString()))
                                        .authorities(new SimpleGrantedAuthority("player")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")  // No favourite status
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnauthorizedWhenPlayerNotAuthenticated() throws Exception {
        // Arrange
        UUID gameId = Variables.GAME_ID;

        // Act & Assert
        mockMvc.perform(
                        patch("/players/library/{gameId}/favourites", gameId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"favourite\": true}")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnNotFoundWhenGameNotInPlayerLibrary() throws Exception {
        // Arrange
        UUID playerId = Variables.PLAYER_ID;
        UUID gameId = Variables.GAME_ID;

        PlayerLibraryJpaEntity playerLibraryJpaEntity = new PlayerLibraryJpaEntity();
        playerLibraryJpaEntity.setPlayerId(playerId);
        playerLibraryJpaEntity.setPlayerLibraryItems(new HashSet<>());

        when(playerLibraryJpaRepository.findByPlayerIdWithLibraryItems(playerId)).thenReturn(playerLibraryJpaEntity);

        // Act & Assert
        mockMvc.perform(
                        patch("/players/library/{gameId}/favourites", gameId)
                                .with(jwt()
                                        .jwt(jwt -> jwt.claim("sub", playerId.toString()))
                                        .authorities(new SimpleGrantedAuthority("player")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"favourite\": true}")
                )
                .andExpect(status().isNotFound());
    }
}
