CREATE TABLE pets (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    raca_id UUID NOT NULL,
    cliente_id UUID NOT NULL,
    data_nascimento TEXT NOT NULL,
    nome TEXT NOT NULL,
    FOREIGN KEY (raca_id) REFERENCES racas(id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);