package jdev.mentoria.lojavirtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "nota_item_produto")
@SequenceGenerator(name = "seq_nota_item_produto", sequenceName = "seq_nota_item_produto", allocationSize = 1, initialValue = 1)
public class NotaItemProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_item_produto")
    private Long id;

    @Column(nullable = false)
    private Double quantidade;

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

    @ManyToOne
    @JoinColumn(
            name = "nota_fiscal_compra_id", // Nome da coluna no banco de dados que armazenará a chave estrangeira.
            nullable = false,   // Indica que a coluna "nota_fiscal_compra_id" não pode conter valores nulos.
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT, // Define o modo de restrição da chave estrangeira.
                    name = "nota_fiscal_compra_fk" // Nome da restrição de chave estrangeira no banco de dados.
            )
    )
    private NotaFiscalCompra notaFiscalCompra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public NotaFiscalCompra getNotaFiscalCompra() {
        return notaFiscalCompra;
    }

    public void setNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) {
        this.notaFiscalCompra = notaFiscalCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaItemProduto that = (NotaItemProduto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
