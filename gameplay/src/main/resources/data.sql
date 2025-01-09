
INSERT INTO gameplay.game_developer (developer_id, game_id, game_title)
VALUES ('2c1784ca-edf0-4882-994d-23473e30f776','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Legacy Battleship'),
       ('2c1784ca-edf0-4882-994d-23473e30f776','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess');

INSERT INTO gameplay.lobby (closed, current_player_count, max_players, game_id, id, owner_id)
VALUES (false, 1, 2, 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'ad12d056-05d1-44d7-9de6-3d896eb8bcd3', 'c7ca1ac8-f9cf-43de-a82c-fb85f84348f2'),
       (false, 3, 4, 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'd802ab36-5d8b-4393-9ed2-2bd063f27c01', '9f01b00e-e627-497c-975c-452451cc0b55'),
       (true, 1, 2, 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'a7809037-78a7-428d-ab78-16f30f41a6c6', '86da4cad-2dc1-4a43-981f-d900e8ad9dc7');

INSERT INTO gameplay.game_invite (accepted, id, invited, inviter, lobby_id)
VALUES (false, '1ff92235-07f7-4992-9851-21a32e998b4b', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', '6ca3863d-d8db-4588-b799-704fdb267814', 'ad12d056-05d1-44d7-9de6-3d896eb8bcd3'),
       (false, 'ef6ed5cc-772d-4647-8d16-4cc0a666ba8d', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9', 'd802ab36-5d8b-4393-9ed2-2bd063f27c01'),
       (true, '5a456874-ada0-42e8-90f5-0e0c9d7fc64d', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9', 'd802ab36-5d8b-4393-9ed2-2bd063f27c01'),
       (false, 'a4a72562-35ab-4002-87f4-198a5832d0e3', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9', 'a7809037-78a7-428d-ab78-16f30f41a6c6');
