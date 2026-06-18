package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.services.community.CommunityService;
import br.ufal.ic.p2.jackut.services.user.CommunityListService;
import br.ufal.ic.p2.jackut.services.user.UserIntegrator;

/**
 * Controlador responsável pelos casos de uso de comunidades.
 */
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityListService communityListService;
    private final UserIntegrator userIntegrator;

    /**
     * Cria o controlador de comunidades.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     */
    public CommunityController() throws SaveError, FileError {
        this.communityService = new CommunityService();
        this.communityListService = new CommunityListService();
        this.userIntegrator = UserIntegrator.getInstance();
    }

    /**
     * Cria uma comunidade para o usuário informado.
     *
     * @param userId identificador do usuário dono da comunidade.
     * @param name nome único da comunidade.
     * @param description descrição da comunidade.
     * @throws UsuarioNaoCadastrado se o usuário dono não estiver cadastrado.
     * @throws ComunidadeComEsseNomeJaExiste se já existir comunidade com o nome informado.
     * @throws UsuarioJaFazParteDessaComunidade se a comunidade já estiver na lista do usuário.
     */
    public void createCommunity(String userId, String name, String description)
            throws UsuarioNaoCadastrado, ComunidadeComEsseNomeJaExiste,
            UsuarioJaFazParteDessaComunidade {
        String ownerUserName = userIntegrator.getUserNameById(userId);
        communityService.createCommunity(userId, ownerUserName, name, description);
        communityListService.addCommunity(userId, name);
    }

    /**
     * Adiciona um usuário a uma comunidade existente.
     *
     * @param userId identificador do usuário.
     * @param name nome da comunidade.
     * @throws UsuarioNaoCadastrado se o usuário não estiver cadastrado.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     * @throws UsuarioJaFazParteDessaComunidade se o usuário já participar da comunidade.
     */
    public void addCommunity(String userId, String name)
            throws UsuarioNaoCadastrado, ComunidadeNaoExiste,
            UsuarioJaFazParteDessaComunidade {
        String userName = userIntegrator.getUserNameById(userId);
        communityService.addMember(name, userName);
        communityListService.addCommunity(userId, name);
    }

    /**
     * Recupera a descrição de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return descrição da comunidade.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getCommunityDescription(String name) throws ComunidadeNaoExiste {
        return communityService.getDescription(name);
    }

    /**
     * Recupera o dono de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return login do usuário dono.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getCommunityOwner(String name) throws ComunidadeNaoExiste {
        return communityService.getOwner(name);
    }

    /**
     * Recupera os membros de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return membros da comunidade em formato textual.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getCommunityMembers(String name) throws ComunidadeNaoExiste {
        return communityService.getMembers(name);
    }

    /**
     * Salva os dados de comunidades.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError {
        communityService.saveData();
    }

    /**
     * Limpa os dados de comunidades.
     */
    public void resetData() {
        communityService.resetData();
    }
}
