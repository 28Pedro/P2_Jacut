package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.repositories.users.RelationshipRepository;

/**
 * Integrador para operações externas sobre relacionamentos.
 */
public class RelationshipIntegrator {

    private static RelationshipIntegrator instance;
    private final RelationshipRepository relationshipRepository;

    private RelationshipIntegrator() throws FileError, SaveError {
        this.relationshipRepository = RelationshipRepository.getInstance();
    }

    /**
     * Retorna a instancia compartilhada do integrador de relacionamentos.
     *
     * @return integrador de relacionamentos.
     * @throws SaveError se a infraestrutura de persistencia nao puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     */    public static RelationshipIntegrator getInstance() throws SaveError, FileError {
        if (instance == null) {
            instance = new RelationshipIntegrator();
        }

        return instance;
    }

    /**
     * Remove relacionamentos proprios e referencias ao usuario em outros relacionamentos.
     *
     * @param userId identificador do usuario removido.
     * @throws UsuarioNaoCadastrado se as estruturas de relacionamento nao existirem.
     */    public void deleteUserRelationships(String userId) throws UsuarioNaoCadastrado {
        relationshipRepository.deleteRelationshipsByUserId(userId);
        relationshipRepository.removeUserFromAllRelationships(userId);
    }
}
