package br.ufal.ic.p2.wepayu.Vendas.Service;

import br.ufal.ic.p2.wepayu.CartaoDePonto.Model.ExceptionHoras;
import br.ufal.ic.p2.wepayu.Empregado.model.Empregado;
import br.ufal.ic.p2.wepayu.Vendas.Exception.ExceptionVendas;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static br.ufal.ic.p2.wepayu.Sistema.listaDeVendedores;
import static br.ufal.ic.p2.wepayu.Sistema.listaEmpregados;

public class VendasValidations {
    public static void validaVenda(String id, String dataInicial, String dataFinal) throws ExceptionVendas {
        validaId(id);
        validaEmpregado(id);
        validaTipoComissionado(id);
        validaDataInicialouFinal(dataInicial, "Data inicial");
        validaDataInicialouFinal(dataFinal, "Data final");
        februaryException(dataInicial, "Data inicial");
        februaryException(dataFinal, "Data final");
        isIntervaloValido(dataInicial,dataFinal);
    }
    public static void validalancamento(String id, String data, double valor) throws ExceptionVendas {
        validaId(id);
        validaEmpregado(id);
        validaTipoComissionado(id);
        validaData(data);
        validaValor(valor);
    }

    public static void validaTipoComissionado(String id) throws ExceptionVendas {
        Empregado empregado = listaEmpregados.get(id);
        if(!empregado.getTipo().equals("comissionado"))
            throw new ExceptionVendas("Empregado nao eh comissionado.");
    }
    private static void validaId(String id) throws ExceptionVendas {
        if (id.isEmpty())
            throw new ExceptionVendas("Identificacao do empregado nao pode ser nula.");
    }
    private static void validaEmpregado(String id) throws ExceptionVendas {
        if (!listaEmpregados.containsKey(id)) {
            throw new ExceptionVendas("Empregado nao existe.");
        }

        if (!listaDeVendedores.containsKey(id))
            throw new ExceptionVendas("Empregado nao eh comissionado.");
    }

    static void validaValor(double valor) throws ExceptionVendas {
        if (valor <= 0)
            throw new ExceptionVendas("Valor deve ser positivo.");
    }
    private static void validaData(String DataString) throws ExceptionVendas {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(DataString, formatter);
            LocalDate dataMinima = LocalDate.of(1900, 1, 1);
            LocalDate dataMaxima = LocalDate.of(2100, 12, 31);

            if (data.isBefore(dataMinima) || data.isAfter(dataMaxima)) {
                throw new ExceptionVendas("Data inválida.");
            }
        } catch (DateTimeParseException e){
            throw new ExceptionVendas("Data invalida.");
        }
    }

    private static void validaDataInicialouFinal(String DataString, String tipoDaData) throws ExceptionVendas {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(DataString, formatter);
            LocalDate dataMinima = LocalDate.of(1900, 1, 1);
            LocalDate dataMaxima = LocalDate.of(2100, 12, 31);

            if (data.isBefore(dataMinima) || data.isAfter(dataMaxima)) {
                throw new ExceptionVendas(tipoDaData + " invalida.");
            }
        } catch (DateTimeParseException e){
            throw new ExceptionVendas(tipoDaData + " invalida.");
        }

    }
    private static void februaryException(String Data, String tipoDaData) throws ExceptionVendas {
        try {
            int diaDoMes = Integer.parseInt(Data.substring(0, Data.indexOf('/')));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(Data, formatter);

            if (data.getMonth() == Month.FEBRUARY && ((data.isLeapYear() && diaDoMes > 29) || (!data.isLeapYear() && diaDoMes > 28))) {
                throw new ExceptionVendas(tipoDaData + " invalida.");
            }
        } catch (DateTimeParseException e) {
            throw new ExceptionVendas(tipoDaData + " invalida.");
        }
    }
    private static void isIntervaloValido(String DataInicial, String DataFinal) throws ExceptionVendas {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataInic = LocalDate.parse(DataInicial, formatter);
            LocalDate dataFinal = LocalDate.parse(DataFinal, formatter);
            if (dataInic.isAfter(dataFinal))
                throw new ExceptionVendas("Data inicial nao pode ser posterior aa data final.");

        } catch (DateTimeParseException e){
            throw new ExceptionVendas("Data inicial nao pode ser posterior aa data final.");
        }
    }
}
