CREATE TABLE pets (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    racaId TEXT NOT NULL,
    clienteId TEXT NOT NULL,
    dataNascimento TEXT NOT NULL,
    nome TEXT NOT NULL,
    FOREIGN KEY (racaId) REFERENCES racas(id),
    FOREIGN KEY (clienteId) REFERENCES clientes(id)
);