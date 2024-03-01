package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.ModuleEmpregado.Service.EmpregadoService;
import br.ufal.ic.p2.wepayu.ModuleEmpregado.model.Empregado;
import br.ufal.ic.p2.wepayu.ModuleCartaoDePonto.Classes.CartaoDePonto;
import br.ufal.ic.p2.wepayu.ModuleSindicato.Classes.MembroSindicato;
import br.ufal.ic.p2.wepayu.ModuleVendas.Model.Vendas;

import java.util.HashMap;

public class Sistema {
    public static String id1;
    public static HashMap<String, Empregado> listaEmpregados = new HashMap<>();
    static EmpregadoService Empregados = new EmpregadoService();
    public static HashMap<String, CartaoDePonto> listaDeCartoes = new HashMap<>();
    public static HashMap<String, MembroSindicato> listaDeSindicalizados = new HashMap<>();

    public static HashMap<String, Vendas> listaDeVendedores = new HashMap<>();
    // <ID do vendedor, Venda> e Vendas -> possui Venda<Data,Valor> das vendas desse empregado
    public void zerarSistema(){
        listaEmpregados.clear();
        listaDeCartoes.clear();
        listaDeSindicalizados.clear();
        listaDeVendedores.clear();
    }
}
