package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoException;
import br.ufal.ic.p2.wepayu.Exception.NaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.SemEnderecoException;
import br.ufal.ic.p2.wepayu.Exception.SemNomeException;
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
    public String getAtributoEmpregado(String emp, String atributo) throws NaoExisteException {
         return Empregados.getAtributoEmpregado(emp, atributo);
    }
}
