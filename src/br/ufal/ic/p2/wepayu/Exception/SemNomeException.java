package br.ufal.ic.p2.wepayu.Exception;

public class SemNomeException extends EmpregadoException {
    public SemNomeException() {
        super("Nome nao pode ser nulo.");
    }
}
