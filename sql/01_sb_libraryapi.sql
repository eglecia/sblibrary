-- -----------------------------------------------------------------------------
-- Autor: André L Eglecia
-- Email: 
-- Empresa: Particular
-- Data: 16/07/2025
-- Projeto: Library API
-- Descrição: DB de teste para projetos WEB REST
-- -----------------------------------------------------------------------------

-- Cria database se não existir
-- createdb library

-- Conectar ao banco via psql e rodar esse script
-- psql -U postgres -h localhost -f <nome_do_arquivo>

-- Cria usuário se não existir
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_user WHERE usename = 'library_user') THEN
        CREATE USER library_user WITH PASSWORD '12345678';
    END IF;
END
$$;

-- Concede permissões para o novo usuário (ainda no banco postgres)
GRANT ALL PRIVILEGES ON DATABASE library TO library_user;

-- Agora conecte-se ao banco "library"
\connect library

-- Configura para utilizar 'UTC'
ALTER DATABASE library SET timezone TO 'UTC';

-- Extensões
CREATE EXTENSION IF NOT EXISTS "unaccent";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criação do enum
CREATE TYPE egenre AS ENUM('FICCAO', 'FANTASIA', 'MISTERIO','ROMANCE', 'BIOGRAFIA', 'CIENCIA');

-- Tabela author
CREATE TABLE author(
    id UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    name varchar(100) NOT NULL,
    dt_birthday date NOT NULL,
    nationality varchar(50) NOT NULL,
    dt_created TIMESTAMP WITH TIME ZONE DEFAULT now(),
    dt_updated TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Tabela book
CREATE TABLE book(
    id UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    isbn varchar(30) UNIQUE NOT NULL,
    title varchar(100) NOT NULL,
    dt_published date NOT NULL,
    genre egenre NOT NULL,
    price numeric(18,2) NOT NULL,
    id_user UUID,
    id_author UUID NOT NULL REFERENCES author(id),
    dt_created TIMESTAMP WITH TIME ZONE DEFAULT now(),
    dt_updated TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE users(
    id UUID NOT NULL PRIMARY KEY,
    login varchar(20) NOT NULL UNIQUE,
    password varchar(300) NOT NULL,
    roles varchar[]
);

-- JPA não consegue trabalhar direito com tipos enum do banco, então ajudamos aqui ...
CREATE CAST (varchar AS egenre) WITH INOUT AS IMPLICIT;

-- Concede permissões nas tabelas/sequências criadas
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO library_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO library_user;

