package jdev.mentoria.lojavirtual.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity // Anotação indicando que essa classe é uma entidade mapeada no banco de dados.
@Table(name = "usuario") // Define o nome da tabela no banco de dados para essa entidade.
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
// Gera uma sequência de valores para a chave primária.
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id // Define que o campo a seguir é a chave primária da entidade.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    // Configura a geração automática de valores para a chave primária.
    private Long id;

    @Column(nullable = false)
    private String login; // Campo para armazenar o login do usuário.

    @Column(nullable = false)
    private String senha; // Campo para armazenar a senha do usuário.

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataAtualSenha;

    @OneToMany(fetch = FetchType.EAGER) // Define um relacionamento de um-para-muitos com a entidade Acesso.
// Define uma tabela de junção chamada "usuario_acesso" para mapear o relacionamento entre "Usuario" e "Acesso".
    @JoinTable(
            name = "usuario_acesso",

            // Configura uma restrição única para garantir que não haja duplicatas na tabela de junção.
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "acesso_id"},
                    name = "unique_acesso_user"),

            // Define como as colunas da tabela de junção se relacionam com as tabelas de entidade.
            joinColumns = @JoinColumn(
                    name = "usuario_id", // Coluna "usuario_id" na tabela de junção.
                    referencedColumnName = "id", // Referencia a coluna "id" da tabela "usuario".
                    table = "usuario", // A tabela "usuario" está envolvida no relacionamento.
                    unique = false, // Não é necessário que os valores sejam exclusivos na coluna "usuario_id" da tabela de junção.

                    // Define a chave estrangeira (foreign key) na coluna "usuario_id" com nome "usuario_fk" e restrição de integridade.
                    foreignKey = @ForeignKey(name = "usuario_fk", value = ConstraintMode.CONSTRAINT)
            ),

            // Define como as colunas de junção inversa se relacionam com as tabelas de entidade.
            inverseJoinColumns = @JoinColumn(
                    name = "acesso_id", // Coluna "acesso_id" na tabela de junção.
                    unique = false, // Não é necessário que os valores sejam exclusivos na coluna "acesso_id" da tabela de junção.
                    referencedColumnName = "id", // Referencia a coluna "id" da tabela "acesso".
                    table = "acesso", // A tabela "acesso" está envolvida no relacionamento.

                    // Define a chave estrangeira (foreign key) na coluna "acesso_id" com nome "acesso_fk" e restrição de integridade.
                    foreignKey = @ForeignKey(name = "acesso_fk", value = ConstraintMode.CONSTRAINT)
            )
    )
    private List<Acesso> acessos; // Lista de acessos associados ao usuário.

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "pessoa_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
    private Pessoa pessoa;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private Pessoa empresa;

    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Pessoa empresa) {
        this.empresa = empresa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataAtualSenha() {
        return dataAtualSenha;
    }

    public void setDataAtualSenha(Date dataAtualSenha) {
        this.dataAtualSenha = dataAtualSenha;
    }

    public List<Acesso> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<Acesso> acessos) {
        this.acessos = acessos;
    }

    /*Autoridades = são os acessos, ROLE_ADMIN, ROLE_SECRETARIO...*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.acessos; // Retorna a lista de acessos como as autoridades do usuário.
    }

    @Override
    public String getPassword() {
        return this.senha; // Retorna a senha do usuário.
    }

    @Override
    public String getUsername() {
        return this.login; // Retorna o login do usuário.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // A conta do usuário nunca expira.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // A conta do usuário nunca é bloqueada.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // As credenciais do usuário nunca expiram.
    }

    @Override
    public boolean isEnabled() {
        return true; // O usuário está habilitado.
    }
}
