package br.ufal.ic.p2.jackut.services.community;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.community.Community;
import br.ufal.ic.p2.jackut.repositories.community.CommunityRepository;
import br.ufal.ic.p2.jackut.repositories.users.CommunityListRepository;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Integrador para remoção dos vínculos de comunidade de um usuário.
 */
public class CommunityIntegrator {

    private static CommunityIntegrator instance;
    private final CommunityRepository communityRepository;
    private final CommunityListRepository communityListRepository;

    private CommunityIntegrator() throws FileError, SaveError {
        this.communityRepository = CommunityRepository.getInstance();
        this.communityListRepository = CommunityListRepository.getInstance();
    }

    /**
     * Retorna a instância compartilhada do integrador.
     *
     * @return integrador de comunidades.
     * @throws FileError se ocorrer falha ao carregar os dados persistidos.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    public static CommunityIntegrator getInstance() throws FileError, SaveError {
        if (instance == null) {
            instance = new CommunityIntegrator();
        }

        return instance;
    }

    /**
     * Exclui comunidades pertencentes ao usuário e remove sua participação nas demais.
     *
     * @param userId identificador do usuário removido.
     * @return dados necessários para ajustar os chats de comunidades preservadas.
     */
    public CommunityDeletionData deleteUserCommunities(String userId) {
        Set<String> communityChatsToKeep = new LinkedHashSet<>();

        for (Community community : communityRepository.getCommunities()) {
            if (community.getOwnerUserId().equals(userId)) {
                communityRepository.deleteCommunity(community);
                communityListRepository.removeCommunityFromAllLists(community.getName());
            } else if (community.getMembersUserIds().contains(userId)) {
                community.removeMember(userId);
                communityChatsToKeep.add(community.getChatMessengerId());
            }
        }

        communityListRepository.deleteCommunityListByUserId(userId);
        return new CommunityDeletionData(communityChatsToKeep);
    }
}
