package br.ufal.ic.p2.jackut.exceptions;

public class EsperandoAceitacaoAmigo extends Exception {
    public EsperandoAceitacaoAmigo() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}

