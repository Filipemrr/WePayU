package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;
    private boolean sindicalizado;
    private String comissao;



    public Empregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.sindicalizado = sindicalizado;
    }
    public Empregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.sindicalizado = sindicalizado;
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

    public String getSalario() {
        return salario;
    }
    public boolean isSindicalizado() {return sindicalizado;}
}

//SubClasses
