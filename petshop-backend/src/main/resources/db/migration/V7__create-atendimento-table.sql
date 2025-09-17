CREATE TABLE atendimentos (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pet_id BIGINT NOT NULL,
    descricao TEXT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data_atendimento TIMESTAMP NOT NULL,
    FOREIGN KEY (pet_id) REFERENCES pets(id)
);