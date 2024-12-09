-- Crear base de datos
CREATE DATABASE dotapicker_matchups;
USE dotapicker_matchups;

-- Tabla: Heroes
CREATE TABLE Heroes (
    heroe_id INT PRIMARY KEY, -- ID del héroe proporcionado por OpenDota
    nombre VARCHAR(255) NOT NULL -- Nombre del héroe (localized_name)
);

-- Tabla: Matchups
CREATE TABLE Matchups (
    matchup_id INT PRIMARY KEY AUTO_INCREMENT,
    heroe_id INT NOT NULL, -- ID del héroe principal
    enemigo_id INT NOT NULL, -- ID del héroe enemigo
    games_played INT NOT NULL, -- Número de partidas jugadas entre los héroes
    wins INT NOT NULL, -- Número de victorias del héroe principal
    winrate FLOAT AS (wins * 100.0 / games_played) PERSISTENT, -- Tasa de victoria calculada
    FOREIGN KEY (heroe_id) REFERENCES Heroes(heroe_id),
    FOREIGN KEY (enemigo_id) REFERENCES Heroes(heroe_id)
);
