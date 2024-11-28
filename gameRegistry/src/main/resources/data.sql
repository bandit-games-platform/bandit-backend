
-- Insert developers
INSERT INTO game_registry.developer (id, studio_name) VALUES
('c5dd675d-0827-4221-9ccd-49180bf045f0', 'Integration Studio'),
('3c0b923c-c267-498e-88a4-eee3a629fea4', 'BanditGames');

-- give them a revoked and an active api key
INSERT INTO game_registry.dev_api_key(developer_id, api_key, revoked)
VALUES ('3c0b923c-c267-498e-88a4-eee3a629fea4', 'band1TAAA', true),
       ('3c0b923c-c267-498e-88a4-eee3a629fea4', 'band1TBBB', false);


-- Seeding data for `game` table
INSERT INTO game_registry.game (id, title, description, current_price, developer_id, current_host, icon_url, background_url) VALUES
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', 'A strategic naval combat game', 10.99, 'c5dd675d-0827-4221-9ccd-49180bf045f0', 'host1','https://cdn.discordapp.com/attachments/1306721570122633317/1307298001383063583/Screenshot_2024-11-16_at_11.55.25.png?ex=6739cba3&is=67387a23&hm=d9ef1e1247cc38dd8ddbd6005a24b9d1650e038ec9fc9920d85d6d11dc5654cd&','https://cdn.discordapp.com/attachments/1306721570122633317/1307300416031162439/Screenshot_2024-11-16_at_12.05.02.png?ex=6739cde3&is=67387c63&hm=50468e030edc646899d9707e362c7668953c69181ba8d72130b1ed025141c40d&'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 'A classic strategy game of kings and queens', 5.99, 'c5dd675d-0827-4221-9ccd-49180bf045f0', 'host2', 'https://cdn.discordapp.com/attachments/1306721570122633317/1307297507092725800/Screenshot_2024-11-16_at_11.53.25.png?ex=6739cb2e&is=673879ae&hm=70b916f6df0d5f17a6aca28e195f079710d94c32fee55437d3d4ccbf9ae5a533&','https://cdn.discordapp.com/attachments/1306721570122633317/1307299288115843082/Screenshot_2024-11-16_at_12.00.32.png?ex=6739ccd6&is=67387b56&hm=7a93a6d9c99646f34c4656bea3daddb7b35007e293be89abd6904cf716c4aaea&'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 'A game of ups and downs', 50.00, 'c5dd675d-0827-4221-9ccd-49180bf045f0', 'host3', 'https://cdn.discordapp.com/attachments/1306721570122633317/1307298303569952808/Screenshot_2024-11-16_at_11.56.37.png?ex=6739cbeb&is=67387a6b&hm=9e562d3a3a8b268c9957b480a0bc9399b000cba835251829e377644def69bd02&','https://img.freepik.com/premium-vector/snakes-ladders-board-game-vector_600323-522.jpg?w=1060');

INSERT INTO game_registry.game_screenshots (game_id, url) VALUES
-- Chess
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/man-is-playing-chess-phone_251474-239.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/chess-app-smartphone-real-chessboard-with-pieces-bench-park_984126-11004.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/playing-chess-digital-device-outdoor-mental-activity_63762-5364.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),

-- Battleship
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://img.freepik.com/premium-photo/retro-battleship-paper-game-as-battle-concept_681987-2783.jpg?w=826'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://cdn.discordapp.com/attachments/1306721570122633317/1307301634770210877/Screenshot_2024-11-16_at_12.09.46.png?ex=6739cf06&is=67387d86&hm=01283906f71c99eac55324664f1f06640880caa69e8bf69b535892d4504eb7dd&'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://bloob.io/img/meta/games/BATTLESHIP.png'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://cdn.discordapp.com/attachments/1306721570122633317/1307301893491523674/Screenshot_2024-11-16_at_12.10.53.png?ex=6739cf43&is=67387dc3&hm=51edc2787e862ef4cd3e37d92186a8d9a4255c72d97838e35d1c8fe7a23d964c&');


-- Seeding data for `achievement` table
INSERT INTO game_registry.achievement (id, title, counter_total, description, game_id) VALUES
-- Achievements for Battleship
('4964f6f7-5cee-3619-8caf-754d10078579', 'Fleet Commander', 5, 'Sink 5 enemy ships in one match','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
('28172f39-8abc-3499-819f-edc22a116468', 'Perfect Precision', 10, 'Hit 10 consecutive shots without a miss', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
('26640462-8af6-3c2d-ae2d-6a133ba87b3a', 'Ultimate Admiral', 20, 'Win 20 matches','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),

-- Achievements for Chess
('3b529ded-ee8a-3c1d-bd56-fdb04ba35abc', 'Grandmaster', 100, 'Win 100 matches','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
('3eaf18ca-2089-354f-81b4-6d0df2622207', 'Checkmate Pro', 50, 'Win 50 games by delivering checkmate within 20 moves','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
('0fbd54d3-386a-3fc6-9833-01a7f75ece13', 'Opening Mastery', 10, 'Master 10 chess openings','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Achievements for Snakes and Ladders
('d903303f-c6fb-36a3-b3a5-0f7ec221ab49', 'Ladder Climber', 20, 'Climb 20 ladders in a single game','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
('da135883-30f9-3211-95b4-4cb7f22df5cf', 'Snake Dodger', 15, 'Avoid 15 snakes in a single game','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
('9e2aabec-3b59-321d-8004-26a24e8e5202', 'Perfect Game', 1, 'Win a game without landing on any snake','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003');

-- Seeding data for `rule` table
INSERT INTO game_registry.game_rules (step_number, rule, game_id) VALUES
-- Rules for Battleship
(1, 'Place all your ships on the grid before starting the game','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
(2, 'Call out coordinates to attack enemy ships','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),

-- Rules for Chess
(1, 'Players alternate turns moving one piece at a time','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
(2, 'The game ends in checkmate, stalemate, or resignation','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Rules for Snakes and Ladders
(1, 'Roll the dice to move your token','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
(2, 'Climb ladders and slide down snakes based on the grid position','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003');
