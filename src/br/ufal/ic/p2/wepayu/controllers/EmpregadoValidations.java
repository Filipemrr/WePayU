package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.ufal.ic.p2.wepayu.controllers.EmpregadoService.isNumeric;

public class EmpregadoValidations {
    public static void validarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoException {
        validaNome(nome);
        validaEndereco(endereco);
        validaTipo(tipo);
        validaSalario(salario);
    }
    public static void validaNome(String nome) throws EmpregadoException {
        if (nome == null || nome.isEmpty()) {
            throw new SemNomeException();
        }
    }
    public static void validaEndereco(String endereco) throws EmpregadoException {
        if (endereco == null || endereco.isEmpty()) {
            throw new SemEnderecoException();
        }
    }
    public static void validaTipo(String tipo) throws EmpregadoException {
        if (!tipo.equalsIgnoreCase("comissionado") && !tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado")){
            throw new TipoInvalidoExcepcion();
        }
    }

    public static void validaSalario(String salario) throws EmpregadoException {
        if (salario.isEmpty())
            throw new SalarioInvalidoException();

        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(salario);
        if (matcher.find())
            throw new SalarioNaoNumerico();


    }

}
