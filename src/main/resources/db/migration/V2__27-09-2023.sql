-- Em resumo, esta consulta é usada para encontrar todas as restrições de chave estrangeira na tabela usuario_acesso que
-- envolvem a coluna acesso_id, exceto aquelas que têm o nome específico 'unique_acesso_user'. Isso pode ser útil para
-- listar todas as restrições de chave estrangeira, exceto uma específica, no contexto de consultas e operações de banco
-- de dados.
select constraint_name
from information_schema.constraint_column_usage
where table_name = 'usuario_acesso'
  and column_name = 'acesso_id'
  and constraint_name <> 'unique_acesso_user';

alter table usuario_acesso  drop constraint "uk_fhwpg5wu1u5p306q8gycxn9ky";
