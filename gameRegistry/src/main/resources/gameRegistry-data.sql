

-- Seeding data for `game` table
INSERT INTO game_registry.game (game_id, title, description, current_host) VALUES
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', 'A strategic naval combat game', 'host1'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 'A classic strategy game of kings and queens', 'host2'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 'A game of ups and downs', 'host3');

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
