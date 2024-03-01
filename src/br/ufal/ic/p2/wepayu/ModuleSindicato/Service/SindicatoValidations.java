package br.ufal.ic.p2.wepayu.ModuleSindicato.Service;

import br.ufal.ic.p2.wepayu.Core.Exceptions.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static br.ufal.ic.p2.wepayu.Sistema.listaDeSindicalizados;
import static br.ufal.ic.p2.wepayu.Sistema.listaEmpregados;

public class SindicatoValidations {
    public static void lancamentoDeTaxaValidations(String id, String data, double taxa) throws Exception {
        validaID(id);
        validaMembro(id);
        validaTaxa(taxa);
        validaData(data);
    }
    public static void getTaxaValidations(String id, String DataInicial, String DataFinal) throws Exception {
        validaEmpregado(id);
        validaIsSindicalizado(id);
        //validacoes de data
        validaDataInicialouFinal(DataInicial, "Data inicial");
        validaDataInicialouFinal(DataFinal, "Data final");
        isIntervaloValido(DataInicial,DataFinal);
        februaryException(DataInicial,"Data inicial");
        februaryException(DataFinal, "Data final");
    }
    public static void alteraEmpregadoValidation(String idSindical) throws Exception {
        validaIdSindical(idSindical);
    }

    private static void validaID(String id) throws Exception {
        if (id.isEmpty())
            throw new IDinvalido("Identificacao do membro nao pode ser nula.");
    }
    private static void validaMembro(String id) throws  Exception{
        for (String KEY: listaDeSindicalizados.keySet()){
            if (Objects.equals(id, listaDeSindicalizados.get(KEY).getIdSindical()))
                return;
        }
        throw new EmpregadoNaoExiste("Membro nao existe.");
    }

    private static void validaEmpregado(String id) throws  Exception{
       for (String IdEmpregado : listaEmpregados.keySet()){
           if (!listaEmpregados.containsKey(IdEmpregado))
               throw new EmpregadoNaoExiste("Empregado Nao Existe");
       }

    }
    private static void validaIsSindicalizado(String id) throws Exception{
        if (!listaDeSindicalizados.containsKey(id))
            throw new EmpregadoNaoEhSindicalizado("Empregado nao eh sindicalizado.");

    }
    private static void validaTaxa(double taxaSindicato) throws Exception{
        if (taxaSindicato<=0)
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

    private static void validaIdSindical(String novoPossivelIdSindical) throws Exception {
        for (String key : listaDeSindicalizados.keySet()){
            if (Objects.equals(listaDeSindicalizados.get(key).getIdSindical(), novoPossivelIdSindical))
                throw new IDjaExiste("Ha outro empregado com esta identificacao de sindicato");
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
    private static void isIntervaloValido(String DataInicial, String DataFinal) throws DataInvalida {
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


}
