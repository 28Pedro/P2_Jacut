package br.ufal.ic.p2.jackut.repositories.users;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.CommunityList;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório responsável por persistir listas de comunidades por usuário.
 */
public class CommunityListRepository extends AbstractRepository<CommunityList> {

    private static CommunityListRepository instance;
    private Map<String, String> communityListByUserId;

    /**
     * Cria o repositório e reconstrói o índice por usuário.
     *
     * @throws FileError se ocorrer falha ao carregar listas persistidas.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    private CommunityListRepository() throws FileError, SaveError {
        super(XMLController.getInstance(), "communityList.xml");

        this.communityListByUserId = new HashMap<>();

        if (!entityMap.isEmpty()) {
            entityMap.forEach((id, communityList) ->
                    communityListByUserId.put(communityList.getUserId(), id));
        }
    }

    /**
     * Retorna a instância única do repositório.
     *
     * @return instância compartilhada do repositório.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar listas persistidas.
     */
    public static CommunityListRepository getInstance() throws SaveError, FileError {
        if (instance == null) {
            instance = new CommunityListRepository();
        }
        return instance;
    }

    /**
     * Salva uma lista de comunidades e atualiza o índice por usuário.
     *
     * @param communityList lista de comunidades salva.
     */
    public void saveCommunityList(CommunityList communityList) {
        addObject(communityList.getId(), communityList);
        communityListByUserId.put(communityList.getUserId(), communityList.getId());
    }

    /**
     * Recupera uma lista pelo identificador do usuário.
     *
     * @param userId identificador do usuário.
     * @return lista de comunidades do usuário.
     * @throws UsuarioNaoCadastrado se a lista do usuário não for encontrada.
     */
    public CommunityList getCommunityListByUserId(String userId) throws UsuarioNaoCadastrado {
        String communityListId = communityListByUserId.get(userId);
        return Optional.ofNullable(entityMap.get(communityListId))
                .orElseThrow(UsuarioNaoCadastrado::new);
    }

    /**
     * Remove a lista de comunidades vinculada ao usuário informado.
     *
     * @param userId identificador do usuário removido.
     */
    public void deleteCommunityListByUserId(String userId) {
        String communityListId = communityListByUserId.remove(userId);

        if (communityListId != null) {
            entityMap.remove(communityListId);
        }
    }

    /**
     * Remove uma comunidade das listas de todos os usuários.
     *
     * @param communityName nome da comunidade removida.
     */
    public void removeCommunityFromAllLists(String communityName) {
        entityMap.values().forEach(list -> list.removeCommunity(communityName));
    }

    /**
     * Limpa listas e índice por usuário.
     */
    @Override
    public void resetData() {
        super.resetData();
        communityListByUserId.clear();
    }
}
