CREATE TABLE contatos (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    cliente_id UUID NOT NULL,
    tag TEXT,
    tipo TEXT NOT NULL,
    valor TEXT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
);