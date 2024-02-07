package br.ufal.ic.p2.wepayu.CartaoDePonto.Classes;

public class Horas {
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
