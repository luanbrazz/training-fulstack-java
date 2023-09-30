package jdev.mentoria.lojavirtual.enums;

public enum StatusContaReceber {
    COBRANCA("Cobrança"),
    VENCIDA("Vencida"),
    ABERTA("Aberta"),
    QUITADA("Quitada");

    private String descricao;

    StatusContaReceber(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
