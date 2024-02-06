package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;
import br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoService;

public class Facade {
    Sistema sistema = new Sistema();
    EmpregadoService Empregados = new EmpregadoService();
    public void encerrarSistema(){
        sistema.zerarSistema();
    }
    public void zerarSistema(){
        sistema.zerarSistema();
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

    public void getEmpregadoPorNome(String nome) throws EmpregadoException {
        Empregados.getEmpregadoPorNome(nome);
    }
    public void removerEmpregado(String emp) throws EmpregadoException {
        Empregados.removerEmpregado(emp);
    }
}
