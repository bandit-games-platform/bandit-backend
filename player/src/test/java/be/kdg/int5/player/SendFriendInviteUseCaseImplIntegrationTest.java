package be.kdg.int5.player;

import be.kdg.int5.player.adapters.out.db.playerFriend.FriendInviteJpaEntity;
import be.kdg.int5.player.adapters.out.db.playerFriend.FriendInviteJpaRepository;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaRepository;
import be.kdg.int5.player.domain.InviteStatus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static be.kdg.int5.player.Variables.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class SendFriendInviteUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerJpaRepository playerJpaRepository;

    @MockBean
    private FriendInviteJpaRepository friendInviteJpaRepository;

    @Test
    void shouldCreateFriendInviteWhenValidInput() throws Exception {
        // Arrange
        when(playerJpaRepository.getReferenceById(PLAYER_ID))
                .thenReturn(new PlayerJpaEntity(player.getId().uuid(), player.getDisplayName()));
        when(playerJpaRepository.getReferenceById(FRIEND_ID))
                .thenReturn(new PlayerJpaEntity(FRIEND_ID, "FriendPlayer"));

        when(friendInviteJpaRepository.findByInviter_IdAndInvited_Id(PLAYER_ID, FRIEND_ID))
                .thenReturn(null); // No existing invite.

        when(friendInviteJpaRepository.save(any(FriendInviteJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Simulate save operation.

        when(playerJpaRepository.findById(PLAYER_ID))
                .thenReturn(Optional.of(playerJpaEntity));

        // Act
        final ResultActions result = mockMvc
                .perform(post("/player/friends/invite-new-friends/" + FRIEND_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", String.valueOf(PLAYER_ID)))
                                .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isCreated());

        ArgumentCaptor<FriendInviteJpaEntity> inviteCaptor = ArgumentCaptor.forClass(FriendInviteJpaEntity.class);
        verify(friendInviteJpaRepository, times(1)).save(inviteCaptor.capture());

        FriendInviteJpaEntity capturedInvite = inviteCaptor.getValue();
        assertEquals(capturedInvite.getInviter().getId(), PLAYER_ID);
        assertEquals(capturedInvite.getInvited().getId(), FRIEND_ID);
    }

    @Test
    void shouldReturnConflictWhenFriendInviteAlreadyExists() throws Exception {
        // Arrange
        when(playerJpaRepository.getReferenceById(PLAYER_ID))
                .thenReturn(new PlayerJpaEntity(PLAYER_ID, "TestPlayer"));
        when(playerJpaRepository.getReferenceById(FRIEND_ID))
                .thenReturn(new PlayerJpaEntity(FRIEND_ID, "FriendPlayer"));

        FriendInviteJpaEntity existingInvite = new FriendInviteJpaEntity(UUID.randomUUID(),
                new PlayerJpaEntity(PLAYER_ID, "TestPlayer"),
                new PlayerJpaEntity(FRIEND_ID, "FriendPlayer"),
                InviteStatus.PENDING,
                LocalDateTime.now());

        when(friendInviteJpaRepository.findByInviter_IdAndInvited_Id(PLAYER_ID, FRIEND_ID))
                .thenReturn(existingInvite); // Simulate an existing invite.

        when(playerJpaRepository.findById(PLAYER_ID))
                .thenReturn(Optional.of(playerJpaEntity));

        // Act
        final ResultActions result = mockMvc
                .perform(post("/player/friends/invite-new-friends/" + FRIEND_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", String.valueOf(PLAYER_ID)))
                                .authorities(new SimpleGrantedAuthority("player"))));

        // Assert
        result.andExpect(status().isConflict());
        verify(friendInviteJpaRepository, never()).save(any(FriendInviteJpaEntity.class));
    }
}
