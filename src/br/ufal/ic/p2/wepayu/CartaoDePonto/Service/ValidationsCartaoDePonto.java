package br.ufal.ic.p2.wepayu.CartaoDePonto.Service;

import br.ufal.ic.p2.wepayu.CartaoDePonto.Model.ExceptionHoras;
import br.ufal.ic.p2.wepayu.Empregado.model.Empregado;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static br.ufal.ic.p2.wepayu.Sistema.listaEmpregados;

public class ValidationsCartaoDePonto {

    public static void validaCartaoLancamento(String id, String data, String horas) throws ExceptionHoras {
        validaID(id);
        validaEmpregado(id);
        validaTipoHorista(id);
        validaData(data);
        validaHoras(horas);
    }
    public static void validaResgateNoCartao(String id, String dataInicial, String dataFinal) throws ExceptionHoras {
        validaTipoHorista(id);
        validaDataInicialouFinal(dataInicial, "Data inicial");
        validaDataInicialouFinal(dataFinal, "Data final");
        isIntervaloValido(dataInicial,dataFinal);
        februaryException(dataInicial,"Data inicial");
        februaryException(dataFinal, "Data final");
    }


    private static void validaHoras(String Horas) throws ExceptionHoras {
        if (Horas.equals("0") || Horas.startsWith("-"))
           throw new ExceptionHoras("Horas devem ser positivas.");
    }

    static void validaID(String id) throws ExceptionHoras {
        if (id.isEmpty()){
            throw new ExceptionHoras("Identificacao do empregado nao pode ser nula.");
        }
    }
    private static void validaEmpregado(String id) throws ExceptionHoras {
        if (listaEmpregados.get(id) == null)
            throw new ExceptionHoras("Empregado nao existe.");
    }

    public static void validaTipoHorista(String id) throws ExceptionHoras {
        Empregado empregado = listaEmpregados.get(id);
        if(!empregado.getTipo().equals("horista"))
            throw new ExceptionHoras("Empregado nao eh horista.");
    }

    private static void validaData(String DataString) throws ExceptionHoras {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(DataString, formatter);
            LocalDate dataMinima = LocalDate.of(1900, 1, 1);
            LocalDate dataMaxima = LocalDate.of(2100, 12, 31);

            if (data.isBefore(dataMinima) || data.isAfter(dataMaxima)) {
                throw new ExceptionHoras("Data inválida.");
            }
        } catch (DateTimeParseException e){
            throw new ExceptionHoras("Data invalida.");
        }
    }
    private static void validaDataInicialouFinal(String DataString, String tipoDaData) throws ExceptionHoras {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(DataString, formatter);
            LocalDate dataMinima = LocalDate.of(1900, 1, 1);
            LocalDate dataMaxima = LocalDate.of(2100, 12, 31);

            if (data.isBefore(dataMinima) || data.isAfter(dataMaxima)) {
                throw new ExceptionHoras(tipoDaData + " invalida.");
            }
        } catch (DateTimeParseException e){
            throw new ExceptionHoras(tipoDaData + " invalida.");
        }

    }

    private static void isIntervaloValido(String DataInicial, String DataFinal) throws ExceptionHoras {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataInic = LocalDate.parse(DataInicial, formatter);
            LocalDate dataFinal = LocalDate.parse(DataFinal, formatter);
            if (dataInic.isAfter(dataFinal))
                throw new ExceptionHoras("Data inicial nao pode ser posterior aa data final.");

        } catch (DateTimeParseException e){
            throw new ExceptionHoras("Data inicial nao pode ser posterior aa data final.");
        }
    }

    private static void februaryException(String Data, String tipoDaData) throws ExceptionHoras {
        try {
            int diaDoMes = Integer.parseInt(Data.substring(0, Data.indexOf('/')));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(Data, formatter);

            if (data.getMonth() == Month.FEBRUARY && ((data.isLeapYear() && diaDoMes > 29) || (!data.isLeapYear() && diaDoMes > 28))) {
                throw new ExceptionHoras(tipoDaData + " invalida.");
            }
        } catch (DateTimeParseException e) {
            throw new ExceptionHoras(tipoDaData + " invalida.");
        }
    }
}
