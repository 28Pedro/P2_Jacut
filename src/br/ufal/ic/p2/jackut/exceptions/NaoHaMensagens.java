package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário não possui mensagens de comunidade pendentes.
 */
public class NaoHaMensagens extends Exception {
    /**
     * Cria a exceção com a mensagem esperada pelos testes de aceitação.
     */
    public NaoHaMensagens() {
        super("Não há mensagens.");
    }
}
