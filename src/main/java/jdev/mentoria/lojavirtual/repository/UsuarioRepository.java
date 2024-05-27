package jdev.mentoria.lojavirtual.repository;

import jdev.mentoria.lojavirtual.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);

    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
    Usuario findUserByPessoa(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuario_acesso'   and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user';\n", nativeQuery = true)
    String consultaConstraintAcesso();

    @Transactional
    @Modifying
    @Query(value = "insert into usuario_acesso(usuario_id, acesso_id) VALUES (?1, (select id from acesso where descricao = 'ROLE_USER'))", nativeQuery = true)
    void insereAcessoUserPj(Long id);
}
