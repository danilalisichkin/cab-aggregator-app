INSERT INTO balance_operations (payout_account_id, amount, type, transcript, created_at)
VALUES
    ('984b2c9d-8307-48ac-b5e3-e26d8fcb6c24', 2000, 'DEPOSIT',
     'Deposit transaction', '2024-12-01T12:00:00'),
    ('984b2c9d-8307-48ac-b5e3-e26d8fcb6c24', -1000, 'WITHDRAWAL',
     'Withdrawal transaction', '2024-11-01T12:00:00'),
    ('15253953-8087-4983-b2c9-f2a9c2af1f31', 100,'DEPOSIT',
     'Bonus sending transaction', '2024-03-01T12:00:00');
