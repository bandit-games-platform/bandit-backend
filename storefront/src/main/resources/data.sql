INSERT INTO storefront.order (id, customer_id, stripe_session_id, product_id, order_date, order_status, order_completed_at)
VALUES ('d0eac6e4-1c21-4666-aab4-1350d9bcf000', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'cs_test_a1LbH5h7KVB9uTqjvP01TKmI7Iiuc8Mfw0PEU1HMcjNhDUhzLCanurgpqL', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', '2024-11-10 15:28', 'COMPLETED', '2024-11-10 15:34'),
       ('8e7d114f-2433-4d95-9c5b-31c19e6bf659', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'cs_test_a1oNqoIPoBVjyQ4jckhb19cQsmCGgEBBeKwQ7nzggkt0XWIJAxMf2HFU0w', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', '2024-11-24 14:30', 'COMPLETED', '2024-11-24 14:39'),
       ('595ded13-ddc6-41e6-879a-589501e02016', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'some_invalid_session1', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c003', '2024-11-28 14:30', 'PENDING', null),
       ('9649d041-eeb6-4f50-bf73-fd443c7282b6', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9', 'some_invalid_session2', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', '2024-11-24 14:30', 'COMPLETED', '2024-11-24 14:32'),
       ('0e88c968-a8b6-4df0-9005-4dd7521754df', '272c3f85-79db-4b6f-a20e-5d1c12e0e5b3', 'some_invalid_session3', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', '2024-11-25 14:30', 'COMPLETED', '2024-11-25 14:32'),
       ('3d94e850-e76e-4bf0-8753-8aad360a173b', 'd7d4c8ca-5c72-4728-9925-8e313ded5edc', 'some_invalid_session4', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', '2024-11-26 14:30', 'COMPLETED', '2024-11-26 14:32'),
       ('66460247-edb2-4d96-8102-5e9c1b3d09aa', '6ca3863d-d8db-4588-b799-704fdb267814', 'some_invalid_session5', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', '2024-11-27 14:30', 'COMPLETED', '2024-11-27 14:32'),
       ('c5bef817-d013-4471-a739-067f4fded175', '272c3f85-79db-4b6f-a20e-5d1c12e0e5b3', 'some_invalid_session6', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', '2024-12-23 14:30', 'COMPLETED', '2024-12-23 14:32'),
       ('e2a20bcc-ecfd-4141-ace1-109e08ae2bc3', 'd7d4c8ca-5c72-4728-9925-8e313ded5edc', 'some_invalid_session7', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', '2024-12-22 14:30', 'COMPLETED', '2024-12-22 14:32'),
       ('c77ecfce-9623-4dee-a571-2a0d543e3d48', '272c3f85-79db-4b6f-a20e-5d1c12e0e5b3', 'some_invalid_session8', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', '2024-12-21 14:30', 'COMPLETED', '2024-12-21 14:32'),
       ('313ef954-0298-4056-a24a-b1281bb61285', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'some_invalid_session9', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c003', '2024-12-20 14:30', 'COMPLETED', '2024-12-20 14:32');

INSERT INTO storefront.product (id, title, developer_id, description, price, background_url)
VALUES ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', '2c1784ca-edf0-4882-994d-23473e30f776', 'A strategic naval combat game', 10.99, 'https://wallpapers.com/images/hd/battleship-pictures-bb1dl2t2ofu085ee.jpg'),
       ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', '2c1784ca-edf0-4882-994d-23473e30f776', 'A classic strategy game of kings and queens', 5.99, 'https://png.pngtree.com/background/20230525/original/pngtree-chess-playing-on-the-chess-board-with-other-pieces-in-view-picture-image_2725931.jpg'),
       ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', '2c1784ca-edf0-4882-994d-23473e30f776', 'A game of ups and downs',50.00, 'https://c8.alamy.com/comp/AMGKMX/snakes-and-ladders-board-game-with-counter-sliding-down-snake-AMGKMX.jpg'),
       ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c004', 'Checkers', '2c1784ca-edf0-4882-994d-23473e30f776', 'Jump to capture and clear the board',4.00, 'https://img.freepik.com/premium-photo/checkers-game-checkerboard-wooden-background_220873-6795.jpg');
