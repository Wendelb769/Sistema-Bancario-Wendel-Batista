CREATE DATABASE banco;

USE banco;

CREATE TABLE usuario(
id INT AUTO_INCREMENT PRIMARY KEY,

cpf VARCHAR(11) NOT NULL UNIQUE,
CHECK(CHAR_LENGTH(cpf) = 11),

senha VARCHAR(8) NOT NULL,
CHECK(CHAR_LENGTH(senha) = 8),

rg VARCHAR(10) NOT NULL,
CHECK(CHAR_LENGTH(rg) = 10),

nomeCompleto VARCHAR(50) NOT NULL,

dataNascimento VARCHAR(10) NOT NULL,

numero VARCHAR(11) NOT NULL,
CHECK(CHAR_LENGTH(numero) = 11)
);

CREATE TABLE contaBancaria(
id_conta INT AUTO_INCREMENT PRIMARY KEY,
id_usuario INT NOT NULL,
cpf_usuario VARCHAR(11) NOT NULL,

saldo DECIMAL (10, 2) DEFAULT 0.00,

CONSTRAINT fk_usuario
FOREIGN KEY (id_usuario)
REFERENCES usuario(id)
ON DELETE CASCADE,

CONSTRAINT fk_cpf_usuario
FOREIGN KEY (cpf_usuario)
REFERENCES usuario(cpf)
);

INSERT INTO usuario (cpf, senha, rg, nomeCompleto, dataNascimento, numero) VALUES ();
INSERT INTO contaBancaria (id_usuario, cpf_usuario, saldo) VALUES ();

SELECT * FROM usuario;
SELECT * FROM contaBancaria;