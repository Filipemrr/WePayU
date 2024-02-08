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
    }


    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }
    public String getId(){return id;}

    public String getSalario() {
        return salario;
    }
    public String getComissao() {
        return comissao;
    }

    public Boolean getIsSindicalizado() { return isSindicalizado;}
    public void SetisSindicalizado(boolean valor) {isSindicalizado = valor;}
    public void SetTipo(String atributo) {this.tipo =atributo;}
}

//SubClasses
