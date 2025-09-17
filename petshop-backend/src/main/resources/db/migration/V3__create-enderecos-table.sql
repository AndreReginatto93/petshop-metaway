CREATE TABLE enderecos (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    cliente_id TEXT NOT NULL,
    logradouro TEXT NOT NULL,
    cidade TEXT NOT NULL,
    bairro TEXT NOT NULL,
    complemento TEXT,
    tag TEXT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);