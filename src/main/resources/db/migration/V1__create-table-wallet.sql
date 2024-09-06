-- Criar a extensão UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criar a tabela t_wallet
CREATE TABLE t_wallet (
    id BIGSERIAL PRIMARY KEY,
    balance NUMERIC(15, 2) NOT NULL DEFAULT 0
);