INSERT INTO player.player (id, join_date, display_name)
VALUES ('d7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', '2024-09-10 11:30', 'jake'),
       ('c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', '2024-10-09 23:55', 'pete'),
       ('94dad160-f5c8-4817-8f2d-611e1436ffcd', '2024-08-08 06:35', 'roman');

INSERT INTO player.player_library (player_id)
VALUES ('94dad160-f5c8-4817-8f2d-611e1436ffcd');

INSERT INTO player.library_items (player_id, game_id, favourite, hidden)
VALUES ('94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', false, false),
       ('94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', false, false);
