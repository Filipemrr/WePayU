package br.ufal.ic.p2.wepayu.CartaoDePonto.Classes;

import java.util.HashMap;


public class CartaoDePonto {
    private String id;
    public HashMap<String, Horas> DataeHoras = new HashMap<>();

    public CartaoDePonto(String id) {
        this.id = id;
    }

}


//lancaCartao emp=${id1} data=1/1/2005 horas=8