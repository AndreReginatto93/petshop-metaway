CREATE TABLE enderecos (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    cliente_id UUID NOT NULL,
    logradouro TEXT NOT NULL,
    cidade TEXT NOT NULL,
    bairro TEXT NOT NULL,
    complemento TEXT,
    tag TEXT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);