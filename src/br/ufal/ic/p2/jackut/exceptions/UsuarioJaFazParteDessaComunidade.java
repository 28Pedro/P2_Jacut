package br.ufal.ic.p2.jackut.exceptions;

/**
 * Exceção lançada quando um usuário tenta entrar em uma comunidade da qual já participa.
 */
public class UsuarioJaFazParteDessaComunidade extends Exception {
    /**
     * Cria a exceção com a mensagem esperada pelos testes de aceitação.
     */
    public UsuarioJaFazParteDessaComunidade() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
