package br.ufal.ic.p2.jackut.Exeptions;

public class UsuarioNaoPodeEnviarRecadoParaSiMesmo extends Exception {
    public UsuarioNaoPodeEnviarRecadoParaSiMesmo() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}

