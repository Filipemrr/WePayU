package br.ufal.ic.p2.wepayu.Vendas.Service;


import br.ufal.ic.p2.wepayu.Vendas.Exception.ExceptionVendas;
import br.ufal.ic.p2.wepayu.Vendas.Model.Vendas;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static br.ufal.ic.p2.wepayu.Sistema.listaDeVendedores;
import static br.ufal.ic.p2.wepayu.Vendas.Service.VendasValidations.*;

public class VendasService {

    public static void criaCartaoDeVendas(String id){
            Vendas NovoCartaoDeVendas = new Vendas(id);
            listaDeVendedores.put(id,NovoCartaoDeVendas);
        }
    public static void LancaVenda(String id, String data, String Valor) throws ExceptionVendas {
        String valorString = Valor.replace(',', '.');
        double valorDaVenda = Double.parseDouble(valorString);
        validalancamento(id, data, valorDaVenda);
        Vendas cartaoDeVenda = listaDeVendedores.get(id);
        cartaoDeVenda.cadastrarNovaVenda(data,valorDaVenda);
    }
    public static String GetVendasRealizadas(String id, String DataInicial, String DataFinal) throws ExceptionVendas {
        validaVenda(id,DataInicial,DataFinal);
        Vendas EmpregadoVendendor = listaDeVendedores.get(id);
        double montanteDeVendas = 0.0;
        LocalDate inicio = LocalDate.parse(DataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate fim = LocalDate.parse(DataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));

        for (String data : EmpregadoVendendor.VendaDoEmpregado.keySet()) {
            LocalDate dataFormatted = LocalDate.parse(data, DateTimeFormatter.ofPattern("d/M/yyyy"));
            if ((dataFormatted.isAfter(inicio) && dataFormatted.isBefore(fim)) || dataFormatted.isEqual(inicio) && !dataFormatted.isEqual(fim)) {
                double ValorDaVenda = EmpregadoVendendor.obterValorVenda(data);
                validaValor(ValorDaVenda);
                montanteDeVendas += ValorDaVenda;
            }
        }

        return String.format("%.2f", montanteDeVendas).replace('.', ',');
    }

    }
