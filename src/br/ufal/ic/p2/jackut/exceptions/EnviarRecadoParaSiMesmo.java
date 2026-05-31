package br.ufal.ic.p2.jackut.exceptions;

public class EnviarRecadoParaSiMesmo extends Exception {
    public EnviarRecadoParaSiMesmo() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}

