package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.CartaoDePonto.Model.ExceptionHoras;
import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;
import br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoService;
import br.ufal.ic.p2.wepayu.Sindicato.Exception.SindicatoExceptions;
import br.ufal.ic.p2.wepayu.Vendas.Exception.ExceptionVendas;

import static br.ufal.ic.p2.wepayu.CartaoDePonto.Service.ServiceCartaoDePonto.*;
import static br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoService.*;
import static br.ufal.ic.p2.wepayu.Sindicato.Service.SindicatoService.*;
import static br.ufal.ic.p2.wepayu.Vendas.Service.VendasService.GetVendasRealizadas;
import static br.ufal.ic.p2.wepayu.Vendas.Service.VendasService.LancaVenda;

public class Facade {
    Sistema sistema = new Sistema();
    public void encerrarSistema(){}
    public void zerarSistema(){
        sistema.zerarSistema();
    }

    //CREATE
    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException, ExceptionHoras { // Nao comissionado
        return Sistema.Empregados.AddEmpregado(nome, endereco, tipo, salario);
    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoException, ExceptionHoras { //Comissionados
        return Sistema.Empregados.AddEmpregado(nome, endereco, tipo, salario, comissao);
    }

    //READ
    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoException {
         return Sistema.Empregados.GetAtributoEmpregado(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, int indice) throws EmpregadoException {
        return Sistema.Empregados.GetEmpregadoPorNome(nome, indice);
    }
    public String getTaxasServico(String id, String dataInicial, String dataFinal) throws SindicatoExceptions {
        return GetTaxasServico(id,dataInicial,dataFinal);
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

    //UPDATE
    public void lancaCartao(String emp, String data, String horas) throws ExceptionHoras {
        LancaCartao(emp,data,horas);
    }
    public void lancaVenda(String emp, String data, String horas) throws ExceptionVendas {
        LancaVenda(emp,data,horas);
    }
    public void lancaTaxaServico(String idSindical, String data, String valor) throws SindicatoExceptions {
        LancaTaxaServico(idSindical,data,valor);
    }
    public void alteraEmpregado(String id, String atributo, String valor) throws SindicatoExceptions, EmpregadoException {
        if (atributo.equalsIgnoreCase("sindicato"))
            AlteraEmpregadoSindicato(id, atributo, valor);
        if (atributo.equalsIgnoreCase("comissao"))
            AlteraEmpregadoComissionado(id,atributo,valor);

        AlteraDefaultAtributoEmpregado(id, atributo, valor);
    }
    //Altera Metodo de pagamento do empregado
    public void alteraEmpregado(String id, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws EmpregadoException {
        AlteraMetodoPagamentoEmpregado(id, atributo,  valor1,  banco,  agencia,contaCorrente);
    }
    //Torna empregado sindicalizado
    public void alteraEmpregado(String id, String atributo, Boolean valor, String idSindical, String taxaSindical) throws SindicatoExceptions {
        SindicalizaEmpregado(id, atributo, valor, idSindical, taxaSindical);
    }
    //Torna empregado Comissionado
    public void alteraEmpregado(String id, String tipo, String valor, String valorComissao) throws SindicatoExceptions, EmpregadoException {
            ComissionaEmpregado(id,tipo,valor, valorComissao);
    }


    //DELETE
    public void removerEmpregado(String emp) throws EmpregadoException {
        Sistema.Empregados.removerEmpregado(emp);
    }
}
