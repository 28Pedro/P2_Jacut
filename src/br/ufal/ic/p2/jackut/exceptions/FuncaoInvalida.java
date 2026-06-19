package br.ufal.ic.p2.jackut.exceptions;

public class FuncaoInvalida extends Exception {

    public FuncaoInvalida(String enemyName) {
        super("Função inválida: " + enemyName + " é seu inimigo.");
    }
}
