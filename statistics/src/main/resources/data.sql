INSERT INTO statistics.player_game_stats (player_id, game_id)
VALUES
    ('c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', 'b2e98f3b-3e2b-4a9e-8e5e-c3b4d0a4e401'),
    ('d7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', 'b3d12e4a-6a7b-412c-9e0e-e4d05c3f4b02'),
    ('94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb02');


INSERT INTO statistics.player_achievements_progress (achievement_progress_id, achievement_id, player_id, game_id, counter_total)
VALUES
    ('e3d12a4f-4b9f-8c0e-d05b-c3f4b7e1d01', 'a1f65dfc-2b4f-4e10-bd0f-33d1b05b9f01', 'c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', 'b2e98f3b-3e2b-4a9e-8e5e-c3b4d0a4e401', 20),
    ('f4d05b3b-9e0e-c7f1-b0e1-a1c5d0f7b302', 'a2e54abc-4d3f-45b8-8a5e-fd4d03b2d101', 'd7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', 'b3d12e4a-6a7b-412c-9e0e-e4d05c3f4b02', 15),
    ('f7bb7281-cbf9-4b60-a549-8e3aceacefe3', '123e4567-e89b-12d3-a456-426614174003', '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01', 10),
    ('bc7b11ff-27bf-45f4-99aa-7021e28b1133', '123e4567-e89b-12d3-a456-426614174002', '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01', 5);



INSERT INTO statistics.completed_game_sessions (
    session_id, start_time, end_time, end_state, turns_taken, avg_seconds_per_turn,
    player_score, opponent_score, clicks, character, was_first_to_go, player_id, game_id
)
VALUES
    ('e1911462-023d-494b-ac70-f61e4ec194c1', '2024-11-01 10:00:00', '2024-11-01 10:30:00', 'WIN', 20, 15.5, 100, 90, 50, 'Knight', true, 'c5b8f7a1-d34e-4e19-9d0f-b4c5d0e1f501', 'b2e98f3b-3e2b-4a9e-8e5e-c3b4d0a4e401'),
    ('5fbe722a-b91c-4190-a413-a73b5a1d32e8', '2024-11-02 15:00:00', '2024-11-02 15:45:00', 'LOSE', 25, 18.3, 85, 95, 60, 'Mage', false, 'd7f9b8e3-3c4f-4b9f-8d7e-e1c0f4a7b901', 'b3d12e4a-6a7b-412c-9e0e-e4d05c3f4b02'),
    ('5bf39cf8-3e23-42f8-9b6d-23f7d5dfe6bb', '2024-11-24 15:00:00', '2024-11-24 17:00:00', 'WIN', 50, 42, null, null, null, null, true, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('c6ec8502-7a19-419d-8234-b2af83e3bfc1', '2024-11-24 10:57:00', '2024-11-24 13:00:00', 'WIN', 19, 14, null, null, null, null, true, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('c3337a71-1caa-4605-b6fd-5e38dd9eb4be', '2024-11-20 14:34:00', '2024-11-20 17:00:00', 'LOSE', 75, 34, null, null, null, null, true, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('de162219-c595-4f71-9552-62147b4618d3', '2024-11-18 20:15:00', '2024-11-18 23:59:14', 'LOSE', 60, 47, null, null, null, null, false, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('9d8c0071-8a82-4324-bc83-a54eee8e4314', '2024-11-18 15:26:00', '2024-11-18 20:05:00', 'LOSE', 45, 65, null, null, null, null, true, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('126db3fa-b725-4333-b129-5457fe73e34c', '2024-11-15 19:16:00', '2024-11-15 23:00:00', 'WIN', 35, 17, null, null, null, null, false, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('6ba385bf-34f1-46d3-8654-4bd4d7a556ad', '2024-11-15 16:24:00', '2024-11-15 19:14:00', 'WIN', 38, 25, null, null, null, null, false, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('b707ef86-1ca2-4240-a502-84fff1a5303b', '2024-11-15 08:14:00', '2024-11-15 12:24:00', 'LOSE', 15, 42, null, null, null, null, true, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('51874c74-aeec-4c97-b52d-e3e767477f9d', '2024-11-11 09:00:34', '2024-11-11 16:00:00', 'WIN', 100, 150, null, null, null, null, true, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('e8aa06e0-4dc3-4aa1-bf57-91df33f31ed7', '2024-11-11 05:34:00', '2024-11-11 08:16:24', 'WIN', 48, 42, null, null, null, null, false, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb01'),
    ('878cd7a1-a275-4652-afa1-90817c748d33', '2024-11-24 19:00:00', '2024-11-24 21:24:27', 'WIN', 40, 66, null, null, null, 'White', true, '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'd7df9644-7803-4b70-8997-0f246665cb02');



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
