package br.ufal.ic.p2.wepayu.Empregado.Service;

import br.ufal.ic.p2.wepayu.Empregado.Exception.EmpregadoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.ufal.ic.p2.wepayu.Sistema.listaEmpregados;

public class EmpregadoValidations {
    public static boolean isNumericString(String string){
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }
    public static void validarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException {
        validaNome(nome);
        validaEndereco(endereco);
        validaTipo(tipo);
        validaSalario(salario);
    }
    public static void validarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoException {
        validaNome(nome);
        validaEndereco(endereco);
        validaTipo(tipo);
        validaSalario(salario);
        validaComissao(comissao);
    }
    public static void validaGetAtributoEmpregado(String id, String atributoRequerido) throws EmpregadoException {
        validaAtributo(id,atributoRequerido);
    }
    public static void validaAtributosDefaultUpdate(String id, String atributo, String valor) throws EmpregadoException {

        if (atributo.equalsIgnoreCase("comissao"))
            validaValorComissao(valor);
        if(atributo.equalsIgnoreCase("salario"))
            validaSalario(valor);
        if (atributo.equalsIgnoreCase("sindicalizado")){
            if (!valor.equalsIgnoreCase("true") && !valor.equalsIgnoreCase("false"))
                throw new EmpregadoException("Valor deve ser true ou false.");
        }
        if (atributo.equalsIgnoreCase("tipo"))
            validaTipo(valor);

        validaId(id);
        validarExistenciaAtributo(atributo);
        validaValor(atributo,valor);


    }


    public static void validaBanco(String id, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws EmpregadoException {
        validaBancoNome(banco,agencia, contaCorrente);
    }
    public static void validaSindicalizacao(String id, String atributo, String valor, String idSindical, String taxaSindical) throws EmpregadoException {
        qualquerStringNula(idSindical, "Identificacao do sindicato");
        qualquerStringNula(taxaSindical,"Taxa sindical");
        qualquerStringNaoNumerica(taxaSindical,"Taxa sindical");
    }

    public static void qualquerStringNaoNumerica(String taxaSindical, String message) throws EmpregadoException {
        for (char c : taxaSindical.toCharArray()) {
            if (Character.isLetter(c)) {
                throw new EmpregadoException(message + " deve ser numerica.");
            }
        }
        if (taxaSindical.charAt(0) == '-') {
            throw new EmpregadoException(message+ " deve ser nao-negativa.");
        }
    }

    private static void validaBancoNome(String banco, String agencia, String contaCorrente) throws EmpregadoException {
        if (banco.isEmpty())
            throw new EmpregadoException("Banco nao pode ser nulo.");
        if (agencia.isEmpty())
            throw new EmpregadoException("Agencia nao pode ser nulo.");
        if (contaCorrente.isEmpty())
            throw new EmpregadoException("Conta corrente nao pode ser nulo.");
    }

    public static void qualquerStringNula(String id, String message) throws EmpregadoException {
        if (id.isEmpty())
            throw new EmpregadoException(message + " nao pode ser nula.");
    }
    public static void validaValorComissao(String valor) throws EmpregadoException {

        if (valor == null || valor.isEmpty()) {
            throw new EmpregadoException("Comissao nao pode ser nula.");
        }
        if (valor.charAt(0) == '-') {
            throw new EmpregadoException("Comissao deve ser nao-negativa.");
        }

        // Verifica se a comissão é um número
        for (char c : valor.toCharArray()) {
            if (!Character.isDigit(c) && c != ',') {
                throw new EmpregadoException("Comissao deve ser numerica.");
            }
        }
    }
    public static void validarExistenciaAtributo(String atributo) throws EmpregadoException {
        switch (atributo.toLowerCase()) {
            case "nome":
            case "endereco":
            case "tipo":
            case "salario":
            case "sindicalizado":
            case "comissao":
            case "metodopagamento":
            case "banco":
            case "agencia":
                break;
            default:
                throw new EmpregadoException("Atributo nao existe.");
        }
    }
    private static void validaAtributo(String id, String atributoRequerido) throws EmpregadoException {
        if (atributoRequerido.equals("comissao")){
            if (!listaEmpregados.get(id).getTipo().equals("comissionado"))
                throw new EmpregadoException("Empregado nao eh comissionado.");
        }
    }
    public static void validaValor(String atributo, String valor) throws EmpregadoException {
        if (valor == null || valor.isEmpty()) {
            String atributoCapitalizado = atributo.substring(0, 1).toUpperCase() + atributo.substring(1);
            throw new EmpregadoException(atributoCapitalizado + " nao pode ser nulo.");
        }
    }

    public static void validaRemocao(String id) throws EmpregadoException {
        validaId(id);
    }
    public static void validaNome(String nome) throws EmpregadoException {
        if (nome == null || nome.isEmpty()) {
            throw new EmpregadoException("Nome nao pode ser nulo.");
        }
    }
    public static void validaEndereco(String endereco) throws EmpregadoException {
        if (endereco == null || endereco.isEmpty()) {
            throw new EmpregadoException("Endereco nao pode ser nulo.");
        }
    }
    public static void validaTipo(String tipo) throws EmpregadoException {
        if (!tipo.equalsIgnoreCase("comissionado") && !tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado")){
            throw new EmpregadoException("Tipo invalido.");
        }
    }

    public static void validaSalario(String salario) throws EmpregadoException {
        if (salario.isEmpty())
            throw new EmpregadoException("Salario nao pode ser nulo.");

        if (isNumericString(salario))
            throw new EmpregadoException("Salario deve ser numerico.");

        if (salario.charAt(0) == '-') {
            throw new EmpregadoException("Salario deve ser nao-negativo.");
        }
    }
    public static void validaComissao(String comissao) throws EmpregadoException {
        if (comissao.isEmpty())
            throw new EmpregadoException("Comissao nao pode ser nula.");

        if(comissao.charAt(0) == '-')
            throw new EmpregadoException("Comissao deve ser nao-negativa.");

        if (isNumericString(comissao))
            throw new EmpregadoException("Comissao deve ser numerica.");
    }
    public static void validaId(String id) throws EmpregadoException {
        if (id.isEmpty())
            throw new EmpregadoException("Identificacao do empregado nao pode ser nula.");

        if (listaEmpregados.get(id) == null)
            throw new EmpregadoException("Empregado nao existe.");
    }
}
