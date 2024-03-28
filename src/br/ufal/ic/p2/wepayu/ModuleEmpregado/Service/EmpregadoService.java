package br.ufal.ic.p2.wepayu.ModuleEmpregado.Service;

import br.ufal.ic.p2.wepayu.Core.Exceptions.*;
import br.ufal.ic.p2.wepayu.Core.utils.GeradorId;
import br.ufal.ic.p2.wepayu.Core.utils.MenuAtributo;
import br.ufal.ic.p2.wepayu.ModuleEmpregado.model.Empregado;
import br.ufal.ic.p2.wepayu.ModuleEmpregado.model.InformacoesBancarias;

import static br.ufal.ic.p2.wepayu.ModuleCartaoDePonto.Service.ServiceCartaoDePonto.CriaCartao;
import static br.ufal.ic.p2.wepayu.ModuleEmpregado.Service.EmpregadoValidations.*;
import static br.ufal.ic.p2.wepayu.ModuleSindicato.Service.SindicatoService.criaVinculoSindical;
import static br.ufal.ic.p2.wepayu.ModuleSindicato.Service.SindicatoValidations.alteraEmpregadoValidation;
import static br.ufal.ic.p2.wepayu.Sistema.*;
import static br.ufal.ic.p2.wepayu.ModuleVendas.Service.VendasService.criaCartaoDeVendas;

public class EmpregadoService {

    GeradorId geradorId;

    public EmpregadoService() {
        geradorId = new GeradorId();
    }

    // Create
    public String AddEmpregado(String nome, String endereco, String tipo, String salario) throws Exception {

        if (tipo.equalsIgnoreCase("comissionado"))
            throw new TipoInvalido("Tipo nao aplicavel.");
        EmpregadoValidations.validarEmpregado(nome, endereco, tipo, salario);
        String id = geradorId.GerarId();
        if (tipo.equalsIgnoreCase("horista"))
            CriaCartao(id);
        Empregado Empregado = new Empregado(id, nome, endereco, tipo, salario);
        listaEmpregados.put(id, Empregado);
        return id;
    }

    public String AddEmpregado(String nome, String endereco, String tipo, String salario, String ValorComissao)
            throws Exception {
        if (!tipo.equalsIgnoreCase("comissionado"))
            throw new TipoInvalido("Tipo nao aplicavel.");

        EmpregadoValidations.validarEmpregado(nome, endereco, tipo, salario, ValorComissao);
        String id = geradorId.GerarId();
        criaCartaoDeVendas(id);
        Empregado Empregado = new Empregado(id, nome, endereco, tipo, salario, ValorComissao);
        listaEmpregados.put(id, Empregado);
        return id;
    }

    // Read
    public String GetEmpregadoPorNome(String nome, int indice) throws Exception {
        int i = 0;
        for (Empregado empregado : listaEmpregados.values()) {
            if (empregado.getNome().equals(nome))
                i++;

            if (indice == i) {
                return empregado.getId();
            }
        }
        throw new NomeInvalido("Nao ha empregado com esse nome.");
    }

    public String GetAtributoEmpregado(String id, String atributo) throws Exception {

        MenuAtributo menuAtributo = new MenuAtributo();
        return menuAtributo.GetAtributoEmpregado(id, atributo);

    }

    // UPDATE
    public static void AlteraDefaultAtributoEmpregado(String id, String atributo, String valor) throws Exception {

        MenuAtributo menuAtributo = new MenuAtributo();
        menuAtributo.AlteraDefaultAtributoEmpregado(id, atributo, valor);

    }

    public static void AlteraMetodoPagamentoEmpregado(String id, String atributo, String valor1, String banco,
            String agencia, String contaCorrente) throws Exception {
        validaBanco(id, atributo, valor1, banco, agencia, contaCorrente);
        Empregado empregadoAtualizado = listaEmpregados.get(id);
        InformacoesBancarias infosBancariasEmpregado = new InformacoesBancarias(valor1, banco, agencia, contaCorrente);
        empregadoAtualizado.SetInformacoesBancarias(infosBancariasEmpregado);
        empregadoAtualizado.setMetodoDePagamento("banco");
        empregadoAtualizado.setisRecebedorPorBanco(true);
        listaEmpregados.put(id, empregadoAtualizado);
    }

    public static void ComissionaEmpregado(String id, String atributo, String valor, String comissao) throws Exception {
        Empregado empregado = listaEmpregados.get(id);
        if (valor.equalsIgnoreCase("comissionado")) {
            empregado.setTipo("comissionado");
            empregado.setComissao(comissao);
            listaEmpregados.put(id, empregado);
        }
        if (valor.equalsIgnoreCase("horista")) {
            empregado.setTipo("horista");
            empregado.setSalario(comissao);
        }
    }

    public static void SindicalizaEmpregado(String id, String atributo, String valor, String idSindical,
            String taxaSindical) throws Exception {
        validaSindicalizacao(id, atributo, valor, idSindical, taxaSindical);
        alteraEmpregadoValidation(idSindical);
        Empregado empregado = listaEmpregados.get(id);
        empregado.SetisSindicalizado(true);
        criaVinculoSindical(id, idSindical, taxaSindical);
        listaEmpregados.put(id, empregado);
    }

    // DELETE
    public void removerEmpregado(String id) throws Exception {
        validaRemocao(id);
        listaEmpregados.remove(id);
    }
}
