CREATE TABLE atendimentos (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    petId TEXT NOT NULL,
    descricao TEXT NOT NULL,
    valor REAL NOT NULL,
    dataAtendimento TEXT NOT NULL,
    FOREIGN KEY (petId) REFERENCES pets(id)
);