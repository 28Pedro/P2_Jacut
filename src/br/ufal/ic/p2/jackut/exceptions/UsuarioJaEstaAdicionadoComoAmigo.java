package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaEstaAdicionadoComoAmigo extends Exception {
    public UsuarioJaEstaAdicionadoComoAmigo() {
        super("Usuário já está adicionado como amigo.");
    }
}

