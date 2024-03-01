package br.ufal.ic.p2.wepayu.Empregado.Service;

import br.ufal.ic.p2.wepayu.Empregado.Exception.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.ufal.ic.p2.wepayu.Sistema.listaEmpregados;

public class EmpregadoValidations {
    public static boolean isNumericString(String string){
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }
    public static void validarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception {
        validaNome(nome);
        validaEndereco(endereco);
        validaTipo(tipo);
        validaSalario(salario);
    }
    public static void validarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
        validaNome(nome);
        validaEndereco(endereco);
        validaTipo(tipo);
        validaSalario(salario);
        validaComissao(comissao);
    }
    public static void validaGetAtributoEmpregado(String id, String atributoRequerido) throws Exception {
        validaAtributo(id,atributoRequerido);
    }
    public static void validaAtributosDefaultUpdate(String id, String atributo, String valor) throws Exception {

        if (atributo.equalsIgnoreCase("comissao"))
            validaValorComissao(valor);
        if(atributo.equalsIgnoreCase("salario"))
            validaSalario(valor);
        if (atributo.equalsIgnoreCase("sindicalizado")){
            if (!valor.equalsIgnoreCase("true") && !valor.equalsIgnoreCase("false"))
                throw new ValorDeveSerBooleano("Valor deve ser true ou false.");
        }
        if (atributo.equalsIgnoreCase("tipo"))
            validaTipo(valor);

        validaId(id);
        validarExistenciaAtributo(atributo);
        validaValor(atributo,valor);


    }


    public static void validaBanco(String id, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws Exception {
        validaBancoNome(banco,agencia, contaCorrente);
    }
    public static void validaSindicalizacao(String id, String atributo, String valor, String idSindical, String taxaSindical) throws Exception {
        qualquerStringNula(idSindical, "Identificacao do sindicato");
        qualquerStringNula(taxaSindical,"Taxa sindical");
        qualquerStringNaoNumerica(taxaSindical,"Taxa sindical");
    }

    public static void qualquerStringNaoNumerica(String taxaSindical, String message) throws Exception {
        for (char c : taxaSindical.toCharArray()) {
            if (Character.isLetter(c)) {
                throw new ValorDeveSerNumerico(message + " deve ser numerica.");
            }
        }
        if (taxaSindical.charAt(0) == '-') {
            throw new ValorDeveSerNaoNegativo(message+ " deve ser nao-negativa.");
        }
    }

    private static void validaBancoNome(String banco, String agencia, String contaCorrente) throws Exception {
        if (banco.isEmpty())
            throw new ParametroDeConsultaInvalido("Banco nao pode ser nulo.");
        if (agencia.isEmpty())
            throw new ParametroDeConsultaInvalido("Agencia nao pode ser nulo.");
        if (contaCorrente.isEmpty())
            throw new ParametroDeConsultaInvalido("Conta corrente nao pode ser nulo.");
    }

    public static void qualquerStringNula(String id, String message) throws Exception {
        if (id.isEmpty())
            throw new ParametroDeConsultaInvalido(message + " nao pode ser nula.");
    }
    public static void validaValorComissao(String valor) throws Exception {

        if (valor == null || valor.isEmpty()) {
            throw new ParametroDeConsultaInvalido("Comissao nao pode ser nula.");
        }
        if (valor.charAt(0) == '-') {
            throw new ParametroDeConsultaInvalido("Comissao deve ser nao-negativa.");
        }

        // Verifica se a comissão é um número
        for (char c : valor.toCharArray()) {
            if (!Character.isDigit(c) && c != ',') {
                throw new ValorDeveSerNumerico("Comissao deve ser numerica.");
            }
        }
    }
    public static void validarExistenciaAtributo(String atributo) throws Exception {
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
                throw new AtributoInvalido("Atributo nao existe.");
        }
    }
    private static void validaAtributo(String id, String atributoRequerido) throws Exception {
        if (atributoRequerido.equals("comissao")){
            if (!listaEmpregados.get(id).getTipo().equals("comissionado"))
                throw new EmpregadonaoEhComissionado("Empregado nao eh comissionado.");
        }
    }
    public static void validaValor(String atributo, String valor) throws Exception {
        if (valor == null || valor.isEmpty()) {
            String atributoCapitalizado = atributo.substring(0, 1).toUpperCase() + atributo.substring(1);
            throw new ValorDeveSerNaoNegativo(atributoCapitalizado + " nao pode ser nulo.");
        }
    }

    public static void validaRemocao(String id) throws Exception {
        validaId(id);
    }
    public static void validaNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalido("Nome nao pode ser nulo.");
        }
    }
    public static void validaEndereco(String endereco) throws Exception {
        if (endereco == null || endereco.isEmpty()) {
            throw new EnderecoInvalido("Endereco nao pode ser nulo.");
        }
    }
    public static void validaTipo(String tipo) throws Exception {
        if (!tipo.equalsIgnoreCase("comissionado") && !tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado")){
            throw new TipoInvalido("Tipo invalido.");
        }
    }

    public static void validaSalario(String salario) throws Exception {
        if (salario.isEmpty())
            throw new SalarioInvalido("Salario nao pode ser nulo.");

        if (isNumericString(salario))
            throw new SalarioInvalido("Salario deve ser numerico.");

        if (salario.charAt(0) == '-') {
            throw new SalarioInvalido("Salario deve ser nao-negativo.");
        }
    }
    public static void validaComissao(String comissao) throws Exception {
        if (comissao.isEmpty())
            throw new ComissaoInvalido("Comissao nao pode ser nula.");

        if(comissao.charAt(0) == '-')
            throw new ComissaoInvalido("Comissao deve ser nao-negativa.");

        if (isNumericString(comissao))
            throw new ComissaoInvalido("Comissao deve ser numerica.");
    }
    public static void validaId(String id) throws Exception {
        if (id.isEmpty())
            throw new IDinvalido("Identificacao do empregado nao pode ser nula.");

        if (listaEmpregados.get(id) == null)
            throw new EmpregadoNaoExiste("Empregado nao existe.");
    }
}
