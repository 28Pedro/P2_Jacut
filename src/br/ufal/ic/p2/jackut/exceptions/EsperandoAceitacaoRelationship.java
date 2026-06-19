package br.ufal.ic.p2.jackut.exceptions;

public class EsperandoAceitacaoRelationship extends Exception {

    public EsperandoAceitacaoRelationship() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
