package jdev.mentoria.lojavirtual.model;

import jdev.mentoria.lojavirtual.enums.StatusContaReceber;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "conta_receber")
//permite que você crie valores de chave primária sequenciais automaticamente ao persistir objetos da entidade.
@SequenceGenerator(name = "seq_conta_receber", sequenceName = "seq_conta_receber", allocationSize = 1, initialValue = 1)
public class ContaReceber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta_receber")
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusContaReceber status;

    @Temporal(TemporalType.DATE)
    private Date dtVencimento;

    @Temporal(TemporalType.DATE)
    private Date dtPagamento;

    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusContaReceber getStatus() {
        return status;
    }

    public void setStatus(StatusContaReceber status) {
        this.status = status;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Date getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(Date dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaReceber that = (ContaReceber) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
