package br.ufal.ic.p2.wepayu.ModuleCartaoDePonto.Service;
import br.ufal.ic.p2.wepayu.ModuleCartaoDePonto.Classes.CartaoDePonto;
import br.ufal.ic.p2.wepayu.ModuleCartaoDePonto.Classes.Horas;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static br.ufal.ic.p2.wepayu.ModuleCartaoDePonto.Service.ValidationsCartaoDePonto.*;
import static br.ufal.ic.p2.wepayu.Sistema.listaDeCartoes;

public class ServiceCartaoDePonto {

    public static void CriaCartao(String id){
        CartaoDePonto NovoCartao = new CartaoDePonto(id);
        listaDeCartoes.put(id,NovoCartao);
    }

    public static void LancaCartao(String id, String data, String horasString) throws Exception {
        validaCartaoLancamento(id,data, horasString);
        // Substitui vírgulas por pontos na string de horas
        horasString = horasString.replace(',', '.');


        double horas = Double.parseDouble(horasString);

        double horasExtras = 0;
        double horasNormais = horas;
        CartaoDePonto cartao = listaDeCartoes.get(id);
        if (horas > 8) {
            horasNormais = 8;
            horasExtras = horas - horasNormais;
        }
        Horas horasDoDia = new Horas(horasNormais, horasExtras);
        cartao.DataeHoras.put(data, horasDoDia);
    }


    public static String GetHorasNormaisTrabalhadas(String id, String dataInicial, String dataFinal) throws Exception {
        validaResgateNoCartao(id, dataInicial, dataFinal);
        CartaoDePonto empregado = listaDeCartoes.get(id);

        double horasNormaisTrabalhadas = 0.0;
        LocalDate inicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate fim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));

        for (String data : empregado.DataeHoras.keySet()) {
            LocalDate dataFormatted = LocalDate.parse(data, DateTimeFormatter.ofPattern("d/M/yyyy"));
            if ((dataFormatted.isAfter(inicio) && dataFormatted.isBefore(fim)) || dataFormatted.isEqual(inicio) && !dataFormatted.isEqual(fim)) {
                Horas horasDoDia = empregado.DataeHoras.get(data);
                double horasNormais = horasDoDia.getHorasNormais();
                horasNormaisTrabalhadas += horasNormais;
            }
        }
        return formatarHoras(horasNormaisTrabalhadas);
    }
    public static String GetHorasExtrasTrabalhadas(String id, String dataInicial, String dataFinal) {
        CartaoDePonto empregado = listaDeCartoes.get(id);
        double horasExtrasTrabalhadas = 0.0;

        LocalDate inicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate fim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));

        for (String data : empregado.DataeHoras.keySet()) {
            LocalDate dataFormatted = LocalDate.parse(data, DateTimeFormatter.ofPattern("d/M/yyyy"));
            if ((dataFormatted.isAfter(inicio) && dataFormatted.isBefore(fim)) || dataFormatted.isEqual(inicio) && !dataFormatted.isEqual(fim)) {
                Horas horasDoDia = empregado.DataeHoras.get(data);
                double horasExtras = horasDoDia.getHorasExcedentes();
                horasExtrasTrabalhadas += horasExtras;
            }
        }
        return formatarHoras(horasExtrasTrabalhadas);
    }

    private static String formatarHoras(double horas) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("0.#", symbols);
        return decimalFormat.format(horas);
    }
}

