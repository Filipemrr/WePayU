package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.CartaoDePonto.Exceptions.ExceptionHoras;
import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;
import br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoService;

import static br.ufal.ic.p2.wepayu.CartaoDePonto.ServiceCartaoDePonto.*;

public class Facade {
    Sistema sistema = new Sistema();
    EmpregadoService Empregados = new EmpregadoService();
    public void encerrarSistema(){
        sistema.zerarSistema();
    }
    public void zerarSistema(){
        sistema.zerarSistema();
    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException, ExceptionHoras { // Nao comissionado
        return Empregados.addEmpregado(nome, endereco, tipo, salario);
    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoException, ExceptionHoras { //Comissionados
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
    public void lancaCartao(String emp, String data, String horas) throws ExceptionHoras {
        LancaCartao(emp,data,horas);
    }
    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws ExceptionHoras {
        return GetHorasNormaisTrabalhadas(emp,dataInicial,dataFinal);
    }
    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal){
        return GetHorasExtrasTrabalhadas(emp,dataInicial,dataFinal);
    }
}
