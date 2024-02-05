package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.controllers.EmpregadoService;

public class Facade {
    EmpregadoService Empregados = new EmpregadoService();

    public void zerarSistema(){

    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException { // Nao comissionado
        return Empregados.addEmpregado(nome, endereco, tipo, salario);
    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoException { //Comissionados
        return Empregados.addEmpregado(nome, endereco, tipo, salario, comissao);
    }
    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoException {
         return Empregados.getAtributoEmpregado(emp, atributo);
    }
}
