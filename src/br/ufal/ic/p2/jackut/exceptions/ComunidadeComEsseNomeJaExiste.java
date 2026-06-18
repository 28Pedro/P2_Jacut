package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando já existe comunidade com o nome informado.
 */
public class ComunidadeComEsseNomeJaExiste extends Exception {
    /**
     * Cria a exceção com a mensagem esperada pelos testes de aceitação.
     */
    public ComunidadeComEsseNomeJaExiste() {
        super("Comunidade com esse nome j\u00e1 existe.");
    }
}
