INSERT INTO player.player (id, join_date, display_name, location, avatar)
VALUES ('272c3f85-79db-4b6f-a20e-5d1c12e0e5b3', '2024-09-10 11:30', 'Dean', 'BELGIUM', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s'),
       ('b20f4436-e61c-46f0-a83f-45f6a9cb45f9', '2024-10-09 23:55', 'Inês', 'PORTUGAL', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s'),
       ('cd281b11-e41e-4f12-93c8-1e9a8bf7403c', '2024-08-08 06:35', 'Roman', 'NEW_ZEALAND', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s'),
       ('6ca3863d-d8db-4588-b799-704fdb267814', '2024-08-08 06:35', 'Boldi', 'HUNGARY', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s'),
       ('d7d4c8ca-5c72-4728-9925-8e313ded5edc', '2024-12-02 02:07', 'Smith', 'NIGERIA', 'https://upload.wikimedia.org/wikipedia/commons/d/dc/RP-1_.jpg'),
       ('d7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', '2024-12-02 02:07', 'BoldInYourFace', 'HUNGARY', 'https://cdn-icons-png.flaticon.com/512/3393/3393852.png'),
       ('94dad160-f5c8-4817-8f2d-611e1436ffcd', '2024-12-02 02:07', 'GeekInês418', 'PORTUGAL', 'https://cdn-icons-png.flaticon.com/512/1985/1985783.png'),
       ('8449ba7c-194c-4e51-b060-cd88cc498836', '2024-12-02 02:07', 'DebtCollector', 'NIGERIA', 'https://cdn-icons-png.flaticon.com/512/6111/6111637.png');

INSERT INTO player.player_library (player_id)
VALUES ('cd281b11-e41e-4f12-93c8-1e9a8bf7403c'),
       ('6ca3863d-d8db-4588-b799-704fdb267814'),
       ('d7d4c8ca-5c72-4728-9925-8e313ded5edc'),
       ('272c3f85-79db-4b6f-a20e-5d1c12e0e5b3'),
       ('b20f4436-e61c-46f0-a83f-45f6a9cb45f9');

INSERT INTO player.library_items (player_id, game_id, favourite, hidden)
VALUES ('cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', false, false),
       ('cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', false, false),
       ('6ca3863d-d8db-4588-b799-704fdb267814', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', false, false),
       ('6ca3863d-d8db-4588-b799-704fdb267814', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', false, false),
       ('cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c004', false, false),
       ('d7d4c8ca-5c72-4728-9925-8e313ded5edc', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', true, false),
       ('d7d4c8ca-5c72-4728-9925-8e313ded5edc', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', false, false),
       ('d7d4c8ca-5c72-4728-9925-8e313ded5edc', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c003', true, false),
       ('272c3f85-79db-4b6f-a20e-5d1c12e0e5b3', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', false, false),
       ('b20f4436-e61c-46f0-a83f-45f6a9cb45f9', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', true, false);

INSERT INTO player.friends (id, player_a_id, player_b_id)
VALUES
    ('bac812ea-8422-4dea-84db-ee09f62170fa', '6ca3863d-d8db-4588-b799-704fdb267814', '272c3f85-79db-4b6f-a20e-5d1c12e0e5b3'),
    ('45603958-1204-4960-86ab-8cb8bf7be683', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9', '6ca3863d-d8db-4588-b799-704fdb267814'),
    ('3cda5cb3-07e5-4973-9994-465b61c8a2f9', '272c3f85-79db-4b6f-a20e-5d1c12e0e5b3', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9'),
    ('4cf27480-e416-4465-a156-e97e04d18ac7', 'd7d4c8ca-5c72-4728-9925-8e313ded5edc', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9'),
    ('bac812ea-8422-4dea-84db-ee09f62170fb', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', '6ca3863d-d8db-4588-b799-704fdb267814'),
    ('45603958-1204-4960-86ab-8cb8bf7be684', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9'),
    ('03fd91f3-217e-40ee-8dd2-2df90c66951c', '272c3f85-79db-4b6f-a20e-5d1c12e0e5b3', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c');

INSERT INTO player.friend_invite_status (time_of_invite, id, invited_id, inviter_id, status)
VALUES
    ('2024-12-02 10:30:00', 'fba3f186-8351-4cac-83d4-c1a58ae6cf81', '272c3f85-79db-4b6f-a20e-5d1c12e0e5b3', 'd7d4c8ca-5c72-4728-9925-8e313ded5edc', 'PENDING'),
    ('2024-12-02 09:30:00', 'fba3f186-8351-4cac-83d4-c1a58ae6cf83', 'd7d4c8ca-5c72-4728-9925-8e313ded5edc', '6ca3863d-d8db-4588-b799-704fdb267814', 'PENDING'),
    ('2024-12-02 09:30:00', 'fba3f186-8351-4cac-83d4-c1a58ae6cf84', 'd7d4c8ca-5c72-4728-9925-8e313ded5edc', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'PENDING'),
    ('2024-12-02 09:30:00', 'fba3f186-8351-4cac-83d4-c1a58ae6cf86', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'd7d4c8ca-5c72-4728-9925-8e313ded5edc', 'PENDING');
