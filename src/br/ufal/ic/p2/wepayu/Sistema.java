package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoService;
import br.ufal.ic.p2.wepayu.Empregado.model.Empregado;
import br.ufal.ic.p2.wepayu.CartaoDePonto.Classes.CartaoDePonto;
import br.ufal.ic.p2.wepayu.Sindicato.Classes.MembroSindicato;
import br.ufal.ic.p2.wepayu.Vendas.Model.Vendas;

import java.util.HashMap;

public class Sistema {
    public static String id1;
    public static HashMap<String, Empregado> listaEmpregados = new HashMap<>();
    static EmpregadoService Empregados = new EmpregadoService();
    public static HashMap<String, CartaoDePonto> listaDeCartoes = new HashMap<>();
    public static HashMap<String, MembroSindicato> listaDeSindicalizados = new HashMap<>();
    public static HashMap<String, Vendas> listaDeVendedores = new HashMap<>();

    public void zerarSistema(){
        listaEmpregados.clear();
    }
}

// <1, Empregado