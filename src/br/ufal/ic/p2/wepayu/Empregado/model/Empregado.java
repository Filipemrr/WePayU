package br.ufal.ic.p2.wepayu.Empregado.model;

import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;
    private boolean sindicalizado;
    private String comissao;
    private int horasNormaisTrabalhadas;



    public Empregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.sindicalizado = sindicalizado;
    }
    public Empregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoException {
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
    public String getComissao() {
        return comissao;
    }
    public boolean isSindicalizado() {return sindicalizado;}
}

//SubClasses
