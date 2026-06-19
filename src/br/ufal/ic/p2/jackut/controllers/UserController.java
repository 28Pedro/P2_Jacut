package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.enums.RelationshipType;
import br.ufal.ic.p2.jackut.services.user.*;

/**
 * Controlador responsável pelos casos de uso relacionados a usuários.
 *
 * <p>Esta classe coordena serviços de conta, perfil, amizade, caixa de
 * mensagens e lista de comunidades, mantendo a fachada desacoplada dos detalhes
 * de criação e consulta dessas estruturas.</p>
 */
public class UserController {

    UserService userService;
    ProfileService profileService;
    RelationshipService relationshipService;
    UserIntegrator userIntegrator;
    MessageBoxService messageBoxService;
    CommunityListService communityListService;

    /**
     * Cria o controlador e inicializa os serviços necessários.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se houver falha ao carregar dados persistidos.
     */
    public UserController() throws SaveError, FileError {
        this.userService = new UserService();
        this.profileService = new ProfileService();
        this.relationshipService = new RelationshipService();
        this.userIntegrator = UserIntegrator.getInstance();
        this.messageBoxService = MessageBoxService.getInstance();
        this.communityListService = new CommunityListService();
    }

    /**
     * Cria um usuário e suas estruturas associadas.
     *
     * @param userName login único do usuário.
     * @param password senha do usuário.
     * @param name nome inicial do perfil.
     * @return identificador único do usuário criado.
     * @throws LoginInvalido se o login informado for inválido.
     * @throws SenhaInvalida se a senha informada for inválida.
     * @throws ContaComEsseNomeJaExiste se já existir usuário com o mesmo login.
     */
    public String CreateUser(String userName, String password, String name)
    throws LoginInvalido, SenhaInvalida, ContaComEsseNomeJaExiste {

         String userId = userService.CreateUser(userName,password);
         profileService.createProfile(userId,name);
         relationshipService.buildRelationshipObjects(userId);
         messageBoxService.buildMessageBoxObject(userId);
         communityListService.buildCommunityListObject(userId);

         return userId;
    }

    /**
     * Recupera um atributo de perfil a partir do login do usuário.
     *
     * @param userName login do usuário.
     * @param attributeName nome do atributo solicitado.
     * @return valor do atributo solicitado.
     * @throws UsuarioNaoCadastrado se o usuário não existir.
     * @throws AtributoNaoPreenchido se o atributo não estiver preenchido.
     */
    public String getUserAttribute(String userName, String attributeName)
    throws UsuarioNaoCadastrado, AtributoNaoPreenchido{

        String userId = userIntegrator.getUserByName(userName);
        return profileService.getUserAttribute(userId,attributeName);
    }

    /**
     * Abre uma sessão para um usuário autenticado.
     *
     * @param userName login do usuário.
     * @param password senha do usuário.
     * @return identificador do usuário autenticado.
     * @throws LoginOuSenhaInvalidos se login ou senha estiverem incorretos.
     */
    public String openSession(String userName, String password) throws
            LoginOuSenhaInvalidos{
        return userService.openSession(userName,password);
    }

    /**
     * Edita um atributo do perfil de um usuário.
     *
     * @param UserId identificador do usuário.
     * @param attribute nome do atributo.
     * @param attributeValue valor a ser armazenado.
     * @throws UsuarioNaoCadastrado se o usuário não existir.
     */
    public void editProfile(String UserId, String attribute,
                            String attributeValue) throws UsuarioNaoCadastrado{

        profileService.editProfile(UserId,attribute,attributeValue);
    }

