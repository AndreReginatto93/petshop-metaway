CREATE TABLE contatos (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    cliente_id TEXT NOT NULL,
    tag TEXT,
    tipo TEXT NOT NULL,
    valor TEXT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);