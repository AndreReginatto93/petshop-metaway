CREATE TABLE pets (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    raca_id TEXT NOT NULL,
    cliente_id TEXT NOT NULL,
    data_nascimento TEXT NOT NULL,
    nome TEXT NOT NULL,
    FOREIGN KEY (raca_id) REFERENCES racas(id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);