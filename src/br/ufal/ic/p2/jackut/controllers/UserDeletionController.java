package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.services.community.CommunityDeletionData;
import br.ufal.ic.p2.jackut.services.community.CommunityIntegrator;
import br.ufal.ic.p2.jackut.services.user.MessageBoxIntegrator;
import br.ufal.ic.p2.jackut.services.user.ProfileIntegrator;
import br.ufal.ic.p2.jackut.services.user.RelationshipIntegrator;
import br.ufal.ic.p2.jackut.services.user.UserIntegrator;

/**
 * Controlador responsável por orquestrar a remoção de contas de usuário.
 */
public class UserDeletionController {

    private final UserIntegrator userIntegrator;
    private final ProfileIntegrator profileIntegrator;
    private final RelationshipIntegrator relationshipIntegrator;
    private final CommunityIntegrator communityIntegrator;
    private final MessageBoxIntegrator messageBoxIntegrator;

    /**
     * Cria o controlador e obtém os integradores responsáveis pelos dados do usuário.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     */
    public UserDeletionController() throws SaveError, FileError {
        this.userIntegrator = UserIntegrator.getInstance();
        this.profileIntegrator = ProfileIntegrator.getInstance();
        this.relationshipIntegrator = RelationshipIntegrator.getInstance();
        this.communityIntegrator = CommunityIntegrator.getInstance();
        this.messageBoxIntegrator = new MessageBoxIntegrator();
    }

    /**
     * Remove todos os dados vinculados a um usuário cadastrado.
     *
     * @param userId identificador do usuário removido.
     * @throws UsuarioNaoCadastrado se o identificador não corresponder a uma conta ativa.
     */
    public void deleteUser(String userId) throws UsuarioNaoCadastrado {
        userIntegrator.getUserNameById(userId);
        relationshipIntegrator.deleteUserRelationships(userId);
        CommunityDeletionData communityDeletionData =
                communityIntegrator.deleteUserCommunities(userId);
        messageBoxIntegrator.deleteUserMessages(
                userId, communityDeletionData.getCommunityChatsToKeep());
        profileIntegrator.deleteProfile(userId);
        userIntegrator.deleteUser(userId);
    }
}
