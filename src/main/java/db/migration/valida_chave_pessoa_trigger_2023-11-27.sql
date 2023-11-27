-- Define uma função chamada validaChavePessoa
CREATE OR REPLACE FUNCTION validaChavePessoa()
-- Retorna um trigger
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
-- Declaração de variável local
DECLARE
existe INTEGER;

BEGIN
    -- Verifica se a pessoa com o ID especificado existe na tabela pessoa_fisica
SELECT COUNT(1) INTO existe FROM pessoa_fisica WHERE id = NEW.pessoa_id;

IF (existe <= 0) THEN
        -- Se não encontrada na tabela pessoa_fisica, verifica na tabela pessoa_juridica
SELECT COUNT(1) INTO existe FROM pessoa_juridica WHERE id = NEW.pessoa_id;

-- Se não encontrada na tabela pessoa_juridica, lança uma exceção
IF (existe <= 0) THEN
            RAISE EXCEPTION 'Não foi encontrado o ID e PK da pessoa para realizar a associação do cadastro';
END IF;
END IF;

RETURN NEW;
END;
$$;


-- Define um trigger chamado validaChavePessoaAvaliacaoProduto
CREATE TRIGGER validaChavePessoa
    BEFORE UPDATE
    ON vd_cp_loja_virt
    FOR EACH ROW
-- Executa a função validaChavePessoa antes de cada atualização na tabela avaliacao_produto
    EXECUTE procedure validaChavePessoa();

CREATE TRIGGER validaChavePessoa2
    BEFORE insert
    ON vd_cp_loja_virt
    FOR EACH ROW
-- Executa a função validaChavePessoa antes de cada atualização na tabela avaliacao_produto
    EXECUTE procedure validaChavePessoa();
