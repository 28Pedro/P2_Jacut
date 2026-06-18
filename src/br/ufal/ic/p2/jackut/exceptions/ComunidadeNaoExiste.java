package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando uma comunidade solicitada não existe.
 */
public class ComunidadeNaoExiste extends Exception {
    /**
     * Cria a exceção com a mensagem esperada pelos testes de aceitação.
     */
    public ComunidadeNaoExiste() {
        super("Comunidade não existe.");
    }
}
