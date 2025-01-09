
-- Insert developers
INSERT INTO game_registry.developer (id, studio_name) VALUES
('2c1784ca-edf0-4882-994d-23473e30f776', 'Integration Studio'),
('14e7d940-2b54-4cbe-93f9-9ec9f6be75ea', 'BanditGames'),
('f4e0eea7-7824-4d06-86e5-e553d2573a3b', 'deandev@kdg.be');

-- give them a revoked and an active api key
INSERT INTO game_registry.dev_api_key(developer_id, api_key, revoked) VALUES
('14e7d940-2b54-4cbe-93f9-9ec9f6be75ea', 'band1TAAA', true),
('14e7d940-2b54-4cbe-93f9-9ec9f6be75ea', 'band1TBBB', false),
('2c1784ca-edf0-4882-994d-23473e30f776', 'band1TCCC', false),
('f4e0eea7-7824-4d06-86e5-e553d2573a3b', 'band1T-button', false);

-- Seeding data for `game` table
INSERT INTO game_registry.game (id, title, description, current_price, developer_id, current_host, icon_url, background_url) VALUES
('b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9', 'Button Game', 'A thrilling game consisting of two players and a magic button', 12.99, '2c1784ca-edf0-4882-994d-23473e30f776', 'http://localhost:8094/val', 'https://upload.wikimedia.org/wikipedia/commons/5/5a/Perspective-Button-Stop-icon.png', 'https://t3.ftcdn.net/jpg/06/26/23/36/360_F_626233679_tesiSRP9Jinq5wS0ZgbdJ6k5adupmgKl.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Legacy Battleship', 'A strategic naval combat game', 10.99, '2c1784ca-edf0-4882-994d-23473e30f776', 'http://localhost:8301/battleship','https://play-lh.googleusercontent.com/WJp4hWdcmYu6hf-p-zd7tfv22P32f9G7HyFTY4mrnFoTS32Fa8-aDacjPFwwoY--DyE','https://wallpapers.com/images/hd/battleship-pictures-bb1dl2t2ofu085ee.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 'A classic strategy game of kings and queens', 5.99, '2c1784ca-edf0-4882-994d-23473e30f776', 'https://example.com', 'https://media.istockphoto.com/id/985550242/vector/business-strategy-with-chess-figures-on-a-chess-board.jpg?s=612x612&w=0&k=20&c=jYIITWu11EM_UblQy9PFjYEo4NrQvUm-OTHDqs1vtaA=','https://png.pngtree.com/background/20230525/original/pngtree-chess-playing-on-the-chess-board-with-other-pieces-in-view-picture-image_2725931.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 'A game of ups and downs', 50.00, '2c1784ca-edf0-4882-994d-23473e30f776', 'http://localhost:8094/sal', 'https://cdn0.iconfinder.com/data/icons/board-card-games-iconez/64/SnakesLadders-512.png','https://play-lh.googleusercontent.com/6Tu71iFYKiLN_egbg2qkYh1g6iFcnvJtoZrlgC2vBZjy4iuJlGEHWZTkPCIsOUmnWQ=w648-h364-rw'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c004', 'Checkers','Jump to capture and clear the board', 4.00, '2c1784ca-edf0-4882-994d-23473e30f776', 'http://localhost:8095/al', 'https://cdn-icons-png.flaticon.com/512/1707/1707234.png','https://img.freepik.com/premium-photo/checkers-game-checkerboard-wooden-background_220873-6795.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c005', 'Monopoly','Real estate trading game of buying, selling, and bankrupting',12.00, '2c1784ca-edf0-4882-994d-23473e30f776','http://localhost:8094/1','https://cdn-icons-png.flaticon.com/512/2367/2367224.png','https://t4.ftcdn.net/jpg/07/04/07/39/360_F_704073981_RycFLJVLJIhBI5mr8xUHKUeIgYdhdgUt.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c006', 'Scrabble','Word game creating words with letter tiles',6.00, '2c1784ca-edf0-4882-994d-23473e30f776','http://localhost:8094/2', 'https://cdn-icons-png.flaticon.com/512/7880/7880465.png', 'https://www.wsgamecompany.com/cdn/shop/files/21020_Scrabble_Luxury_Edition-05_4.jpg?v=1730911699'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c007', 'Catan','Resource game building settlements and earning points',25.00, '2c1784ca-edf0-4882-994d-23473e30f776','http://localhost:8094/3', 'https://img.tapimg.net/market/images/ecd547de1c1fa937dc54a5e338bed409.png/appicon?t=1','https://catancollector.com/images/catan-collection/electronic/Hintergrund_Das-Spiel_1920x1080.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c008', 'Risk','Strategy game conquering territories for domination',23.00, '2c1784ca-edf0-4882-994d-23473e30f776','http://localhost:8094/4','https://cdn-icons-png.flaticon.com/512/1732/1732476.png', 'https://res.cloudinary.com/jerrick/image/upload/v1611789143/6011f357fb8db7001cbd3012.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c009', 'Cluedo','Mystery game deducing murderer, weapon, and location',21.00, '2c1784ca-edf0-4882-994d-23473e30f776','http://localhost:8094/5', 'https://cdn-icons-png.flaticon.com/512/17305/17305219.png', 'https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/41526493168665.5f91637bd4c21.png'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c010', 'Race for the Galaxy','Card game building space civilizations and managing resources',17.00, '2c1784ca-edf0-4882-994d-23473e30f776','http://localhost:8094/6', 'https://cdn-icons-png.flaticon.com/512/3919/3919942.png','https://i0.wp.com/www.pixelatedcardboard.com/wp-content/uploads/2017/05/RFTG-Feature-1024x440.jpg?resize=961%2C413');

INSERT INTO game_registry.game_screenshots (game_id, url) VALUES
-- Chess
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/man-is-playing-chess-phone_251474-239.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/chess-app-smartphone-real-chessboard-with-pieces-bench-park_984126-11004.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'https://img.freepik.com/premium-photo/playing-chess-digital-device-outdoor-mental-activity_63762-5364.jpg?ga=GA1.1.1451404831.1728295284&semt=ais_hybrid'),

-- Battleship
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://img.freepik.com/premium-photo/retro-battleship-paper-game-as-battle-concept_681987-2783.jpg?w=826'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://wallpapercave.com/dwp1x/wp11552693.jpg'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://bloob.io/img/meta/games/BATTLESHIP.png'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'https://wallpapercave.com/dwp1x/wp11552694.jpg');


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
-- Rules for ButtonGame
(1, 'Click button between 1 and 3 times', 'b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9'),
(2, 'Make your friend guess how many times you pressed the button, dont forget to submit', 'b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9'),

-- Rules for Battleship
(1, 'Place all your ships on the grid before starting the game','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
(2, 'Call out coordinates to attack enemy ships','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),

-- Rules for Chess
(1, 'Players alternate turns moving one piece at a time','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
(2, 'The game ends in checkmate, stalemate, or resignation','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Rules for Snakes and Ladders
(1, 'Roll the dice to move your token','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
(2, 'Climb ladders and slide down snakes based on the grid position','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),

-- Rules for Checkers
(1, 'Players take turns moving pieces diagonally forward. Kings can move backward too.', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c004'),
(2, 'Capture opponent pieces by jumping over them.', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c004');
