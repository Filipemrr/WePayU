package br.ufal.ic.p2.wepayu.CartaoDePonto;

import java.util.HashMap;


public class CartaoDePonto {
    private String id;
    HashMap<String, Horas> DataeHoras = new HashMap<>();

    public CartaoDePonto(String id) {
        this.id = id;
    }

}


class Horas {
    private double horasNormais;
    private double horasExcedentes;

    public Horas(double horasNormais, double horasExcedentes) {
        this.horasNormais = horasNormais;
        this.horasExcedentes = horasExcedentes;
    }

    public double getHorasNormais(){
        return this.horasNormais;
    }

    public double getHorasExcedentes() {return horasExcedentes;}
}

//lancaCartao emp=${id1} data=1/1/2005 horas=8