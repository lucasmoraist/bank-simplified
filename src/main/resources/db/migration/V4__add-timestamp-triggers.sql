-- Criar a função para atualizar o campo updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Criar a trigger para a tabela t_user
CREATE TRIGGER set_timestamp_user
BEFORE UPDATE ON t_user
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Criar a trigger para a tabela t_wallet
CREATE TRIGGER set_timestamp_wallet
BEFORE UPDATE ON t_wallet
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Criar a trigger para a tabela t_transaction
CREATE TRIGGER set_timestamp_transaction
BEFORE UPDATE ON t_transaction
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
