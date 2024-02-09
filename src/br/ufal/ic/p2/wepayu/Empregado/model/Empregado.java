package br.ufal.ic.p2.wepayu.Empregado.model;

import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;

public class Empregado {
    private String id;
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;
    private boolean isSindicalizado;
    private String comissao;
    private String metodoDePagamento;
    private Boolean isComissionado = false;
    private InformacoesBancarias informacoesBancarias;
    private boolean isRecebedorPorBanco;

    public Empregado(String id, String nome, String endereco, String tipo, String salario) throws EmpregadoException {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.isSindicalizado = isSindicalizado;
    }
    public Empregado(String id, String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoException {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.comissao = comissao;
        this.isComissionado = true;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String Nome) {this.nome = Nome;}

    public String getEndereco() {return endereco;}
    public void setEndereco(String Endereco) {this.endereco = Endereco;}

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String Tipo) {this.tipo = Tipo;}

    public String getId(){return id;}

    public String getSalario() {
        return salario;
    }
    public void setSalario(String Salario) {this.salario = Salario;}

    public String getComissao() {
        return comissao;
    }
    public void setComissao(String NovaComissao) {this.comissao = NovaComissao;}

    public String getMetodoDePagamento() { return metodoDePagamento;}
    public void setMetodoDePagamento(String MetodoDePagamento) {this.metodoDePagamento = MetodoDePagamento;}
    public boolean getisComissionado(){return isComissionado;}
    public void SetisComissionado(Boolean valor){this.isComissionado = valor;}

    public void SetInformacoesBancarias(InformacoesBancarias informacoesBancarias){
        this.informacoesBancarias = informacoesBancarias;
        this.isRecebedorPorBanco = true;
    }
    public String getInformacaoBancaria(String infoRequerida){
        if (infoRequerida.equalsIgnoreCase("banco"))
            return informacoesBancarias.getBanco();
        if (infoRequerida.equalsIgnoreCase("agencia"))
            return informacoesBancarias.getAgencia();
        if (infoRequerida.equalsIgnoreCase("contaCorrente"))
            return informacoesBancarias.getContaCorrente();
        if (infoRequerida.equalsIgnoreCase("valor"))
            return informacoesBancarias.getValor();

        return "Empregado nao recebe em banco.";
    }
    public boolean isRecebedorPorBanco(){return isRecebedorPorBanco;}
    public Boolean getIsSindicalizado() { return isSindicalizado;}
    public void SetisSindicalizado(boolean valor) {isSindicalizado = valor;}
    public void SetTipo(String atributo) {this.tipo =atributo;}

}

//SubClasses
