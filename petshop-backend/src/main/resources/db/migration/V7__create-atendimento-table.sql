CREATE TABLE atendimentos (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    pet_id UUID NOT NULL,
    descricao TEXT NOT NULL,
    valor REAL NOT NULL,
    data_atendimento TEXT NOT NULL,
    FOREIGN KEY (pet_id) REFERENCES pets(id)
);