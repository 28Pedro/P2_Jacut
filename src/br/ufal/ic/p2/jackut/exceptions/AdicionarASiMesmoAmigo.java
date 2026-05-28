package br.ufal.ic.p2.jackut.exceptions;

public class AdicionarASiMesmoAmigo extends Exception {
    public AdicionarASiMesmoAmigo() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}

