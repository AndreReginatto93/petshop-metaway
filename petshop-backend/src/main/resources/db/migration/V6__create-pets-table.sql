CREATE TABLE pets (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    raca_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    data_nascimento TIMESTAMP NOT NULL,
    nome TEXT NOT NULL,
    FOREIGN KEY (raca_id) REFERENCES racas(id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);