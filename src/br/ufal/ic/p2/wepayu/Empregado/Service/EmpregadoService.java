package br.ufal.ic.p2.wepayu.Empregado.Service;
import br.ufal.ic.p2.wepayu.CartaoDePonto.Exceptions.ExceptionHoras;
import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;
import br.ufal.ic.p2.wepayu.Empregado.model.Empregado;

import java.util.Map;
import java.util.UUID;

import static br.ufal.ic.p2.wepayu.CartaoDePonto.ServiceCartaoDePonto.CriaCartao;
import static br.ufal.ic.p2.wepayu.CartaoDePonto.ValidationsCartaoDePonto.validaTipoHorista;
import static br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoValidations.validaRemocao;
import static br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoValidations.validaTipo;
import static br.ufal.ic.p2.wepayu.Sistema.listaEmpregados;

public class EmpregadoService {

    public String GerarId(){
        String id = UUID.randomUUID().toString();
        return id;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    //adiciona empregados nao comissionados
    public String addEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException, ExceptionHoras {
        if (tipo.equalsIgnoreCase("comissionado"))
            throw new EmpregadoException("Tipo nao aplicavel.");
        EmpregadoValidations.validarEmpregado(nome,endereco, tipo,salario);
        String id = GerarId();

        if (tipo.equalsIgnoreCase("horista"))
            CriaCartao(id);
        Empregado Empregado = new Empregado(nome,endereco,tipo,salario);
        listaEmpregados.put(id,Empregado);
        return id;
    }
    //adiciona somente empregados comissionados
    public String addEmpregado(String nome, String endereco, String tipo, String salario, String ValorComissao) throws EmpregadoException {
        if (!tipo.equalsIgnoreCase("comissionado"))
            throw new EmpregadoException("Tipo nao aplicavel.");
        EmpregadoValidations.validarEmpregado(nome,endereco, tipo,salario,ValorComissao);

        String id = GerarId();
        Empregado Empregado = new Empregado(nome,endereco,tipo,salario, ValorComissao);
        listaEmpregados.put(id,Empregado);
        return id;
    }

    public String getAtributoEmpregado(String id, String atributo) throws EmpregadoException {

        Empregado empregado = listaEmpregados.get(id);

        if (id.isEmpty())
            throw new EmpregadoException("Identificacao do empregado nao pode ser nula.");

        if (empregado == null)
            throw new EmpregadoException("Empregado nao existe.");


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
            case "comissao":
                return empregado.getComissao();
            default:
                throw new EmpregadoException("Atributo nao existe.");
        }
    }

    public void getEmpregadoPorNome(String nome){

    }

    public void removerEmpregado(String id) throws EmpregadoException {
        validaRemocao(id);
        listaEmpregados.remove(id);
    }

}
