-- Criar a tabela t_transaction
CREATE TABLE t_transaction (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    payer_id BIGINT,
    payee_id BIGINT,
    amount NUMERIC(15, 2),
    status VARCHAR(50),
    authorization_code VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payer
        FOREIGN KEY (payer_id) REFERENCES t_user(id),
    CONSTRAINT fk_payee
        FOREIGN KEY (payee_id) REFERENCES t_user(id)
);

-- Criar índices para as colunas de referência
CREATE INDEX idx_transaction_payer ON t_transaction(payer_id);
CREATE INDEX idx_transaction_payee ON t_transaction(payee_id);
