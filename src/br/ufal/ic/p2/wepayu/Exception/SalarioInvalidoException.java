package br.ufal.ic.p2.wepayu.Exception;

public class SalarioInvalidoException extends EmpregadoException{

    public SalarioInvalidoException(){
        super("Salario nao pode ser nulo.");
    }
}
