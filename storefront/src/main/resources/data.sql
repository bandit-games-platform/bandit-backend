INSERT INTO storefront.order (id, customer_id, stripe_session_id, product_id, order_date, order_status, order_completed_at)
VALUES ('d0eac6e4-1c21-4666-aab4-1350d9bcf000', 'b20f4436-e61c-46f0-a83f-45f6a9cb45f9', 'cs_test_a1LbH5h7KVB9uTqjvP01TKmI7Iiuc8Mfw0PEU1HMcjNhDUhzLCanurgpqL', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', '2024-11-10 15:28', 'COMPLETED', '2024-11-10 15:34'),
       ('8e7d114f-2433-4d95-9c5b-31c19e6bf659', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'cs_test_a1oNqoIPoBVjyQ4jckhb19cQsmCGgEBBeKwQ7nzggkt0XWIJAxMf2HFU0w', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', '2024-11-24 14:30', 'COMPLETED', '2024-11-24 14:39'),
       ('595ded13-ddc6-41e6-879a-589501e02016', 'cd281b11-e41e-4f12-93c8-1e9a8bf7403c', 'some_invalid_session', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c003', '2024-11-28 14:30', 'PENDING', null);

INSERT INTO storefront.product (id, title, developer_id, description, price, background_url)
VALUES ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', '2c1784ca-edf0-4882-994d-23473e30f776', 'A strategic naval combat game', 10.99, 'https://wallpapers.com/images/hd/battleship-pictures-bb1dl2t2ofu085ee.jpg'),
       ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', '2c1784ca-edf0-4882-994d-23473e30f776', 'A classic strategy game of kings and queens', 5.99, 'https://png.pngtree.com/background/20230525/original/pngtree-chess-playing-on-the-chess-board-with-other-pieces-in-view-picture-image_2725931.jpg'),
       ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', '2c1784ca-edf0-4882-994d-23473e30f776', 'A game of ups and downs',50.00, 'https://c8.alamy.com/comp/AMGKMX/snakes-and-ladders-board-game-with-counter-sliding-down-snake-AMGKMX.jpg'),
       ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c004', 'Checkers', '2c1784ca-edf0-4882-994d-23473e30f776', 'Jump to capture and clear the board',4.00, 'https://img.freepik.com/premium-photo/checkers-game-checkerboard-wooden-background_220873-6795.jpg');
