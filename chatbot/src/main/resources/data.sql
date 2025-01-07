
-- Seeding data for `game_details` table
INSERT INTO chatbot.game_details (game_id, title, description) VALUES
('b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9', 'Button Game', 'A thrilling game consisting of two players and a magic button'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', 'A strategic naval combat game'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 'A classic strategy game of kings and queens'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 'A game of ups and downs'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c004', 'Checkers','Jump to capture and clear the board'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c005', 'Monopoly',  'Real estate trading game of buying, selling, and bankrupting'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c006', 'Scrabble',  'Word game creating words with letter tiles'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c007', 'Catan',  'Resource game building settlements and earning points'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c008', 'Risk',  'Strategy game conquering territories for domination'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c009', 'Cluedo', 'Mystery game deducing murderer, weapon, and location'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c010', 'Race for the Galaxy',  'Card game building space civilizations and managing resources');


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
('c32e4567-e89b-12d3-a456-426614174015', 2, 'Climb ladders and slide down snakes based on the grid position','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),

('c32e4567-e89b-12d3-a456-426614174016', 1, 'Click button between 1 and 3 times', 'b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9'),
('c32e4567-e89b-12d3-a456-426614174017', 2, 'Make your friend guess how many times you pressed the button, dont forget to submit', 'b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9');