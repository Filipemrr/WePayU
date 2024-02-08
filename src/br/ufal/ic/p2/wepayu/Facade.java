package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.CartaoDePonto.Model.ExceptionHoras;
import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;
import br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoService;
import br.ufal.ic.p2.wepayu.Vendas.Exception.ExceptionVendas;

import static br.ufal.ic.p2.wepayu.CartaoDePonto.Service.ServiceCartaoDePonto.*;
import static br.ufal.ic.p2.wepayu.Vendas.Service.VendasService.GetVendasRealizadas;
import static br.ufal.ic.p2.wepayu.Vendas.Service.VendasService.LancaVenda;

public class Facade {
    Sistema sistema = new Sistema();

    public void encerrarSistema(){}
    public void zerarSistema(){
        sistema.zerarSistema();
    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException, ExceptionHoras { // Nao comissionado
        return Sistema.Empregados.addEmpregado(nome, endereco, tipo, salario);
    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoException, ExceptionHoras { //Comissionados
        return Sistema.Empregados.addEmpregado(nome, endereco, tipo, salario, comissao);
    }
    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoException {
         return Sistema.Empregados.getAtributoEmpregado(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, int indice) throws EmpregadoException {
        return Sistema.Empregados.getEmpregadoPorNome(nome, indice);
    }
    public void removerEmpregado(String emp) throws EmpregadoException {
        Sistema.Empregados.removerEmpregado(emp);
    }
    public void lancaCartao(String emp, String data, String horas) throws ExceptionHoras {
        LancaCartao(emp,data,horas);
    }
    public void lancaVenda(String emp, String data, String horas) throws ExceptionVendas {
        LancaVenda(emp,data,horas);
    }
    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws ExceptionVendas {
        return GetVendasRealizadas(emp,dataInicial,dataFinal);
    }
    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws ExceptionHoras {
        return GetHorasNormaisTrabalhadas(emp,dataInicial,dataFinal);
    }
    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal){
        return GetHorasExtrasTrabalhadas(emp,dataInicial,dataFinal);
    }
}
