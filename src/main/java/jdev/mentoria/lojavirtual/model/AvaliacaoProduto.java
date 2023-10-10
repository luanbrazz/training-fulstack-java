package jdev.mentoria.lojavirtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "avaliacao_produto")
@SequenceGenerator(name = "seq_avaliacao_produto", sequenceName = "seq_avaliacao_produto", allocationSize = 1, initialValue = 1)
public class AvaliacaoProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avaliacao_produto")
    private Long id;

    @Column(nullable = false)
    private Integer nota;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(
            name = "pessoa_id", // Nome da coluna no banco de dados que armazenará a chave estrangeira.
            nullable = false,   // Indica que a coluna "pessoa_id" não pode conter valores nulos.
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT, // Define o modo de restrição da chave estrangeira.
                    name = "pessoa_fk" // Nome da restrição de chave estrangeira no banco de dados.
            )
    )
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(
            name = "produto_id", // Nome da coluna no banco de dados que armazenará a chave estrangeira.
            nullable = false,   // Indica que a coluna "produto_id" não pode conter valores nulos.
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT, // Define o modo de restrição da chave estrangeira.
                    name = "produto_fk" // Nome da restrição de chave estrangeira no banco de dados.
            )
    )
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvaliacaoProduto that = (AvaliacaoProduto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
