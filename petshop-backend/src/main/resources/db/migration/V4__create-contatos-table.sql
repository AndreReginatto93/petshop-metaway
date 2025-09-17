CREATE TABLE contatos (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    clienteId TEXT NOT NULL,
    tag TEXT,
    tipo TEXT NOT NULL,
    valor TEXT NOT NULL,
    FOREIGN KEY (clienteId) REFERENCES clientes(id) ON DELETE CASCADE
);