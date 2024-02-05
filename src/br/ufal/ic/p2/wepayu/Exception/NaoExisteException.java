package br.ufal.ic.p2.wepayu.Exception;

public class NaoExisteException extends EmpregadoException{
    public NaoExisteException(){
        super("Empregado nao existe.");
    }

}
