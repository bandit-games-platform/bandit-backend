
INSERT INTO gameplay.game_developer (developer_id, game_id, game_title)
VALUES ('d7df9644-7803-4b70-8997-0f246665cb01','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship'),
       ('d7df9644-7803-4b70-8997-0f246665cb01','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess');

INSERT INTO gameplay.lobby (closed, current_player_count, max_players, game_id, id, owner_id)
VALUES (false, 1, 2, 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'ad12d056-05d1-44d7-9de6-3d896eb8bcd3', 'c7ca1ac8-f9cf-43de-a82c-fb85f84348f2'),
       (false, 3, 4, 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'd802ab36-5d8b-4393-9ed2-2bd063f27c01', '9f01b00e-e627-497c-975c-452451cc0b55'),
       (true, 1, 2, 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'a7809037-78a7-428d-ab78-16f30f41a6c6', '86da4cad-2dc1-4a43-981f-d900e8ad9dc7');

INSERT INTO gameplay.game_invite (accepted, id, invited, inviter, lobby_id)
VALUES (false, '1ff92235-07f7-4992-9851-21a32e998b4b', '9f01b00e-e627-497c-975c-452451cc0b55', 'c7ca1ac8-f9cf-43de-a82c-fb85f84348f2', 'ad12d056-05d1-44d7-9de6-3d896eb8bcd3'),
       (false, 'ef6ed5cc-772d-4647-8d16-4cc0a666ba8d', '9f01b00e-e627-497c-975c-452451cc0b55', '86da4cad-2dc1-4a43-981f-d900e8ad9dc7', 'd802ab36-5d8b-4393-9ed2-2bd063f27c01'),
       (true, '5a456874-ada0-42e8-90f5-0e0c9d7fc64d', '9f01b00e-e627-497c-975c-452451cc0b55', '86da4cad-2dc1-4a43-981f-d900e8ad9dc7', 'd802ab36-5d8b-4393-9ed2-2bd063f27c01'),
       (false, 'a4a72562-35ab-4002-87f4-198a5832d0e3', '9f01b00e-e627-497c-975c-452451cc0b55', '86da4cad-2dc1-4a43-981f-d900e8ad9dc7', 'a7809037-78a7-428d-ab78-16f30f41a6c6');
