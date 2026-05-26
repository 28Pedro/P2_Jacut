package br.ufal.ic.p2.jackut.Exeptions;

public class UsuarioNaoCadastrado extends Exception {
    public UsuarioNaoCadastrado() {
        super("Usuário não cadastrado.");
    }
}

