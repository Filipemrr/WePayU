package br.ufal.ic.p2.wepayu.ModuleVendas.Service;
import br.ufal.ic.p2.wepayu.Core.Exceptions.*;
import br.ufal.ic.p2.wepayu.ModuleEmpregado.model.Empregado;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static br.ufal.ic.p2.wepayu.Sistema.listaDeVendedores;
import static br.ufal.ic.p2.wepayu.Sistema.listaEmpregados;

public class VendasValidations {
    public static void validaVenda(String id, String dataInicial, String dataFinal) throws Exception {
        validaId(id);
        validaEmpregado(id);
        validaTipoComissionado(id);
        validaDataInicialouFinal(dataInicial, "Data inicial");
        validaDataInicialouFinal(dataFinal, "Data final");
        februaryException(dataInicial, "Data inicial");
        februaryException(dataFinal, "Data final");
        isIntervaloValido(dataInicial,dataFinal);
    }
    public static void validalancamento(String id, String data, double valor) throws Exception {
        validaId(id);
        validaEmpregado(id);
        validaTipoComissionado(id);
        validaData(data);
        validaValor(valor);
    }

    public static void validaTipoComissionado(String id) throws Exception {
        Empregado empregado = listaEmpregados.get(id);
        if(!empregado.getTipo().equals("comissionado"))
            throw new EmpregadonaoEhComissionado("Empregado nao eh comissionado.");
    }
    private static void validaId(String id) throws Exception {
        if (id.isEmpty())
            throw new IDinvalido("Identificacao do empregado nao pode ser nula.");
    }
    private static void validaEmpregado(String id) throws Exception {
        if (!listaEmpregados.containsKey(id)) {
            throw new EmpregadoNaoExiste("Empregado nao existe.");
        }

        if (!listaDeVendedores.containsKey(id))
            throw new EmpregadonaoEhComissionado("Empregado nao eh comissionado.");
    }

    static void validaValor(double valor) throws Exception {
        if (valor <= 0)
            throw new ValorDeveSerNaoNegativo("Valor deve ser positivo.");
    }
    private static void validaData(String DataString) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(DataString, formatter);
            LocalDate dataMinima = LocalDate.of(1900, 1, 1);
            LocalDate dataMaxima = LocalDate.of(2100, 12, 31);

            if (data.isBefore(dataMinima) || data.isAfter(dataMaxima)) {
                throw new DataInvalida("Data inválida.");
            }
        } catch (DateTimeParseException e){
            throw new DataInvalida("Data invalida.");
        }
    }

    private static void validaDataInicialouFinal(String DataString, String tipoDaData) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(DataString, formatter);
            LocalDate dataMinima = LocalDate.of(1900, 1, 1);
            LocalDate dataMaxima = LocalDate.of(2100, 12, 31);

            if (data.isBefore(dataMinima) || data.isAfter(dataMaxima)) {
                throw new DataInvalida(tipoDaData + " invalida.");
            }
        } catch (DateTimeParseException e){
            throw new DataInvalida(tipoDaData + " invalida.");
        }

    }
    private static void februaryException(String Data, String tipoDaData) throws Exception {
        try {
            int diaDoMes = Integer.parseInt(Data.substring(0, Data.indexOf('/')));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate data = LocalDate.parse(Data, formatter);

            if (data.getMonth() == Month.FEBRUARY && ((data.isLeapYear() && diaDoMes > 29) || (!data.isLeapYear() && diaDoMes > 28))) {
                throw new DataInvalida(tipoDaData + " invalida.");
            }
        } catch (DateTimeParseException e) {
            throw new DataInvalida(tipoDaData + " invalida.");
        }
    }
    private static void isIntervaloValido(String DataInicial, String DataFinal) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dataInic = LocalDate.parse(DataInicial, formatter);
            LocalDate dataFinal = LocalDate.parse(DataFinal, formatter);
            if (dataInic.isAfter(dataFinal))
                throw new DataInvalida("Data inicial nao pode ser posterior aa data final.");

        } catch (DateTimeParseException e){
            throw new DataInvalida("Data inicial nao pode ser posterior aa data final.");
        }
    }
}
