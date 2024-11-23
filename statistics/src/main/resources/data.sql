INSERT INTO statistics.player_game_stats (player_id, game_id)
VALUES
    ('c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', 'b2e98f3b-3e2b-4a9e-8e5e-c3b4d0a4e401'),
    ('d7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', 'b3d12e4a-6a7b-412c-9e0e-e4d05c3f4b02');


INSERT INTO statistics.player_achievements_progress (achievement_progress_id, achievement_id, player_id, game_id, counter_total)
VALUES
    ('e3d12a4f-4b9f-8c0e-d05b-c3f4b7e1d01', 'a1f65dfc-2b4f-4e10-bd0f-33d1b05b9f01', 'c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', 'b2e98f3b-3e2b-4a9e-8e5e-c3b4d0a4e401', 20),
    ('f4d05b3b-9e0e-c7f1-b0e1-a1c5d0f7b302', 'a2e54abc-4d3f-45b8-8a5e-fd4d03b2d101', 'd7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', 'b3d12e4a-6a7b-412c-9e0e-e4d05c3f4b02', 15);



INSERT INTO statistics.completed_game_sessions (
    session_id, start_time, end_time, end_state, turns_taken, avg_seconds_per_turn,
    player_score, opponent_score, clicks, character, was_first_to_go, player_id, game_id
)
VALUES
    ('e1911462-023d-494b-ac70-f61e4ec194c1', '2024-11-01 10:00:00', '2024-11-01 10:30:00', 'WIN', 20, 15.5, 100, 90, 50, 'Knight', true, 'c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', 'b2e98f3b-3e2b-4a9e-8e5e-c3b4d0a4e401'),
    ('5fbe722a-b91c-4190-a413-a73b5a1d32e8', '2024-11-02 15:00:00', '2024-11-02 15:45:00', 'LOSE', 25, 18.3, 85, 95, 60, 'Mage', false, 'd7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', 'b3d12e4a-6a7b-412c-9e0e-e4d05c3f4b02');



INSERT INTO statistics.completed_game_sessions (
    session_id, start_time, end_time, end_state, turns_taken, avg_seconds_per_turn,
    player_score, opponent_score, clicks, character, was_first_to_go, player_id, game_id
)
VALUES
-- Session 1: Close match
('e4529160-dea4-41f3-83f9-7794be39493b', '2024-11-03 11:00:00', '2024-11-03 11:35:00', 'DRAW', 30, 12.0, 150, 150, 70, 'Archer', false, 'c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', 'b2e98f3b-3e2b-4a9e-8e5e-c3b4d0a4e401'),

-- Session 2: Dominant win
('7f4afdfd-1ef8-4af4-af4f-f5da15c6c1f9', '2024-11-04 13:30:00', '2024-11-04 14:00:00', 'WIN', 15, 10.5, 200, 50, 40, 'Warrior', true, 'd7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', 'b3d12e4a-6a7b-412c-9e0e-e4d05c3f4b02'),

-- Session 3: Epic battle, but a loss
('b3b5c74e-c9a7-409b-bec2-9d6ad71368c5', '2024-11-05 18:00:00', '2024-11-05 19:15:00', 'LOSE', 45, 16.2, 95, 130, 85, 'Rogue', false, 'c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', 'b2e98f3b-3e2b-4a9e-8e5e-c3b4d0a4e401'),

-- Session 4: Short session due to surrender
('39dde8d6-4052-482a-9e5d-3fc326fbb633', '2024-11-06 21:00:00', '2024-11-06 21:10:00', 'WIN', 5, 20.0, 50, 0, 10, 'Wizard', true, 'd7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', 'b3d12e4a-6a7b-412c-9e0e-e4d05c3f4b02');
