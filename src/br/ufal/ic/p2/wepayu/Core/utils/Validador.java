package br.ufal.ic.p2.wepayu.Core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufal.ic.p2.wepayu.Core.Exceptions.ParametroDeConsultaInvalido;
import br.ufal.ic.p2.wepayu.Core.Exceptions.ValorDeveSerNaoNegativo;
import br.ufal.ic.p2.wepayu.Core.Exceptions.ValorDeveSerNumerico;

public class Validador {

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumericString(String string) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    public static void qualquerStringNaoNumerica(String taxaSindical, String message) throws Exception {
        for (char c : taxaSindical.toCharArray()) {
            if (Character.isLetter(c)) {
                throw new ValorDeveSerNumerico(message + " deve ser numerica.");
            }
        }
        if (taxaSindical.charAt(0) == '-') {
            throw new ValorDeveSerNaoNegativo(message + " deve ser nao-negativa.");
        }
    }

    public static void qualquerStringNula(String id, String message) throws Exception {
        if (id.isEmpty())
            throw new ParametroDeConsultaInvalido(message + " nao pode ser nula.");
    }
}
