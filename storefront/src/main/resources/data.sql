INSERT INTO storefront.order (id, customer_id, stripe_session_id, product_id, order_date, order_status, order_completed_at)
VALUES ('d0eac6e4-1c21-4666-aab4-1350d9bcf000', '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'cs_test_a1LbH5h7KVB9uTqjvP01TKmI7Iiuc8Mfw0PEU1HMcjNhDUhzLCanurgpqL', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c001', '2024-11-10 15:28', 'COMPLETED', '2024-11-10 15:34'),
       ('8e7d114f-2433-4d95-9c5b-31c19e6bf659', '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'cs_test_a1oNqoIPoBVjyQ4jckhb19cQsmCGgEBBeKwQ7nzggkt0XWIJAxMf2HFU0w', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c002', '2024-11-24 14:30', 'COMPLETED', '2024-11-24 14:39'),
       ('595ded13-ddc6-41e6-879a-589501e02016', '94dad160-f5c8-4817-8f2d-611e1436ffcd', 'some_invalid_session', 'd77e1d1f-6b46-4c89-9290-3b9cf8a7c003', '2024-11-28 14:30', 'PENDING', null);

INSERT INTO storefront.product (id, title, price)
VALUES ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c001', 'Battleship', 10.99),
       ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c002', 'Chess', 5.99),
       ('d77e1d1f-6b46-4c89-9290-3b9cf8a7c003', 'Snakes and Ladders', 50.00);
