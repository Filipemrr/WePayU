package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Empregado.model.Empregado;

import java.util.HashMap;
import java.util.Map;

public class Sistema {
    public static HashMap<String, Empregado> listaEmpregados = new HashMap<>();
    public void zerarSistema(){
        listaEmpregados.clear();
    }
}
