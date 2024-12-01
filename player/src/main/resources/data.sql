INSERT INTO player.player (id, birthdate, avatar, display_name, gender, location)
VALUES
    ('8449ba7c-194c-4e51-b060-cd88cc498836', '1995-06-15', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'SmithRick', 'MALE', 'NIGERIA'),
    ('b1a2e3f4-5678-1234-5678-90abcdef1234', '1995-06-15', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'PlayerOne', 'MALE', 'USA'),
    ('c2b3f4e5-6789-2345-6789-01bcdef23456', '1998-09-20', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'GamerGirl', 'FEMALE', 'UNITED_KINGDOM'),
    ('d3c4f5e6-7890-3456-7890-12cdef345678', '2000-01-10', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'ProPlayer', 'OTHER', 'AUSTRALIA'),
    ('e4d5f6c7-8901-4567-8901-23def4567890', '1992-11-05', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'TopGamer', 'MALE', 'JAPAN'),
    ('f5e6d7c8-9012-5678-9012-34ef56789012', '1999-04-25', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'ElitePlayer', 'FEMALE', 'CANADA');


INSERT INTO player.player (id, birthdate, avatar, display_name, gender, location)
VALUES
    ('e47369ed-5cf2-48ec-a1f2-42cf7fa10836', '1985-03-22', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'Challenger', 'MALE', 'INDIA'),
    ('86da4cad-2dc1-4a43-981f-d900e8ad9dc7', '2002-07-15', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'Ines', 'FEMALE', 'GERMANY'),
    ('74053627-749b-4076-ba15-888444c09878', '1997-12-10', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'Dean', 'OTHER', 'BRAZIL'),
    ('c7ca1ac8-f9cf-43de-a82c-fb85f84348f2', '1993-02-18', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'Boldi', 'MALE', 'SOUTH_AFRICA'),
    ('4939923a-d359-45d1-ad39-9b36d9d23a0d', '2005-09-05', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'SkyBlazer', 'FEMALE', 'AUSTRALIA'),
    ('81c15287-4975-4def-815f-67c82718fd70', '1991-06-30', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'Roman', 'MALE', 'JAPAN'),
    ('8913596b-1968-4e06-9e62-7f4f89d33dde', '1998-01-23', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'Lightning', 'FEMALE', 'CANADA'),
    ('10dae503-0132-439b-86e9-b649a058cbe8', '1989-11-12', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHfd3PPulVSp4ZbuBFNkePoUR_fLJQe474Ag&s', 'Shadow', 'OTHER', 'EGYPT'),
    ('d7aef9fa-630d-4716-aa81-c0ef08e09370', '2000-03-08', '', 'IronFist', 'MALE', 'USA'),
    ('8067be3e-d09a-47db-8ed7-3bced86cba13', '1996-08-16', null, 'WindWalker', 'FEMALE', 'UNITED_KINGDOM');


INSERT INTO player.friends (id, player_id, friend_id)
VALUES
    ('bac812ea-8422-4dea-84db-ee09f62170fa', '8449ba7c-194c-4e51-b060-cd88cc498836', '86da4cad-2dc1-4a43-981f-d900e8ad9dc7'),
    ('45603958-1204-4960-86ab-8cb8bf7be683', '8449ba7c-194c-4e51-b060-cd88cc498836', '74053627-749b-4076-ba15-888444c09878'),
    ('11b6cd2c-25a5-460d-8b62-51c43de57744', '8449ba7c-194c-4e51-b060-cd88cc498836', 'c7ca1ac8-f9cf-43de-a82c-fb85f84348f2'),
    ('3cda5cb3-07e5-4973-9994-465b61c8a2f9', '8449ba7c-194c-4e51-b060-cd88cc498836', '81c15287-4975-4def-815f-67c82718fd70'),
    ('03fd91f3-217e-40ee-8dd2-2df90c66951b', '8449ba7c-194c-4e51-b060-cd88cc498836', '10dae503-0132-439b-86e9-b649a058cbe8');