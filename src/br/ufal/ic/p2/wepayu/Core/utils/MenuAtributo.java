package br.ufal.ic.p2.wepayu.Core.utils;

import static br.ufal.ic.p2.wepayu.ModuleEmpregado.Service.EmpregadoValidations.validaAtributosDefaultUpdate;
import static br.ufal.ic.p2.wepayu.ModuleEmpregado.Service.EmpregadoValidations.validaGetAtributoEmpregado;
import static br.ufal.ic.p2.wepayu.Sistema.*;

import br.ufal.ic.p2.wepayu.Core.Exceptions.AtributoInvalido;
import br.ufal.ic.p2.wepayu.Core.Exceptions.EmpregadoNaoEhSindicalizado;
import br.ufal.ic.p2.wepayu.Core.Exceptions.EmpregadoNaoExiste;
import br.ufal.ic.p2.wepayu.Core.Exceptions.EmpregadonaoEhComissionado;
import br.ufal.ic.p2.wepayu.Core.Exceptions.IDinvalido;
import br.ufal.ic.p2.wepayu.Core.Exceptions.MetodoDePagamentoInvalido;
import br.ufal.ic.p2.wepayu.ModuleEmpregado.model.Empregado;
import br.ufal.ic.p2.wepayu.ModuleSindicato.Classes.MembroSindicato;

public class MenuAtributo {

    public void AlteraDefaultAtributoEmpregado(String id, String atributo, String valor) throws Exception {
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
        } else if (atributo.equalsIgnoreCase("comissao")) {
            if (valor.isEmpty()) {
                throw new EmpregadonaoEhComissionado("Comissao nao pode ser nula.");
            } else {
                AlteraEmpregadoComissionado(id, atributo, valor);
            }
        }

        else if (atributo.equalsIgnoreCase("metodoPagamento")) {
            if (!valor.equalsIgnoreCase("correios") && !valor.equalsIgnoreCase("emMaos")
                    && !valor.equalsIgnoreCase("banco"))
                throw new MetodoDePagamentoInvalido("Metodo de pagamento invalido.");
            if (!valor.equalsIgnoreCase("banco"))
                empregadoAtualizado.setisRecebedorPorBanco(false);
            empregadoAtualizado.setMetodoDePagamento(valor);

        } else if (atributo.equalsIgnoreCase("sindicalizado")) {
            AlteraEmpregadoSindicato(id, atributo, valor);
        }

        listaEmpregados.put(id, empregadoAtualizado);
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
            if (Validador.isNumeric(salarioStr)) {
                double salario = Double.parseDouble(salarioStr);
                if (salario % 1 == 0) { // se for inteiro
                    return String.format("%.0f,00", salario);
                } else { // se for decimal
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
            if (!empregado.getIsSindicalizado())
                throw new EmpregadoNaoEhSindicalizado("Empregado nao eh sindicalizado.");

            MembroSindicato sindicalista = listaDeSindicalizados.get(id);
            if (atributo.equals("idSindicato"))
                return sindicalista.getIdSindical();
            if (atributo.equals("taxaSindical"))
                return sindicalista.getTaxaSindical();
        }

        if (atributo.equalsIgnoreCase("banco") || atributo.equalsIgnoreCase("agencia")
                || atributo.equalsIgnoreCase("contaCorrente") || atributo.equalsIgnoreCase("valor"))
            if (!empregado.isRecebedorPorBanco())
                throw new MetodoDePagamentoInvalido("Empregado nao recebe em banco.");
            else
                return empregado.getInformacaoBancaria(atributo);

        throw new AtributoInvalido("Atributo nao existe.");
    }

    private static void AlteraEmpregadoComissionado(String id, String atributo, String valor) throws Exception {
        Empregado empregado = listaEmpregados.get(id);
        if (empregado.getTipo().equals("comissionado")) {
            empregado.setComissao(valor);
            listaEmpregados.put(id, empregado);
        } else
            throw new EmpregadonaoEhComissionado("Empregado nao eh comissionado.");
    }

    private static void AlteraEmpregadoSindicato(String id, String atributo, String valor) throws Exception {
        Empregado empregado = listaEmpregados.get(id);
        if (valor.equalsIgnoreCase("false"))
            empregado.SetisSindicalizado(false);

        if (valor.equalsIgnoreCase("true"))
            empregado.SetisSindicalizado(true);

        if (!empregado.getTipo().equalsIgnoreCase("comissionado"))
            throw new EmpregadoNaoEhSindicalizado("Empregado nao eh sindicalizado.");
    }

}
