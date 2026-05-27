package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaAdicionadoComoAmigoEsperandoAceitacao extends Exception {
    public UsuarioJaEstaAdicionadoComoAmigoEsperandoAceitacao() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}

