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

    private static void validaAtributo(String id, String atributoRequerido) throws EmpregadoException {
        if (atributoRequerido.equals("comissao")){
            if (!listaEmpregados.get(id).getTipo().equals("comissionado"))
                throw new EmpregadoException("Empregado nao eh comissionado.");
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
