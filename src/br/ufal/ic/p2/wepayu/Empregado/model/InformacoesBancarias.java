package br.ufal.ic.p2.wepayu.Empregado.model;

public class InformacoesBancarias {
    private String valor;
    private String banco;
    private String agencia;
    private String contaCorrente;

    // Construtor
    public InformacoesBancarias(String valor, String banco, String agencia, String contaCorrente) {
        this.valor = valor;
        this.banco = banco;
        this.agencia = agencia;
        this.contaCorrente = contaCorrente;
    }

    // Métodos de acesso
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(String metodoDePagamento) {
        this.contaCorrente = metodoDePagamento;
    }
}
