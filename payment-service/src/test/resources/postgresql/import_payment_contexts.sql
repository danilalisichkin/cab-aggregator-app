INSERT INTO payment_contexts (id, payment_id, type, context_id)
VALUES
    (1, 'pi_1JXXXXXXYYYYYY', 'RIDE', '507f1f77bcf86cd799439011'),
    (2, 'pi_1JZZZZZZWWWWWW', 'RIDE', '30c8c7f7ece3c4a6c6c4e861');

ALTER SEQUENCE payment_contexts_id_seq RESTART WITH 3;
