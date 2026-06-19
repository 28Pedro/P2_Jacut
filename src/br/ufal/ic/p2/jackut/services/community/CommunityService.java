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
     * Valida se um nome de comunidade está disponível.
     *
     * @param name nome da comunidade.
     * @throws ComunidadeComEsseNomeJaExiste se já existir comunidade com o nome informado.
     */
    public void validateCommunityNameAvailable(String name)
            throws ComunidadeComEsseNomeJaExiste {
        if (communityRepository.communityNameExists(name)) {
            throw new ComunidadeComEsseNomeJaExiste();
        }
    }

    /**
     * Cria uma nova comunidade.
     *
     * @param ownerUserId identificador do usuário dono.
     * @param ownerUserName login do usuário dono.
     * @param name nome único da comunidade.
     * @param description descrição da comunidade.
     * @param chatMessengerId identificador do chat da comunidade.
     * @throws ComunidadeComEsseNomeJaExiste se já existir comunidade com o nome informado.
     */
    public void createCommunity(String ownerUserId, String ownerUserName, String name,
                                String description, String chatMessengerId)
            throws ComunidadeComEsseNomeJaExiste {
        validateCommunityNameAvailable(name);

        Community community = new Community(UUID.randomUUID().toString(), name, description,
                ownerUserId, ownerUserName, chatMessengerId);
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
     * Recupera o identificador do chat associado à comunidade.
     *
     * @param name nome da comunidade.
     * @return identificador do chat da comunidade.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getChatMessengerId(String name) throws ComunidadeNaoExiste {
        return communityRepository.getCommunityByName(name).getChatMessengerId();
    }

    /**
     * Adiciona um usuário como membro de uma comunidade.
     *
     * @param communityName nome da comunidade.
     * @param userId identificador do usuário.
     * @param userName login do usuário.
     * @return identificador do chat da comunidade.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     * @throws UsuarioJaFazParteDessaComunidade se o usuário já participar da comunidade.
     */
    public String addMember(String communityName, String userId, String userName)
            throws ComunidadeNaoExiste, UsuarioJaFazParteDessaComunidade {
        Community community = communityRepository.getCommunityByName(communityName);

        if (community.containsMember(userName)) {
            throw new UsuarioJaFazParteDessaComunidade();
        }

        community.addMember(userId, userName);
        return community.getChatMessengerId();
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
