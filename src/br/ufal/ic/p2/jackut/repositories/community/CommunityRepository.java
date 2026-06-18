package br.ufal.ic.p2.jackut.repositories.community;

import br.ufal.ic.p2.jackut.exceptions.ComunidadeNaoExiste;
import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.community.Community;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Map;

/**
 * Repositório responsável por persistir e recuperar comunidades.
 */
public class CommunityRepository extends AbstractRepository<Community> {

    private static CommunityRepository instance;
    private Map<String, String> communityByName;

    /**
     * Cria o repositório de comunidades e reconstrói o índice por nome.
     *
     * @throws FileError se ocorrer falha ao carregar comunidades persistidas.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    private CommunityRepository() throws FileError, SaveError {
        super(XMLController.getInstance(), "community.xml");

        communityByName = new HashMap<>();

        if (!entityMap.isEmpty()) {
            entityMap.forEach((id, community) ->
                    communityByName.put(community.getName(), community.getId()));
        }
    }

    /**
     * Retorna a instância única do repositório de comunidades.
     *
     * @return instância compartilhada do repositório.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar comunidades persistidas.
     */
    public static CommunityRepository getInstance() throws SaveError, FileError {
        if (instance == null) {
            instance = new CommunityRepository();
        }
        return instance;
    }

    /**
     * Salva uma comunidade e atualiza o índice por nome.
     *
     * @param community comunidade a ser salva.
     */
    public void saveCommunity(Community community) {
        addObject(community.getId(), community);
        communityByName.put(community.getName(), community.getId());
    }

    /**
     * Verifica se já existe comunidade com determinado nome.
     *
     * @param name nome da comunidade.
     * @return {@code true} se o nome já estiver cadastrado.
     */
    public boolean communityNameExists(String name) {
        return communityByName.containsKey(name);
    }

    /**
     * Recupera uma comunidade pelo nome.
     *
     * @param name nome da comunidade.
     * @return comunidade encontrada.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public Community getCommunityByName(String name) throws ComunidadeNaoExiste {
        String communityId = communityByName.get(name);
        return getObject(communityId).orElseThrow(ComunidadeNaoExiste::new);
    }

    /**
     * Limpa comunidades e índice por nome.
     */
    @Override
    public void resetData() {
        super.resetData();
        communityByName.clear();
    }
}
