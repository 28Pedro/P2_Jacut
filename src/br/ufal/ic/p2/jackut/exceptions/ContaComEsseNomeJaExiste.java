package br.ufal.ic.p2.jackut.exceptions;

public class ContaComEsseNomeJaExiste extends Exception {
    public ContaComEsseNomeJaExiste() {
        super("Conta com esse nome já existe.");
    }
}

