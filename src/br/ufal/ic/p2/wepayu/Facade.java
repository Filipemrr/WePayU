package br.ufal.ic.p2.wepayu;
import static br.ufal.ic.p2.wepayu.ModuleEmpregado.Service.EmpregadoService.*;
import static br.ufal.ic.p2.wepayu.ModuleFolhaDePagamento.Service.FolhaService.geraBalancoTotal;
import static br.ufal.ic.p2.wepayu.ModuleCartaoDePonto.Service.ServiceCartaoDePonto.*;
import static br.ufal.ic.p2.wepayu.ModuleSindicato.Service.SindicatoService.*;
import static br.ufal.ic.p2.wepayu.ModuleVendas.Service.VendasService.GetVendasRealizadas;
import static br.ufal.ic.p2.wepayu.ModuleVendas.Service.VendasService.LancaVenda;

public class Facade {
    Sistema sistema = new Sistema();
    public void encerrarSistema(){}
    public void zerarSistema(){
        sistema.zerarSistema();
    }

    //CREATE
    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception { // Nao comissionado
        return Sistema.Empregados.AddEmpregado(nome, endereco, tipo, salario);
    }
    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception { //Comissionados
        return Sistema.Empregados.AddEmpregado(nome, endereco, tipo, salario, comissao);
    }

    //READ
    public String getAtributoEmpregado(String emp, String atributo) throws Exception {
         return Sistema.Empregados.GetAtributoEmpregado(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, int indice) throws Exception {
        return Sistema.Empregados.GetEmpregadoPorNome(nome, indice);
    }
    public String getTaxasServico(String id, String dataInicial, String dataFinal) throws Exception {
        return GetTaxasServico(id,dataInicial,dataFinal);
    }
    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws Exception {
        return GetVendasRealizadas(emp,dataInicial,dataFinal);
    }
    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
        return GetHorasNormaisTrabalhadas(emp,dataInicial,dataFinal);
    }
    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal){
        return GetHorasExtrasTrabalhadas(emp,dataInicial,dataFinal);
    }

    //UPDATE
    public void lancaCartao(String emp, String data, String horas) throws Exception {
        LancaCartao(emp,data,horas);
    }
    public void lancaVenda(String emp, String data, String horas) throws Exception {
        LancaVenda(emp,data,horas);
    }
    public void lancaTaxaServico(String idSindical, String data, String valor) throws Exception {
        LancaTaxaServico(idSindical,data,valor);
    }
    public void alteraEmpregado(String id, String atributo, String valor) throws Exception {
        if (atributo.equalsIgnoreCase("sindicato"))
            AlteraEmpregadoSindicato(id, atributo, valor);
        if (atributo.equalsIgnoreCase("comissao"))
            AlteraEmpregadoComissionado(id,atributo,valor);

        AlteraDefaultAtributoEmpregado(id, atributo, valor);
    }

    public String  totalFolha(String data) {
        return geraBalancoTotal(data);
    }

    //Altera Metodo de pagamento do empregado
    public void alteraEmpregado(String id, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws Exception {
        AlteraMetodoPagamentoEmpregado(id, atributo,  valor1,  banco,  agencia,contaCorrente);
    }
    //Torna empregado sindicalizado
    public void alteraEmpregado(String id, String atributo, String valor, String idSindical, String taxaSindical) throws Exception {
        SindicalizaEmpregado(id, atributo, valor, idSindical, taxaSindical);
    }
    //Torna empregado Comissionado
    public void alteraEmpregado(String id, String tipo, String valor, String valorComissao) throws Exception {
            ComissionaEmpregado(id,tipo,valor, valorComissao);
    }


    //DELETE
    public void removerEmpregado(String emp) throws Exception {
        Sistema.Empregados.removerEmpregado(emp);
    }
}
