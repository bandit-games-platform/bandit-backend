package be.kdg.int5.player;

import be.kdg.int5.player.adapters.out.db.player.PlayerJpaEntity;
import be.kdg.int5.player.adapters.out.db.playerFriend.FriendInviteJpaEntity;
import be.kdg.int5.player.adapters.out.db.playerFriend.FriendInviteJpaRepository;
import be.kdg.int5.player.adapters.out.db.playerFriend.FriendsRelationJpaRepository;
import be.kdg.int5.player.adapters.out.db.playerFriend.FriendsRelationJpaEntity;
import be.kdg.int5.player.domain.InviteStatus;
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
public class ProcessPendingFriendInviteIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FriendInviteJpaRepository friendInviteJpaRepository;

    @MockBean
    private FriendsRelationJpaRepository friendsRelationJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void shouldAcceptFriendInviteWhenValidInput() throws Exception {
        // Arrange
        // Mock the invite entity
        FriendInviteJpaEntity inviteEntity = new FriendInviteJpaEntity(
                friendInviteId,
                new PlayerJpaEntity(INVITER_ID),
                new PlayerJpaEntity(PLAYER_ID),
                InviteStatus.PENDING,
                LocalDateTime.now()
        );

        // Mock the invite entity
        FriendInviteJpaEntity savedEntity = new FriendInviteJpaEntity(
                friendInviteId,
                new PlayerJpaEntity(INVITER_ID),
                new PlayerJpaEntity(PLAYER_ID),
                InviteStatus.ACCEPTED,
                LocalDateTime.now()
        );

        FriendsRelationJpaEntity storedFriendEntity = new FriendsRelationJpaEntity(
                UUID.randomUUID(),
                new PlayerJpaEntity(savedEntity.getInvited().getId()),
                new PlayerJpaEntity(savedEntity.getInviter().getId()
                ), LocalDateTime.now());

        // Mocks the repository behavior for fetching the invite
        when(friendInviteJpaRepository.findById(friendInviteId)).thenReturn(Optional.of(inviteEntity));

        // Captures the entities being saved
        ArgumentCaptor<FriendInviteJpaEntity> saveCaptor = ArgumentCaptor.forClass(FriendInviteJpaEntity.class);
        ArgumentCaptor<FriendsRelationJpaEntity> playerFriendCaptor = ArgumentCaptor.forClass(FriendsRelationJpaEntity.class);

        // Mocks save methods
        when(friendInviteJpaRepository.save(saveCaptor.capture())).thenReturn(savedEntity);
        when(friendsRelationJpaRepository.save(playerFriendCaptor.capture())).thenReturn(storedFriendEntity);

        // Act
        ResultActions result = mockMvc.perform(
                post("/player/friends/pending-invites/" + friendInviteId + "?action=accept")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", PLAYER_ID.toString()))
                                .authorities(new SimpleGrantedAuthority("player")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Asserts the response status
        result.andExpect(status().isCreated());

        // Asserts invite status update
        FriendInviteJpaEntity savedInviteEntity = saveCaptor.getValue();
        assertEquals(friendInviteId, savedInviteEntity.getId());
        assertEquals(InviteStatus.ACCEPTED, savedInviteEntity.getStatus());

        // Assert friendship creation
        FriendsRelationJpaEntity savedFriendEntity = playerFriendCaptor.getValue();
        assertEquals(INVITER_ID, savedFriendEntity.getPlayerA().getId());
        assertEquals(PLAYER_ID, savedFriendEntity.getPlayerB().getId());

        verify(friendsRelationJpaRepository, times(1)).save(playerFriendCaptor.capture());
    }


    @Test
    void shouldRejectFriendInviteWhenValidInput() throws Exception {
        // Arrange
        FriendInviteJpaEntity inviteEntity = new FriendInviteJpaEntity(
                friendInviteId,
                new PlayerJpaEntity(INVITER_ID),
                new PlayerJpaEntity(PLAYER_ID),
                InviteStatus.PENDING,
                LocalDateTime.now()
        );

        // Mock the invite entity
        FriendInviteJpaEntity savedEntity = new FriendInviteJpaEntity(
                friendInviteId,
                new PlayerJpaEntity(INVITER_ID),
                new PlayerJpaEntity(PLAYER_ID),
                InviteStatus.ACCEPTED,
                LocalDateTime.now()
        );

        // Mocks the repository behavior for fetching the invite
        when(friendInviteJpaRepository.findById(friendInviteId)).thenReturn(Optional.of(inviteEntity));

        // Captures the entities being saved
        ArgumentCaptor<FriendInviteJpaEntity> saveCaptor = ArgumentCaptor.forClass(FriendInviteJpaEntity.class);
        ArgumentCaptor<FriendsRelationJpaEntity> playerFriendCaptor = ArgumentCaptor.forClass(FriendsRelationJpaEntity.class);

        // Mocks save methods
        when(friendInviteJpaRepository.save(saveCaptor.capture())).thenReturn(savedEntity);
        when(friendsRelationJpaRepository.save(playerFriendCaptor.capture())).thenReturn(new FriendsRelationJpaEntity(
                UUID.randomUUID(),
                new PlayerJpaEntity(savedEntity.getInvited().getId()),
                new PlayerJpaEntity(savedEntity.getInviter().getId()), LocalDateTime.now()));

        // Act
        ResultActions result = mockMvc.perform(
                post("/player/friends/pending-invites/" + friendInviteId + "?action=reject")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", PLAYER_ID.toString()))
                                .authorities(new SimpleGrantedAuthority("player")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        result.andExpect(status().isNoContent());
        verify(friendsRelationJpaRepository, never()).save(playerFriendCaptor.capture());
    }

    @Test
    void shouldReturnUnauthorizedWhenPlayerNotRightlyAuthenticated() throws Exception {
        // Arrange
        // Mock the invite entity
        FriendInviteJpaEntity inviteEntity = new FriendInviteJpaEntity(
                friendInviteId,
                new PlayerJpaEntity(INVITER_ID),
                new PlayerJpaEntity(UUID.randomUUID()),
                InviteStatus.PENDING,
                LocalDateTime.now()
        );

        // Mock the invite entity
        FriendInviteJpaEntity savedEntity = new FriendInviteJpaEntity(
                friendInviteId,
                new PlayerJpaEntity(INVITER_ID),
                new PlayerJpaEntity(UUID.randomUUID()),
                InviteStatus.ACCEPTED,
                LocalDateTime.now()
        );

        // Mocks the repository behavior for fetching the invite
        when(friendInviteJpaRepository.findById(friendInviteId)).thenReturn(Optional.of(inviteEntity));

        // Captures the entities being saved
        ArgumentCaptor<FriendInviteJpaEntity> saveCaptor = ArgumentCaptor.forClass(FriendInviteJpaEntity.class);
        ArgumentCaptor<FriendsRelationJpaEntity> playerFriendCaptor = ArgumentCaptor.forClass(FriendsRelationJpaEntity.class);

        // Mocks save methods
        when(friendInviteJpaRepository.save(saveCaptor.capture())).thenReturn(savedEntity);
        when(friendsRelationJpaRepository.save(playerFriendCaptor.capture())).thenReturn(new FriendsRelationJpaEntity(
                UUID.randomUUID(),
                new PlayerJpaEntity(savedEntity.getInvited().getId()),
                new PlayerJpaEntity(savedEntity.getInviter().getId()), LocalDateTime.now()));

        // Act
        ResultActions result = mockMvc.perform(
                post("/player/friends/pending-invites/" + friendInviteId + "?action=accept")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", PLAYER_ID.toString()))
                                .authorities(new SimpleGrantedAuthority("player")))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidActionProvided() throws Exception {
        // Arrange
        UUID playerId = UUID.randomUUID();
        UUID friendInviteId = UUID.randomUUID();

        // Act & Assert
        mockMvc.perform(
                post("/player/friends/pending-invites/" + friendInviteId + "?action=invalid")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", playerId.toString()))
                                .authorities(new SimpleGrantedAuthority("player")))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
