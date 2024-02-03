package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.Empregado;

import java.util.HashMap;
import java.util.UUID;

public class EmpregadoService {
    private HashMap<String, Empregado> listaEmpregados = new HashMap<>();
    public String GerarId(){
        String id = UUID.randomUUID().toString();
        return id;
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String addEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        String id = GerarId();
        Empregado Empregado = new Empregado(nome,endereco,tipo,salario);
        listaEmpregados.put(id,Empregado);
        return id;
    }
    public String getAtributoEmpregado(String id, String atributo) throws EmpregadoNaoExisteException {

        Empregado empregado = listaEmpregados.get(id);

        if (empregado == null)
            throw new EmpregadoNaoExisteException();

        switch (atributo) {
            case "nome":
                return empregado.getNome();
            case "endereco":
                return empregado.getEndereco();
            case "tipo":
                return empregado.getTipo();

            case "salario":
                String salarioStr = empregado.getSalario();
                if (isNumeric(salarioStr)) {
                    double salario = Double.parseDouble(salarioStr);
                    if (salario % 1 == 0) { // se for inteiro
                        return String.format("%.0f,00", salario);
                    } else { //se for decimal
                        return String.format("%.2f", salario).replace(".", ",");
                    }
                } else {
                    return salarioStr; // Retornar a string original se não for um número
                }

            case "sindicalizado":
                if (empregado.isSindicalizado()){
                    return String.valueOf(empregado.isSindicalizado());
                }else
                    return "false";

            default:
                throw new EmpregadoNaoExisteException();
        }
    }

}
