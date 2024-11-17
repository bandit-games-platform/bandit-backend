
-- Insert images into table
INSERT INTO game_registry.image_resource( url) VALUES
('https://cdn.discordapp.com/attachments/1306721570122633317/1307298001383063583/Screenshot_2024-11-16_at_11.55.25.png?ex=6739cba3&is=67387a23&hm=d9ef1e1247cc38dd8ddbd6005a24b9d1650e038ec9fc9920d85d6d11dc5654cd&'),
('https://cdn.discordapp.com/attachments/1306721570122633317/1307300416031162439/Screenshot_2024-11-16_at_12.05.02.png?ex=6739cde3&is=67387c63&hm=50468e030edc646899d9707e362c7668953c69181ba8d72130b1ed025141c40d&'),
('https://cdn.discordapp.com/attachments/1306721570122633317/1307297507092725800/Screenshot_2024-11-16_at_11.53.25.png?ex=6739cb2e&is=673879ae&hm=70b916f6df0d5f17a6aca28e195f079710d94c32fee55437d3d4ccbf9ae5a533&'),
('https://cdn.discordapp.com/attachments/1306721570122633317/1307299288115843082/Screenshot_2024-11-16_at_12.00.32.png?ex=6739ccd6&is=67387b56&hm=7a93a6d9c99646f34c4656bea3daddb7b35007e293be89abd6904cf716c4aaea&'),
('https://cdn.discordapp.com/attachments/1306721570122633317/1307298303569952808/Screenshot_2024-11-16_at_11.56.37.png?ex=6739cbeb&is=67387a6b&hm=9e562d3a3a8b268c9957b480a0bc9399b000cba835251829e377644def69bd02&'),
('https://img.freepik.com/premium-vector/snakes-ladders-board-game-vector_600323-522.jpg?w=1060'),
('https://img.freepik.com/premium-photo/retro-battleship-paper-game-as-battle-concept_681987-2783.jpg?w=826'),
('https://cdn.discordapp.com/attachments/1306721570122633317/1307301634770210877/Screenshot_2024-11-16_at_12.09.46.png?ex=6739cf06&is=67387d86&hm=01283906f71c99eac55324664f1f06640880caa69e8bf69b535892d4504eb7dd&'),
('https://bloob.io/img/meta/games/BATTLESHIP.png'),
('https://cdn.discordapp.com/attachments/1306721570122633317/1307301893491523674/Screenshot_2024-11-16_at_12.10.53.png?ex=6739cf43&is=67387dc3&hm=51edc2787e862ef4cd3e37d92186a8d9a4255c72d97838e35d1c8fe7a23d964c&'),
('https://img.freepik.com/premium-photo/man-is-playing-chess-phone_251474-239.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),
('https://img.freepik.com/premium-photo/chess-app-smartphone-real-chessboard-with-pieces-bench-park_984126-11004.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),
('https://img.freepik.com/premium-photo/playing-chess-digital-device-outdoor-mental-activity_63762-5364.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid');


-- Seeding data for `game` table
INSERT INTO game_registry.game (game_id, title, description, current_host, icon_url, background_url,current_price) VALUES
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', 'A strategic naval combat game', 'host1','https://cdn.discordapp.com/attachments/1306721570122633317/1307298001383063583/Screenshot_2024-11-16_at_11.55.25.png?ex=6739cba3&is=67387a23&hm=d9ef1e1247cc38dd8ddbd6005a24b9d1650e038ec9fc9920d85d6d11dc5654cd&','https://cdn.discordapp.com/attachments/1306721570122633317/1307300416031162439/Screenshot_2024-11-16_at_12.05.02.png?ex=6739cde3&is=67387c63&hm=50468e030edc646899d9707e362c7668953c69181ba8d72130b1ed025141c40d&',35.50),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 'A classic strategy game of kings and queens', 'host2', 'https://cdn.discordapp.com/attachments/1306721570122633317/1307297507092725800/Screenshot_2024-11-16_at_11.53.25.png?ex=6739cb2e&is=673879ae&hm=70b916f6df0d5f17a6aca28e195f079710d94c32fee55437d3d4ccbf9ae5a533&','https://cdn.discordapp.com/attachments/1306721570122633317/1307299288115843082/Screenshot_2024-11-16_at_12.00.32.png?ex=6739ccd6&is=67387b56&hm=7a93a6d9c99646f34c4656bea3daddb7b35007e293be89abd6904cf716c4aaea&',9.99),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 'A game of ups and downs', 'host3','https://cdn.discordapp.com/attachments/1306721570122633317/1307298303569952808/Screenshot_2024-11-16_at_11.56.37.png?ex=6739cbeb&is=67387a6b&hm=9e562d3a3a8b268c9957b480a0bc9399b000cba835251829e377644def69bd02&','https://img.freepik.com/premium-vector/snakes-ladders-board-game-vector_600323-522.jpg?w=1060',20.99);

INSERT INTO game_registry.game_screenshot (id, game, screenshot_url) VALUES
-- Chess
('5712c581-bad7-49d5-86da-f35f21572474', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/man-is-playing-chess-phone_251474-239.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),
('d54098ad-5449-4609-9e2e-d8141ff96106', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/chess-app-smartphone-real-chessboard-with-pieces-bench-park_984126-11004.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),
('6aaec734-989e-4c2c-8fef-14563496a525', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/playing-chess-digital-device-outdoor-mental-activity_63762-5364.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),

-- Battleship
('756ea026-631c-4504-84c6-39bfb3e63ddf', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://img.freepik.com/premium-photo/retro-battleship-paper-game-as-battle-concept_681987-2783.jpg?w=826'),
('7ba0c631-6a45-4227-b4f8-455d7b2ab484', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://cdn.discordapp.com/attachments/1306721570122633317/1307301634770210877/Screenshot_2024-11-16_at_12.09.46.png?ex=6739cf06&is=67387d86&hm=01283906f71c99eac55324664f1f06640880caa69e8bf69b535892d4504eb7dd&'),
('b6c4ad65-d1c2-4c09-87f8-6c4e85763640', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://bloob.io/img/meta/games/BATTLESHIP.png'),
('4e6f6280-90db-486e-8a7d-c02acfbaeb90', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://cdn.discordapp.com/attachments/1306721570122633317/1307301893491523674/Screenshot_2024-11-16_at_12.10.53.png?ex=6739cf43&is=67387dc3&hm=51edc2787e862ef4cd3e37d92186a8d9a4255c72d97838e35d1c8fe7a23d964c&');


-- Seeding data for `achievement` table
INSERT INTO game_registry.achievement (id, title, counter_total, description, game) VALUES
-- Achievements for Battleship
('123e4567-e89b-12d3-a456-426614174001', 'Fleet Commander', 5, 'Sink 5 enemy ships in one match','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
('123e4567-e89b-12d3-a456-426614174002', 'Perfect Precision', 10, 'Hit 10 consecutive shots without a miss', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
('123e4567-e89b-12d3-a456-426614174003', 'Ultimate Admiral', 20, 'Win 20 matches','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),

-- Achievements for Chess
('223e4567-e89b-12d3-a456-426614174004', 'Grandmaster', 100, 'Win 100 matches','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
('223e4567-e89b-12d3-a456-426614174005', 'Checkmate Pro', 50, 'Win 50 games by delivering checkmate within 20 moves','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
('223e4567-e89b-12d3-a456-426614174006', 'Opening Mastery', 10, 'Master 10 chess openings','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Achievements for Snakes and Ladders
('323e4567-e89b-12d3-a456-426614174007', 'Ladder Climber', 20, 'Climb 20 ladders in a single game','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
('323e4567-e89b-12d3-a456-426614174008', 'Snake Dodger', 15, 'Avoid 15 snakes in a single game','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
('323e4567-e89b-12d3-a456-426614174009', 'Perfect Game', 1, 'Win a game without landing on any snake','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003');

-- Seeding data for `rule` table
INSERT INTO game_registry.rule (uuid, step_number, rule, game) VALUES
-- Rules for Battleship
('c12e4567-e89b-12d3-a456-426614174010', 1, 'Place all your ships on the grid before starting the game','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
('c12e4567-e89b-12d3-a456-426614174011', 2, 'Call out coordinates to attack enemy ships','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),

-- Rules for Chess
('c22e4567-e89b-12d3-a456-426614174012', 1, 'Players alternate turns moving one piece at a time','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
('c22e4567-e89b-12d3-a456-426614174013', 2, 'The game ends in checkmate, stalemate, or resignation','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Rules for Snakes and Ladders
('c32e4567-e89b-12d3-a456-426614174014', 1, 'Roll the dice to move your token','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
('c32e4567-e89b-12d3-a456-426614174015', 2, 'Climb ladders and slide down snakes based on the grid position','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003');
