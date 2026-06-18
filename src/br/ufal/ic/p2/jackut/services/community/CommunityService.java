package br.ufal.ic.p2.jackut.services.community;

import br.ufal.ic.p2.jackut.exceptions.ComunidadeComEsseNomeJaExiste;
import br.ufal.ic.p2.jackut.exceptions.ComunidadeNaoExiste;
import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaFazParteDessaComunidade;
import br.ufal.ic.p2.jackut.models.community.Community;
import br.ufal.ic.p2.jackut.repositories.community.CommunityRepository;

import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio de comunidades.
 */
public class CommunityService {

    private final CommunityRepository communityRepository;

    /**
     * Cria o serviço de comunidades.
     *
     * @throws FileError se ocorrer falha ao carregar comunidades persistidas.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    public CommunityService() throws FileError, SaveError {
        this.communityRepository = CommunityRepository.getInstance();
    }

    /**
     * Cria uma nova comunidade.
     *
     * @param ownerUserId identificador do usuário dono.
     * @param ownerUserName login do usuário dono.
     * @param name nome único da comunidade.
     * @param description descrição da comunidade.
     * @throws ComunidadeComEsseNomeJaExiste se já existir comunidade com o nome informado.
     */
    public void createCommunity(String ownerUserId, String ownerUserName, String name,
                                String description) throws ComunidadeComEsseNomeJaExiste {
        if (communityRepository.communityNameExists(name)) {
            throw new ComunidadeComEsseNomeJaExiste();
        }

        Community community = new Community(UUID.randomUUID().toString(), name, description,
                ownerUserId, ownerUserName);
        communityRepository.saveCommunity(community);
    }

    /**
     * Recupera a descrição de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return descrição da comunidade.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getDescription(String name) throws ComunidadeNaoExiste {
        return communityRepository.getCommunityByName(name).getDescription();
    }

    /**
     * Recupera o login do dono de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return login do dono da comunidade.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getOwner(String name) throws ComunidadeNaoExiste {
        return communityRepository.getCommunityByName(name).getOwnerUserName();
    }

    /**
     * Recupera os membros de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return membros da comunidade em formato textual.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getMembers(String name) throws ComunidadeNaoExiste {
        return communityRepository.getCommunityByName(name).buildMembersList();
    }

    /**
     * Adiciona um usuário como membro de uma comunidade.
     *
     * @param communityName nome da comunidade.
     * @param userName login do usuário.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     * @throws UsuarioJaFazParteDessaComunidade se o usuário já participar da comunidade.
     */
    public void addMember(String communityName, String userName)
            throws ComunidadeNaoExiste, UsuarioJaFazParteDessaComunidade {
        Community community = communityRepository.getCommunityByName(communityName);

        if (community.containsMember(userName)) {
            throw new UsuarioJaFazParteDessaComunidade();
        }

        community.addMember(userName);
    }

    /**
     * Salva os dados de comunidades.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError {
        communityRepository.saveData();
    }

    /**
     * Limpa os dados de comunidades.
     */
    public void resetData() {
        communityRepository.resetData();
    }
}
