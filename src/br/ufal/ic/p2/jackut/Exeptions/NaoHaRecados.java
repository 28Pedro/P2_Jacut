package br.ufal.ic.p2.jackut.Exeptions;

public class NaoHaRecados extends Exception {
    public NaoHaRecados() {
        super("Não há recados.");
    }
}

