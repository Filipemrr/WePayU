package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.controllers.EmpregadoService;

public class Facade {
    EmpregadoService Empregados = new EmpregadoService();

    public void zerarSistema(){

    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
       return Empregados.addEmpregado(nome, endereco, tipo, salario);
    }

    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException {
         return Empregados.getAtributoEmpregado(emp, atributo);
    }
}
