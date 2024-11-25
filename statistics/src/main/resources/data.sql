INSERT INTO statistics.player_game_stats (player_id, game_id)
VALUES
    ('8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
    ('8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002');


INSERT INTO statistics.player_achievements_progress (achievement_progress_id, achievement_id, player_id, game_id, counter_total)
VALUES
    ('a91baf13-3965-45ff-9ee5-13be8f105f23', '123e4567-e89b-12d3-a456-426614174001', '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 2),
    ('29242f25-1438-49a2-84ca-1cbd285fba30', '123e4567-e89b-12d3-a456-426614174002', '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 4),
    ('c63704ae-f6bc-4a26-bfd3-3ab890bcd159', '223e4567-e89b-12d3-a456-426614174004', '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 25);



INSERT INTO statistics.completed_game_sessions (
    session_id, start_time, end_time, end_state, turns_taken, avg_seconds_per_turn,
    player_score, opponent_score, clicks, character, was_first_to_go, player_id, game_id
)
VALUES
    ('e1911462-023d-494b-ac70-f61e4ec194c1', '2024-11-01 10:00:00', '2024-11-01 10:30:00', 'WIN', 20, 15.5, 100, 90, 50, 'Knight', true, '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),
    ('5fbe722a-b91c-4190-a413-a73b5a1d32e8', '2024-11-02 15:00:00', '2024-11-02 15:45:00', 'LOSS', 25, 18.3, 85, 95, 60, 'Mage', false, '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002');



INSERT INTO statistics.completed_game_sessions (
    session_id, start_time, end_time, end_state, turns_taken, avg_seconds_per_turn,
    player_score, opponent_score, clicks, character, was_first_to_go, player_id, game_id
)
VALUES
-- Session 1: Close match
('e4529160-dea4-41f3-83f9-7794be39493b', '2024-11-03 11:00:00', '2024-11-03 11:35:00', 'DRAW', 30, 12.0, 150, 150, 70, 'Archer', false, '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Session 2: Dominant win
('7f4afdfd-1ef8-4af4-af4f-f5da15c6c1f9', '2024-11-04 13:30:00', '2024-11-04 14:00:00', 'WIN', 15, 10.5, 200, 50, 40, 'Warrior', true, '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001'),

-- Session 3: Epic battle, but a loss
('b3b5c74e-c9a7-409b-bec2-9d6ad71368c5', '2024-11-05 18:00:00', '2024-11-05 19:15:00', 'LOSS', 45, 16.2, 95, 130, 85, 'Rogue', false, '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002'),

-- Session 4: Short session due to surrender
('39dde8d6-4052-482a-9e5d-3fc326fbb633', '2024-11-06 21:00:00', '2024-11-06 21:10:00', 'WIN', 5, 20.0, 50, 0, 10, 'Wizard', true, '8449ba7c-194c-4e51-b060-cd88cc498836', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001');
