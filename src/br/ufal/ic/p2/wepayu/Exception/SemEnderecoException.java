package br.ufal.ic.p2.wepayu.Exception;

public class SemEnderecoException extends EmpregadoException {
    public SemEnderecoException(){
        super("Endereco nao pode ser nulo.");
    }
}
