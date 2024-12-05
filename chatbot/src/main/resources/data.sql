
-- Seeding data for `game_details` table
INSERT INTO chatbot.game_details (game_id, title, description) VALUES
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', 'A strategic naval combat game'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 'A classic strategy game of kings and queens'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 'A game of ups and downs');

-- Seeding data for `rule` table
INSERT INTO chatbot.game_rule (uuid, step_number, rule, game_details) VALUES
-- Rules for Battleship
('c12e4567-e89b-12d3-a456-426614174010', 1, 'Place all your ships on the grid before starting the game','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
('c12e4567-e89b-12d3-a456-426614174011', 2, 'Call out coordinates to attack enemy ships','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),

-- Rules for Chess
('c22e4567-e89b-12d3-a456-426614174012', 1, 'Players alternate turns moving one piece at a time','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
('c22e4567-e89b-12d3-a456-426614174013', 2, 'The game ends in checkmate, stalemate, or resignation','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Rules for Snakes and Ladders
('c32e4567-e89b-12d3-a456-426614174014', 1, 'Roll the dice to move your token','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
('c32e4567-e89b-12d3-a456-426614174015', 2, 'Climb ladders and slide down snakes based on the grid position','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003');
