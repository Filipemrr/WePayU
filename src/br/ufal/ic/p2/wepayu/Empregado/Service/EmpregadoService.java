package br.ufal.ic.p2.wepayu.Empregado.Service;
import br.ufal.ic.p2.wepayu.CartaoDePonto.Model.ExceptionHoras;
import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;
import br.ufal.ic.p2.wepayu.Empregado.model.Empregado;
import br.ufal.ic.p2.wepayu.Empregado.model.InformacoesBancarias;
import br.ufal.ic.p2.wepayu.Sindicato.Classes.MembroSindicato;
import br.ufal.ic.p2.wepayu.Sindicato.Exception.SindicatoExceptions;
import br.ufal.ic.p2.wepayu.Sistema;

import java.rmi.server.ExportException;
import java.util.UUID;

import static br.ufal.ic.p2.wepayu.CartaoDePonto.Service.ServiceCartaoDePonto.CriaCartao;
import static br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoValidations.validaGetAtributoEmpregado;
import static br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoValidations.validaRemocao;
import static br.ufal.ic.p2.wepayu.Sindicato.Service.SindicatoService.criaVinculoSindical;
import static br.ufal.ic.p2.wepayu.Sindicato.Service.SindicatoValidations.alteraEmpregadoValidation;
import static br.ufal.ic.p2.wepayu.Sistema.*;
import static br.ufal.ic.p2.wepayu.Vendas.Service.VendasService.criaCartaoDeVendas;

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

    //Create
    public String AddEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException, ExceptionHoras {
        if (tipo.equalsIgnoreCase("comissionado"))
            throw new EmpregadoException("Tipo nao aplicavel.");
        EmpregadoValidations.validarEmpregado(nome,endereco, tipo,salario);
        String id = GerarId();
        if (tipo.equalsIgnoreCase("horista"))
            CriaCartao(id);
        Empregado Empregado = new Empregado(id,nome,endereco,tipo,salario);
        listaEmpregados.put(id,Empregado);
        return id;
    }
    public String AddEmpregado(String nome, String endereco, String tipo, String salario, String ValorComissao) throws EmpregadoException {
        if (!tipo.equalsIgnoreCase("comissionado"))
            throw new EmpregadoException("Tipo nao aplicavel.");

        EmpregadoValidations.validarEmpregado(nome,endereco, tipo,salario,ValorComissao);
        String id = GerarId();
        criaCartaoDeVendas(id);
        Empregado Empregado = new Empregado(id,nome,endereco,tipo,salario, ValorComissao);
        listaEmpregados.put(id,Empregado);
        return id;
    }

    //Read
    public String GetEmpregadoPorNome(String nome, int indice) throws EmpregadoException {
        int i = 0;
        for (Empregado empregado : listaEmpregados.values()) {
            if (empregado.getNome().equals(nome))
                i++;

            if (indice == i) {
                return empregado.getId();
            }
        }
        throw new EmpregadoException("Nao ha empregado com esse nome.");
    }
    public String GetAtributoEmpregado(String id, String atributo) throws EmpregadoException {
        validaGetAtributoEmpregado(id, atributo);
        Empregado empregado = listaEmpregados.get(id);

        if (id.isEmpty())
            throw new EmpregadoException("Identificacao do empregado nao pode ser nula.");

        if (empregado == null)
            throw new EmpregadoException("Empregado nao existe.");

        if (atributo.equals("nome")) {
            return empregado.getNome();
        }

        if (atributo.equals("endereco")) {
            return empregado.getEndereco();
        }

        if (atributo.equals("tipo")) {
            return empregado.getTipo();
        }

        if (atributo.equals("salario")) {
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
        }

        if (atributo.equals("sindicalizado")) {
            if (empregado.getIsSindicalizado()){
                return String.valueOf(empregado.getIsSindicalizado());
            } else {
                return "false";
            }
        }

        if (atributo.equals("comissao")) {
            if (empregado.getisComissionado())
                return empregado.getComissao();

            throw new EmpregadoException("Empregado nao eh comissionado.");
        }

        if (atributo.equals("metodoPagamento")) {
            if (empregado.isRecebedorPorBanco()) {
                return "banco";
            } else {
                return "emMaos";
            }
        }

        if (atributo.equalsIgnoreCase("idSindicato") || atributo.equalsIgnoreCase("taxaSindical")) {
            if(!empregado.getIsSindicalizado())
                throw new EmpregadoException("Empregado nao eh sindicalizado.");

            MembroSindicato sindicalista = listaDeSindicalizados.get(id);
            if (atributo.equals("idSindicato")) return sindicalista.getIdSindical();
            if (atributo.equals("taxaSindical")) return sindicalista.getTaxaSindical();
        }

        if (atributo.equals("banco") || atributo.equalsIgnoreCase("agencia") || atributo.equalsIgnoreCase("contaCorrente") || atributo.equalsIgnoreCase("valor"))
            if (!empregado.isRecebedorPorBanco())
                throw new EmpregadoException("Empregado nao recebe em banco.");
            else
                return empregado.getInformacaoBancaria(atributo);

        throw new EmpregadoException("Atributo nao existe.");
    }

    //UPDATE
    public static void AlteraMetodoPagamentoEmpregado(String id, String atributo, String valor1, String banco, String agencia, String contaCorrente){
        Empregado empregadoAtualizado = listaEmpregados.get(id);
        InformacoesBancarias infosBancariasEmpregado = new InformacoesBancarias(valor1,banco,agencia,contaCorrente);
        empregadoAtualizado.SetInformacoesBancarias(infosBancariasEmpregado);
        empregadoAtualizado.setMetodoDePagamento("banco");
        listaEmpregados.put(id,empregadoAtualizado);
    }
    public static void AlteraEmpregadoComissionado(String id, String atributo, String valor) throws EmpregadoException {
        Empregado empregado = listaEmpregados.get(id);
        if (empregado.getTipo().equals("comissionado")){
            empregado.setComissao(valor);
            listaEmpregados.put(id,empregado);
        }
        else
            throw new EmpregadoException("Empregado nao eh comissionado.");
    }
    public static void AlteraEmpregadoSindicato(String id, String atributo, String valor) throws EmpregadoException {
        Empregado empregado = listaEmpregados.get(id);
        if (valor.equalsIgnoreCase("false"))
            empregado.SetisSindicalizado(false);

        if (valor.equalsIgnoreCase("true"))
            empregado.SetisSindicalizado(true);

        if (!empregado.getisComissionado())
            throw new EmpregadoException("Empregado nao eh sindicalizado.");
    }
    public static void ComissionaEmpregado(String id, String atributo, String valor,String comissao) throws EmpregadoException {
            Empregado empregado = listaEmpregados.get(id);
            empregado.setTipo("comissionado");
            empregado.setComissao(comissao);
            empregado.SetisComissionado(true);
            listaEmpregados.put(id,empregado);
    }
    public static void AlteraDefaultAtributoEmpregado(String id, String atributo, String valor) throws EmpregadoException {
        Empregado empregadoAtualizado = listaEmpregados.get(id);
        if (empregadoAtualizado == null) {
            throw new EmpregadoException("Empregado não encontrado");
        }
        if (atributo.equalsIgnoreCase("nome")) {
            empregadoAtualizado.setNome(valor);
        } else if (atributo.equalsIgnoreCase("endereco")) {
            empregadoAtualizado.setEndereco(valor);
        } else if (atributo.equalsIgnoreCase("tipo")) {
            empregadoAtualizado.setTipo(valor);
        } else if (atributo.equalsIgnoreCase("salario")) {
            empregadoAtualizado.setSalario(valor);
        }
        else if (atributo.equalsIgnoreCase("comissao"))
            empregadoAtualizado.setComissao(valor);
        else if (atributo.equalsIgnoreCase("metodoPagamento"))
            empregadoAtualizado.setMetodoDePagamento(valor);

        listaEmpregados.put(id, empregadoAtualizado);
    }
    public static void SindicalizaEmpregado(String id, String atributo, Boolean valor, String idSindical, String taxaSindical) throws SindicatoExceptions {
        alteraEmpregadoValidation(idSindical);
        Empregado empregado = listaEmpregados.get(id);
        empregado.SetisSindicalizado(valor);
        criaVinculoSindical(id,idSindical,taxaSindical);
        listaEmpregados.put(id,empregado);
    }


    //DELETE
    public void removerEmpregado(String id) throws EmpregadoException {
        validaRemocao(id);
        listaEmpregados.remove(id);
    }

}
