-- Adiciona uma nova coluna chamada 'tipo_pessoa' à tabela 'pessoa_fisica'
ALTER TABLE pessoa_fisica
    ADD COLUMN tipo_pessoa character varying(255);

ALTER TABLE pessoa_juridica
    ADD COLUMN tipo_pessoa character varying(255);
