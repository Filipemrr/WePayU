package br.ufal.ic.p2.wepayu.ModuleSindicato.Classes;

import java.util.HashMap;

public class MembroSindicato {
    private String idDoEmpregado;
    private String idSindical;
    private String taxaSindical;
    public HashMap<String,Double> DataeTaxa = new HashMap<>();

    public MembroSindicato(String idDoEmpregado, String idSindical, String taxaSindical){
        this.idDoEmpregado = idDoEmpregado;
        this.idSindical = idSindical;
        this.taxaSindical = taxaSindical;
    }

    public String getIdDoEmpregado() {return idDoEmpregado;}
    public String getIdSindical() {return idSindical;}
    public void cadastrarNovaTaxa(String data, Double valor) {
        DataeTaxa.put(data, valor);
    }
    public String getTaxaSindical() {
        try {
            int inteiro = Integer.parseInt(taxaSindical);
            return inteiro + ",00";
    } catch (NumberFormatException e) {
        return taxaSindical;
    }}

}
