package br.ufal.ic.p2.jackut.Exeptions;

public class ContaComEsseNomeJaExiste extends Exception {
    public ContaComEsseNomeJaExiste() {
        super("Conta com esse nome já existe.");
    }
}

