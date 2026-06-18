package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaFazParteDessaComunidade;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.CommunityList;
import br.ufal.ic.p2.jackut.repositories.users.CommunityListRepository;

import java.util.UUID;

/**
 * Serviço responsável pela lista de comunidades associada a cada usuário.
 */
public class CommunityListService {

    private final CommunityListRepository communityListRepository;

    /**
     * Cria o serviço de listas de comunidades.
     *
     * @throws FileError se ocorrer falha ao carregar listas persistidas.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    public CommunityListService() throws FileError, SaveError {
        this.communityListRepository = CommunityListRepository.getInstance();
    }

    /**
     * Cria uma lista de comunidades para um usuário.
     *
     * @param userId identificador do usuário dono da lista.
     */
    public void buildCommunityListObject(String userId) {
        CommunityList communityList = new CommunityList(userId, UUID.randomUUID().toString());
        communityListRepository.saveCommunityList(communityList);
    }

    /**
     * Adiciona uma comunidade à lista do usuário.
     *
     * @param userId identificador do usuário.
     * @param communityName nome da comunidade.
     * @throws UsuarioNaoCadastrado se a lista do usuário não for encontrada.
     * @throws UsuarioJaFazParteDessaComunidade se o usuário já estiver na comunidade.
     */
    public void addCommunity(String userId, String communityName)
            throws UsuarioNaoCadastrado, UsuarioJaFazParteDessaComunidade {
        CommunityList communityList = communityListRepository.getCommunityListByUserId(userId);

        if (communityList.containsCommunity(communityName)) {
            throw new UsuarioJaFazParteDessaComunidade();
        }

        communityList.addCommunity(communityName);
    }

    /**
     * Retorna as comunidades de um usuário em formato textual.
     *
     * @param userId identificador do usuário.
     * @return comunidades do usuário no formato esperado pelos testes.
     * @throws UsuarioNaoCadastrado se a lista do usuário não for encontrada.
     */
    public String getCommunities(String userId) throws UsuarioNaoCadastrado {
        return communityListRepository.getCommunityListByUserId(userId).buildCommunityList();
    }

    /**
     * Salva as listas de comunidades.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError {
        communityListRepository.saveData();
    }

    /**
     * Limpa as listas de comunidades.
     */
    public void resetData() {
        communityListRepository.resetData();
    }
}
