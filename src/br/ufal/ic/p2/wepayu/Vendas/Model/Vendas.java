package br.ufal.ic.p2.wepayu.Vendas.Model;

import br.ufal.ic.p2.wepayu.CartaoDePonto.Classes.Horas;

import java.util.HashMap;

public class Vendas {
    private String id;
    public HashMap<String, Double> VendaDoEmpregado = new HashMap<>();

    public Vendas(String id){
        this.id = id;
    }
    public void cadastrarNovaVenda(String data, Double valor) {
        VendaDoEmpregado.put(data, valor);
    }
    public Double obterValorVenda(String data) {
        return VendaDoEmpregado.get(data);
    }
}


