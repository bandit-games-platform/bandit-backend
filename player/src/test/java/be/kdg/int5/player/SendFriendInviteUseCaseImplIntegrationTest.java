package be.kdg.int5.player;

import be.kdg.int5.player.adapters.out.db.playerFriend.FriendInviteJpaEntity;
import be.kdg.int5.player.adapters.out.db.playerFriend.FriendInviteJpaRepository;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.adapters.out.db.player.PlayerJpaRepository;
import be.kdg.int5.player.domain.InviteStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
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

    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final UUID FRIEND_ID = UUID.randomUUID();


    @Test
    @WithMockUser(username = "test-player", authorities = {"player"})
    void shouldCreateFriendInviteWhenValidInput() throws Exception {
        // Arrange
        when(playerJpaRepository.getReferenceById(PLAYER_ID))
                .thenReturn(new PlayerJpaEntity(PLAYER_ID, "TestPlayer"));
        when(playerJpaRepository.getReferenceById(FRIEND_ID))
                .thenReturn(new PlayerJpaEntity(FRIEND_ID, "FriendPlayer"));

        when(friendInviteJpaRepository.findByInviter_IdAndInvited_Id(PLAYER_ID, FRIEND_ID))
                .thenReturn(null); // No existing invite.

        when(friendInviteJpaRepository.save(any(FriendInviteJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Simulate save operation.

        // Mock the JWT token
        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "none")
                .claim("sub", PLAYER_ID.toString())
                .claim("scope", "player")
                .build();

        // Act
        final ResultActions result = mockMvc
                .perform(post("/friends/invite-new-friends/" + FRIEND_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(new JwtAuthenticationToken(jwt))));

        // Assert
        result.andExpect(status().isCreated());

        ArgumentCaptor<FriendInviteJpaEntity> inviteCaptor = ArgumentCaptor.forClass(FriendInviteJpaEntity.class);
        verify(friendInviteJpaRepository, times(1)).save(inviteCaptor.capture());

        FriendInviteJpaEntity capturedInvite = inviteCaptor.getValue();
        assertEquals(capturedInvite.getInviter().getId(), PLAYER_ID);
        assertEquals(capturedInvite.getInvited().getId(), FRIEND_ID);
    }

    @Test
    @WithMockUser(authorities = {"player"})
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

        // Mock the JWT token
        Jwt jwt = Jwt.withTokenValue("mock-token")
                .header("alg", "none")
                .claim("sub", PLAYER_ID.toString())
                .build();

        // Act
        final ResultActions result = mockMvc
                .perform(post("/friends/invite-new-friends/" + FRIEND_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(authentication(new JwtAuthenticationToken(jwt))));

        // Assert
        result.andExpect(status().isConflict());
        verify(friendInviteJpaRepository, never()).save(any(FriendInviteJpaEntity.class));
    }
}
