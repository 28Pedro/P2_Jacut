package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoPodsAdicionarASiMesmoComoAmigo extends Exception {
    public UsuarioNaoPodsAdicionarASiMesmoComoAmigo() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}

