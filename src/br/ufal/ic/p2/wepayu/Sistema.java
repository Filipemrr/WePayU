package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Empregado.model.Empregado;
import br.ufal.ic.p2.wepayu.CartaoDePonto.Classes.CartaoDePonto;

import java.util.HashMap;

public class Sistema {
    public static HashMap<String, Empregado> listaEmpregados = new HashMap<>();
    public static HashMap<String, CartaoDePonto> listaDeCartoes = new HashMap<>();

    public void zerarSistema(){
        listaEmpregados.clear();
    }
}
