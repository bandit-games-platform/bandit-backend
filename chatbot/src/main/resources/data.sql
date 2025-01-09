
-- Seeding data for `game_details` table
INSERT INTO chatbot.game_details (game_id, title, description, summary) VALUES
('b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9', 'Button Game', 'A thrilling game consisting of two players and a magic button', 'Title: Magic Button Game\n   Description: Engage in an exciting two-player guessing game with the enigmatic magic button! Each player secretly clicks the button between 1 and 3 times. The goal is to make your friend guess the number of clicks - submit your guess when ready!\n\n   Brief Rules: Click 1-3 times, let friend guess, submit guess.'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Legacy Battleship', 'A strategic naval combat game', 'Title: Battleship\n\n   Description: A classic strategic naval combat game where players secretly arrange their ships on a grid and take turns attacking each other''s vessels by calling out coordinates. Win by sinking all of your opponent''s ships!\n\n   Rules (Shortened): Place ships, call coordinates to attack. Sink all ships to win.'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 'A classic strategy game of kings and queens', 'Title: Chess\n   Description: A timeless strategy game of royal maneuvering, where kings and queens vie for victory.\n\n   Rules: Players take turns moving one piece at a time to achieve checkmate or stalemate.'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 'A game of ups and downs', 'Title: Snakes and Ladders\n   Description: A delightful journey filled with unexpected twists of ups and downs.\n\n   Brief Rules: Roll the dice, move your token, climb ladders or slide down snakes based on grid position.\n   Compact Rules: Move tokens, climb ladders, slide down snakes, roll dice.'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c004', 'Checkers','Jump to capture and clear the board', 'Title: Checkers\n   Description: A classic game of strategy where pieces are captured by jumping over them, with the goal of clearing the entire board.\n\n   Rules Summary (Shortest version): Players alternate turns moving diagonal forward, capturing opponents by jumping over them; kings can also move backward.'),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c005', 'Monopoly',  'Real estate trading game of buying, selling, and bankrupting', null),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c006', 'Scrabble',  'Word game creating words with letter tiles', null),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c007', 'Catan',  'Resource game building settlements and earning points', null),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c008', 'Risk',  'Strategy game conquering territories for domination', null),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c009', 'Cluedo', 'Mystery game deducing murderer, weapon, and location', null),
('d77e1d1f-6b46-4c89-9290-3b9cf8a7c010', 'Race for the Galaxy',  'Card game building space civilizations and managing resources', null);


-- Seeding data for `rule` table
INSERT INTO chatbot.game_rule (uuid, step_number, rule, game_details) VALUES
-- Rules for ButtonGame
('c32e4567-e89b-12d3-a456-426614174016', 1, 'Click button between 1 and 3 times.', 'b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9'),
('c32e4567-e89b-12d3-a456-426614174017', 2, 'Make your friend guess how many times you pressed the button, dont forget to submit.', 'b6572f1b-cd36-3ebb-b292-6cfd0b92f8e9'),

-- Rules for Battleship
('c12e4567-e89b-12d3-a456-426614174010', 1, 'Place all your ships on the grid before starting the game.','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
('c12e4567-e89b-12d3-a456-426614174011', 2, 'Call out coordinates to attack enemy ships.','d77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),

-- Rules for Chess
('c22e4567-e89b-12d3-a456-426614174012', 1, 'Players alternate turns moving one piece at a time.','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),
('c22e4567-e89b-12d3-a456-426614174013', 2, 'The game ends in checkmate, stalemate, or resignation.','d77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Rules for Snakes and Ladders
('c32e4567-e89b-12d3-a456-426614174014', 1, 'Roll the dice to move your token.','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),
('c32e4567-e89b-12d3-a456-426614174015', 2, 'Climb ladders and slide down snakes based on the grid position.','d77e1d1f-6b46-4c89-9290-3b9cf8a7c003'),

-- Rules for Checkers
('22ed2db4-3f69-4066-a6c7-3d2fdf49c8d1', 1, 'Players take turns moving pieces diagonally forward. Kings can move backward too.', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c004'),
('22ed2db4-3f69-4066-a6c7-3d2fdf49c8d2', 2, 'Capture opponent pieces by jumping over them.', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c004');
