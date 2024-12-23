INSERT INTO payment_contexts (id, payment_id, type, context_id)
VALUES
    (1, 'pi_1JXXXXXXYYYYYY', 'RIDE', '60c72b2f9b1e8c001c8d5a3f'),
    (2, 'pi_1JZZZZZZWWWWWW', 'RIDE', '64b1f2c1d23e5a6f7b89c012');

ALTER SEQUENCE payment_contexts_id_seq RESTART WITH 3;
