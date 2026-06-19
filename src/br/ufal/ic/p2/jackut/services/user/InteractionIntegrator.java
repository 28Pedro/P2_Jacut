package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchido;
import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.FuncaoInvalida;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;

/**
 * Integrador responsavel por validar interacoes entre usuarios.
 */
public class InteractionIntegrator {

    private final UserIntegrator userIntegrator;
    private final ProfileService profileService;
    private final RelationshipService relationshipService;

    /**
     * Cria o integrador com os servicos necessarios para validar uma interacao.
     *
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     * @throws SaveError se a infraestrutura de persistencia nao puder ser preparada.
     */
    public InteractionIntegrator() throws FileError, SaveError {
        this.userIntegrator = UserIntegrator.getInstance();
        this.profileService = new ProfileService();
        this.relationshipService = new RelationshipService();
    }

    /**
     * Impede a interacao quando o destinatario tiver marcado o remetente como inimigo.
     *
     * @param userId identificador do usuario que inicia a interacao.
     * @param targetUserName login do usuario destinatario.
     * @throws UsuarioNaoCadastrado se o destinatario nao estiver cadastrado.
     * @throws FuncaoInvalida se o destinatario tiver marcado o usuario como inimigo.
     */
    public void assertCanInteract(String userId, String targetUserName)
            throws UsuarioNaoCadastrado, FuncaoInvalida {
        String targetUserId = userIntegrator.getUserByName(targetUserName);

        if (relationshipService.isBlockedByEnemy(userId, targetUserId)) {
            throw new FuncaoInvalida(getDisplayName(targetUserId));
        }
    }

    private String getDisplayName(String userId) throws UsuarioNaoCadastrado {
        try {
            return profileService.getUserAttribute(userId, "nome");
        } catch (AtributoNaoPreenchido e) {
            return userIntegrator.getUserNameById(userId);
        }
    }
}
