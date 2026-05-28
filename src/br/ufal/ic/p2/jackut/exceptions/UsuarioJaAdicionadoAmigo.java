package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioJaAdicionadoAmigo extends Exception {
    public UsuarioJaAdicionadoAmigo() {
        super("Usuário já está adicionado como amigo.");
    }
}