    /**
     * Solicita ou confirma amizade com outro usuário.
     *
     * @param userId identificador do usuário que executa a ação.
     * @param friendUserName login do usuário a ser adicionado.
     * @throws UsuarioNaoCadastrado se algum usuário não estiver cadastrado.
     * @throws AdicionarASiMesmoRelationship se o usuário tentar adicionar a si mesmo.
     * @throws UsuarioJaAdicionadoRelationship se os usuários já forem amigos.
     * @throws EsperandoAceitacaoRelationship se já existir solicitação pendente.
     * @throws FuncaoInvalida se o destinatário tiver marcado o usuário como inimigo.
     */
    public void addRelationship(String userId, String relatedUserName, RelationshipType type)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship,
            FuncaoInvalida {

        String relatedUserId = userIntegrator.getUserByName(relatedUserName);
        assertCanInteract(userId, relatedUserName);
        relationshipService.addRelationship(userId, relatedUserId, type);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param userName login do primeiro usuário.
     * @param friendUsername login do segundo usuário.
     * @return {@code true} se os usuários forem amigos; {@code false} caso contrário.
     * @throws UsuarioNaoCadastrado se algum usuário não estiver cadastrado.
     */
    public boolean isFriend(String userName, String friendUsername) throws
            UsuarioNaoCadastrado{

        String friendId = userIntegrator.getUserByName(friendUsername);
        String userId = userIntegrator.getUserByName(userName);

         return relationshipService.hasRelationship(userId, friendId, RelationshipType.FRIENDSHIP);
    }

    /**
     * Retorna os amigos de um usuário em formato textual.
     *
     * @param userName login do usuário consultado.
     * @return lista textual com os logins dos amigos.
     * @throws UsuarioNaoCadastrado se o usuário não estiver cadastrado.
     */
    public String getFriends(String userName) throws UsuarioNaoCadastrado{

        String userId = userIntegrator.getUserByName(userName);

        return userService.buildUsernameListById(
                relationshipService.getRelatedUsers(userId, RelationshipType.FRIENDSHIP)
        );

    }

    public void addIdol(String userId, String idolUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship,
            FuncaoInvalida {
        String idolUserId = userIntegrator.getUserByName(idolUserName);
        assertCanInteract(userId, idolUserName);
        relationshipService.addRelationship(userId, idolUserId, RelationshipType.FAN);
    }

    public boolean isFan(String userName, String idolUserName) throws UsuarioNaoCadastrado {
        String userId = userIntegrator.getUserByName(userName);
        String idolUserId = userIntegrator.getUserByName(idolUserName);
        return relationshipService.hasRelationship(userId, idolUserId, RelationshipType.FAN);
    }

    public String getFans(String userName) throws UsuarioNaoCadastrado {
        String userId = userIntegrator.getUserByName(userName);
        return userService.buildUsernameListById(
                relationshipService.getReverseRelatedUsers(userId, RelationshipType.FAN));
    }

    public void addCrush(String userId, String crushUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship,
            FuncaoInvalida {
        String crushUserId = userIntegrator.getUserByName(crushUserName);
        assertCanInteract(userId, crushUserName);
        relationshipService.addRelationship(userId, crushUserId, RelationshipType.CRUSH);
    }

    public boolean isCrush(String userId, String crushUserName) throws UsuarioNaoCadastrado {
        String crushUserId = userIntegrator.getUserByName(crushUserName);
        return relationshipService.hasRelationship(userId, crushUserId, RelationshipType.CRUSH);
    }

    public String getCrushes(String userId) throws UsuarioNaoCadastrado {
        return userService.buildUsernameListById(
                relationshipService.getRelatedUsers(userId, RelationshipType.CRUSH));
    }

    public void addEnemy(String userId, String enemyUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship {
        String enemyUserId = userIntegrator.getUserByName(enemyUserName);
        relationshipService.addRelationship(userId, enemyUserId, RelationshipType.ENEMY);
    }

    public boolean hasReciprocalCrush(String userId, String crushUserName)
            throws UsuarioNaoCadastrado {
        String crushUserId = userIntegrator.getUserByName(crushUserName);
        return relationshipService.hasRelationship(crushUserId, userId, RelationshipType.CRUSH);
    }

    public String getUserNameById(String userId) throws UsuarioNaoCadastrado {
        return userIntegrator.getUserNameById(userId);
    }

    public String getUserIdByName(String userName) throws UsuarioNaoCadastrado {
        return userIntegrator.getUserByName(userName);
    }

    public String getUserDisplayNameById(String userId) throws UsuarioNaoCadastrado {
        try {
            return profileService.getUserAttribute(userId, "nome");
        } catch (AtributoNaoPreenchido e) {
            return userIntegrator.getUserNameById(userId);
        }
    }

    public void assertCanInteract(String userId, String targetUserName)
            throws UsuarioNaoCadastrado, FuncaoInvalida {
        String targetUserId = userIntegrator.getUserByName(targetUserName);

        if (relationshipService.isBlockedByEnemy(userId, targetUserId)) {
            throw new FuncaoInvalida(getUserDisplayNameById(targetUserId));
        }
    }

    /**
     * Retorna as comunidades das quais o usuário participa.
     *
     * @param userName login do usuário consultado.
     * @return lista textual com os nomes das comunidades.
     * @throws UsuarioNaoCadastrado se o usuário não estiver cadastrado.
     */
    public String getCommunities(String userName) throws UsuarioNaoCadastrado {
        String userId = userIntegrator.getUserByName(userName);
        return communityListService.getCommunities(userId);
    }

    /**
     * Salva os dados dos serviços coordenados por este controlador.
     *
     * @throws SaveError se ocorrer falha ao persistir algum dado.
     */
    public void saveData() throws SaveError{
        userService.saveData();
        profileService.saveData();
        relationshipService.saveData();
        messageBoxService.saveData();
        communityListService.saveData();
    }

    /**
     * Limpa os dados dos serviços coordenados por este controlador.
     */
    public void resetData(){
        userService.resetData();
        profileService.resetData();
        relationshipService.resetData();
        messageBoxService.resetData();
        communityListService.resetData();
    }
}
