
-- Insert developers
INSERT INTO game_registry.developer (id, studio_name) VALUES
('d7df9644-7803-4b70-8997-0f246665cb01', 'Integration Studio'),
('3c0b923c-c267-498e-88a4-eee3a629fea4', 'BanditGames');

-- give them a revoked and an active api key
INSERT INTO game_registry.dev_api_key(developer_id, api_key, revoked)
VALUES ('3c0b923c-c267-498e-88a4-eee3a629fea4', 'band1TAAA', true),
       ('3c0b923c-c267-498e-88a4-eee3a629fea4', 'band1TBBB', false),
       ('d7df9644-7803-4b70-8997-0f246665cb01', 'band1TCCC', false);


-- Seeding data for `game` table
INSERT INTO game_registry.game (id, title, description, current_price, developer_id, current_host, icon_url, background_url) VALUES
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', 'A strategic naval combat game', 10.99, 'd7df9644-7803-4b70-8997-0f246665cb01', 'http://localhost:8301/battleship','https://play-lh.googleusercontent.com/WJp4hWdcmYu6hf-p-zd7tfv22P32f9G7HyFTY4mrnFoTS32Fa8-aDacjPFwwoY--DyE','https://wallpapers.com/images/hd/battleship-pictures-bb1dl2t2ofu085ee.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 'A classic strategy game of kings and queens', 5.99, 'd7df9644-7803-4b70-8997-0f246665cb01', 'https://example.com', 'https://media.istockphoto.com/id/985550242/vector/business-strategy-with-chess-figures-on-a-chess-board.jpg?s=612x612&w=0&k=20&c=jYIITWu11EM_UblQy9PFjYEo4NrQvUm-OTHDqs1vtaA=','https://png.pngtree.com/background/20230525/original/pngtree-chess-playing-on-the-chess-board-with-other-pieces-in-view-picture-image_2725931.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 'A game of ups and downs', 50.00, 'd7df9644-7803-4b70-8997-0f246665cb01', 'http://localhost:8094/sal', 'https://images.dwncdn.net/images/t_app-icon-l/p/bd8338f9-339d-4026-aa6c-ef432c608103/2947135626/18516_4-78350587-logo','https://c8.alamy.com/comp/AMGKMX/snakes-and-ladders-board-game-with-counter-sliding-down-snake-AMGKMX.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c004', 'Checkers', 'Jump to capture and clear the board', 4.00, 'd7df9644-7803-4b70-8997-0f246665cb01', 'http://localhost:8095/al', 'https://cdn-icons-png.flaticon.com/512/1707/1707234.png','https://img.freepik.com/premium-photo/checkers-game-checkerboard-wooden-background_220873-6795.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c005', 'Tic-Tac-Toe', 'Simple three-in-a-row game', 5.00, 'd7df9644-7803-4b70-8997-0f246665cb01', 'http://localhost:8096/bal', 'https://cdn-icons-png.flaticon.com/512/806/806131.png','https://img.freepik.com/premium-vector/tic-tac-toe-game-linear-outline-icon-colour-neon-style-brick-wall-background-light-icon_549897-1770.jpg?semt=ais_hybrid'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c006', 'Lost Cities', 'Build card expeditions for maximum points', 6.00, 'd7df9644-7803-4b70-8997-0f246665cb01', 'http://localhost:8097/cal', 'https://m.media-amazon.com/images/S/aplus-media/vc/304326f4-3781-47a1-bb18-7aeb77ea8be7._CR0,0,300,300_PT0_SX300__.jpg','https://b1803394.smushcdn.com/1803394/wp-content/uploads/2017/05/lost-cities-review-header-990x557.jpg?lossy=1&strip=1&webp=1'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c007', 'Santorini', 'Build towers and outmaneuver your opponent', 7.00, 'd7df9644-7803-4b70-8997-0f246665cb01', 'http://localhost:8098/dal', 'https://play-lh.googleusercontent.com/wJfGzmEfSH3JjfY_lHRYEBEkxw4oVUS7gOgdzxCYLL0XuUDuExVUliKdT46H_scv0Wc=w240-h480-rw','https://c4.wallpaperflare.com/wallpaper/468/139/739/board-games-santorini-wallpaper-preview.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c008', 'Patchwork', 'Create a quilt in this tile-laying game', 8.00, 'd7df9644-7803-4b70-8997-0f246665cb01', 'http://localhost:8099/fal', 'https://m.media-amazon.com/images/I/61MF-0Duh+L._SL300_.jpg','https://store-images.s-microsoft.com/image/apps.33573.13510798886911914.a34f97b7-4e2e-4c05-a9b4-dd95e8c0eb22.cbfc1ce8-b404-480f-a0ad-5b4556cc54e3?mode=scale&q=90&h=400&w=800&background=%23000000');

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
