-- Criar a tabela t_user
CREATE TABLE t_user (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(800) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    wallet_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wallet
        FOREIGN KEY (wallet_id) REFERENCES t_wallet(id)
);