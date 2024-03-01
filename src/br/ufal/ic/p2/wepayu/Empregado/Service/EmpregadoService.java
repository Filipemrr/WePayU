package br.ufal.ic.p2.wepayu.Empregado.Service;
import br.ufal.ic.p2.wepayu.CartaoDePonto.Model.ExceptionHoras;
import br.ufal.ic.p2.wepayu.Empregado.Exception.*;
import br.ufal.ic.p2.wepayu.Empregado.model.Empregado;
import br.ufal.ic.p2.wepayu.Empregado.model.InformacoesBancarias;
import br.ufal.ic.p2.wepayu.Sindicato.Classes.MembroSindicato;
import br.ufal.ic.p2.wepayu.Sindicato.Exception.SindicatoExceptions;
import br.ufal.ic.p2.wepayu.Sistema;

import java.rmi.server.ExportException;
import java.util.UUID;

import static br.ufal.ic.p2.wepayu.CartaoDePonto.Service.ServiceCartaoDePonto.CriaCartao;
import static br.ufal.ic.p2.wepayu.Empregado.Service.EmpregadoValidations.*;
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
    public String AddEmpregado(String nome, String endereco, String tipo, String salario) throws Exception {
        if (tipo.equalsIgnoreCase("comissionado"))
            throw new TipoInvalido("Tipo nao aplicavel.");
        EmpregadoValidations.validarEmpregado(nome,endereco, tipo,salario);
        String id = GerarId();
        if (tipo.equalsIgnoreCase("horista"))
            CriaCartao(id);
        Empregado Empregado = new Empregado(id,nome,endereco,tipo,salario);
        listaEmpregados.put(id,Empregado);
        return id;
    }
    public String AddEmpregado(String nome, String endereco, String tipo, String salario, String ValorComissao) throws  Exception {
        if (!tipo.equalsIgnoreCase("comissionado"))
            throw new TipoInvalido("Tipo nao aplicavel.");

        EmpregadoValidations.validarEmpregado(nome,endereco, tipo,salario,ValorComissao);
        String id = GerarId();
        criaCartaoDeVendas(id);
        Empregado Empregado = new Empregado(id,nome,endereco,tipo,salario, ValorComissao);
        listaEmpregados.put(id,Empregado);
        return id;
    }

    //Read
    public String GetEmpregadoPorNome(String nome, int indice) throws Exception {
        int i = 0;
        for (Empregado empregado : listaEmpregados.values()) {
            if (empregado.getNome().equals(nome))
                i++;

            if (indice == i) {
                return empregado.getId();
            }
        }
        throw new NomeInvalido("Nao ha empregado com esse nome.");
    }
    public String GetAtributoEmpregado(String id, String atributo) throws Exception {
        validaGetAtributoEmpregado(id, atributo);
        Empregado empregado = listaEmpregados.get(id);

        if (id.isEmpty())
            throw new IDinvalido("Identificacao do empregado nao pode ser nula.");

        if (empregado == null)
            throw new EmpregadoNaoExiste("Empregado nao existe.");

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
            if (empregado.getIsSindicalizado())
                return "true";
            else
                return "false";
        }

        if (atributo.equals("comissao")) {
            if (empregado.getTipo().equalsIgnoreCase("comissionado"))
                return empregado.getComissao();

            throw new EmpregadonaoEhComissionado("Empregado nao eh comissionado.");
        }

        if (atributo.equals("metodoPagamento")) {
           return empregado.getMetodoDePagamento();
        }

        if (atributo.equalsIgnoreCase("idSindicato") || atributo.equalsIgnoreCase("taxaSindical")) {
            if(!empregado.getIsSindicalizado())
                throw new EmpregadoNaoEhSindicalizado("Empregado nao eh sindicalizado.");

            MembroSindicato sindicalista = listaDeSindicalizados.get(id);
            if (atributo.equals("idSindicato")) return sindicalista.getIdSindical();
            if (atributo.equals("taxaSindical")) return sindicalista.getTaxaSindical();
        }

        if (atributo.equalsIgnoreCase("banco") || atributo.equalsIgnoreCase("agencia") || atributo.equalsIgnoreCase("contaCorrente") || atributo.equalsIgnoreCase("valor"))
            if (!empregado.isRecebedorPorBanco())
                throw new MetodoDePagamentoInvalido("Empregado nao recebe em banco.");
            else
                return empregado.getInformacaoBancaria(atributo);

        throw new AtributoInvalido("Atributo nao existe.");
    }

    //UPDATE
    public static void AlteraDefaultAtributoEmpregado(String id, String atributo, String valor) throws Exception {
        validaAtributosDefaultUpdate(id, atributo, valor);
        Empregado empregadoAtualizado = listaEmpregados.get(id);
        if (empregadoAtualizado == null) {
            throw new EmpregadoNaoExiste("Empregado não encontrado");
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
        else if (atributo.equalsIgnoreCase("comissao")){
            if (valor.equalsIgnoreCase("true")) {
                empregadoAtualizado.setComissao(valor);
                empregadoAtualizado.setIsComissionado(true);
            }
        }

        else if (atributo.equalsIgnoreCase("metodoPagamento")){
            if (!valor.equalsIgnoreCase("correios") && !valor.equalsIgnoreCase("emMaos") && !valor.equalsIgnoreCase("banco"))
                throw new MetodoDePagamentoInvalido("Metodo de pagamento invalido.");
            if (!valor.equalsIgnoreCase("banco"))
                empregadoAtualizado.setisRecebedorPorBanco(false);
            empregadoAtualizado.setMetodoDePagamento(valor);

        }
        else if (atributo.equalsIgnoreCase("sindicalizado")){
            if (valor.equalsIgnoreCase("true"))
                empregadoAtualizado.SetisSindicalizado(true);
            if (valor.equalsIgnoreCase("false"))
                empregadoAtualizado.SetisSindicalizado(false);
            else
                throw new ValorInvalido("Valor invalido");
        }


        listaEmpregados.put(id, empregadoAtualizado);
    }
    public static void AlteraMetodoPagamentoEmpregado(String id, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws Exception {
        validaBanco(id,atributo,valor1,banco,agencia,contaCorrente);
        Empregado empregadoAtualizado = listaEmpregados.get(id);
        InformacoesBancarias infosBancariasEmpregado = new InformacoesBancarias(valor1,banco,agencia,contaCorrente);
        empregadoAtualizado.SetInformacoesBancarias(infosBancariasEmpregado);
        empregadoAtualizado.setMetodoDePagamento("banco");
        empregadoAtualizado.setisRecebedorPorBanco(true);
        listaEmpregados.put(id,empregadoAtualizado);
    }
    public static void AlteraEmpregadoComissionado(String id, String atributo, String valor) throws Exception {
        Empregado empregado = listaEmpregados.get(id);
        if (empregado.getTipo().equals("comissionado")){
            empregado.setComissao(valor);
            listaEmpregados.put(id,empregado);
        }
        else
            throw new EmpregadonaoEhComissionado("Empregado nao eh comissionado.");
    }
    public static void AlteraEmpregadoSindicato(String id, String atributo, String valor) throws Exception {
        Empregado empregado = listaEmpregados.get(id);
        if (valor.equalsIgnoreCase("false"))
            empregado.SetisSindicalizado(false);

        if (valor.equalsIgnoreCase("true"))
            empregado.SetisSindicalizado(true);

        if (!empregado.getTipo().equalsIgnoreCase("comissionado"))
            throw new EmpregadoNaoEhSindicalizado("Empregado nao eh sindicalizado.");
    }
    public static void ComissionaEmpregado(String id, String atributo, String valor,String comissao) throws  Exception{
        Empregado empregado = listaEmpregados.get(id);
        if (valor.equalsIgnoreCase("comissionado")){
                empregado.setTipo("comissionado");
                empregado.setComissao(comissao);
                listaEmpregados.put(id,empregado);
        }
            if (valor.equalsIgnoreCase("horista")){
                empregado.setTipo("horista");
                empregado.setSalario(comissao);
            }
    }

    public static void SindicalizaEmpregado(String id, String atributo, String valor, String idSindical, String taxaSindical) throws Exception {
        validaSindicalizacao(id,atributo,valor,idSindical,taxaSindical);
        alteraEmpregadoValidation(idSindical);
        Empregado empregado = listaEmpregados.get(id);
        empregado.SetisSindicalizado(true);
        criaVinculoSindical(id,idSindical,taxaSindical);
        listaEmpregados.put(id,empregado);
    }


    //DELETE
    public void removerEmpregado(String id) throws Exception {
        validaRemocao(id);
        listaEmpregados.remove(id);
    }

}
