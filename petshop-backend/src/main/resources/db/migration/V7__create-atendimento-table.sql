CREATE TABLE atendimentos (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    pet_id TEXT NOT NULL,
    descricao TEXT NOT NULL,
    valor REAL NOT NULL,
    data_atendimento TEXT NOT NULL,
    FOREIGN KEY (pet_id) REFERENCES pets(id)
);