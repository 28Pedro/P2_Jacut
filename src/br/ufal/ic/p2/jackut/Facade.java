package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.ChatMessengerController;
import br.ufal.ic.p2.jackut.controllers.CommunityController;
import br.ufal.ic.p2.jackut.controllers.UserController;
import br.ufal.ic.p2.jackut.controllers.UserDeletionController;
import br.ufal.ic.p2.jackut.exceptions.*;

/**
 * Fachada pública da aplicação Jackut.
 *
 * <p>Esta classe concentra os métodos utilizados pelos testes de aceitação e
 * delega a execução para os controladores responsáveis. O objetivo é oferecer
 * uma API simples e estável, ocultando a organização interna em serviços,
 * modelos e repositórios.</p>
 */
public class Facade {

    UserController userController;
    ChatMessengerController chatMessengerController;
    CommunityController communityController;
    UserDeletionController userDeletionController;

    /**
     * Cria uma nova fachada e inicializa os controladores principais.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     */
    public Facade() throws SaveError, FileError {
        this.userController = new UserController();
        this.chatMessengerController = new ChatMessengerController();
        this.communityController = new CommunityController();
        this.userDeletionController = new UserDeletionController();
    }

    /**
     * Remove os dados persistidos e reinicia as estruturas em memória.
     */
    public void zerarSistema(){
        userController.resetData();
        chatMessengerController.resetData();
        communityController.resetData();
    }

    /**
     * Persiste os dados atuais da aplicação.
     *
     * @throws SaveError se ocorrer falha durante a gravação dos dados.
     */
    public void encerrarSistema() throws SaveError{
        userController.saveData();
        chatMessengerController.saveData();
        communityController.saveData();
    }

    /**
     * Cria uma nova conta de usuário.
     *
     * @param userName login único usado para identificar o usuário.
     * @param passWorld senha usada para autenticação do usuário.
     * @param name nome inicial armazenado no perfil do usuário.
     * @return identificador único do usuário criado.
     * @throws SenhaInvalida se a senha informada for vazia ou inválida.
     * @throws LoginInvalido se o login informado for vazio ou inválido.
     * @throws ContaComEsseNomeJaExiste se já existir uma conta com o login informado.
     */
     public String criarUsuario(String userName, String passWorld, String name)
     throws SenhaInvalida, LoginInvalido, ContaComEsseNomeJaExiste {
            return userController.CreateUser(userName,passWorld,name);
     }

    /**
     * Recupera um atributo do perfil de um usuário.
     *
     * @param username login do usuário cujo atributo será consultado.
     * @param attributeName nome do atributo solicitado.
     * @return valor textual do atributo solicitado.
     * @throws UsuarioNaoCadastrado se não existir usuário com o login informado.
     * @throws AtributoNaoPreenchido se o atributo solicitado não estiver preenchido.
     */
     public String getAtributoUsuario(String username, String attributeName)
     throws UsuarioNaoCadastrado,AtributoNaoPreenchido{
        return userController.getUserAttribute(username,attributeName);
     }

    /**
     * Abre uma sessão para o usuário informado.
     *
     * @param userName login do usuário.
     * @param password senha do usuário.
     * @return identificador da sessão, representado pelo ID do usuário autenticado.
     * @throws LoginOuSenhaInvalidos se o login ou a senha forem inválidos.
     */
     public String abrirSessao(String userName, String password) throws LoginOuSenhaInvalidos{
        return userController.openSession(userName,password);
     }

    /**
     * Edita um atributo do perfil do usuário autenticado.
     *
     * @param userId identificador do usuário.
     * @param attribute nome do atributo a ser editado.
     * @param attributeValue novo valor do atributo.
     * @throws UsuarioNaoCadastrado se não existir usuário para o identificador informado.
     */
     public void editarPerfil (String userId, String attribute, String attributeValue)
     throws UsuarioNaoCadastrado{
        userController.editProfile(userId,attribute, attributeValue);
     }

    /**
     * Remove uma conta e todos os dados associados ao usuario.
     *
     * @param userId identificador da conta removida.
     * @throws UsuarioNaoCadastrado se a conta nao estiver cadastrada.
     */
    public void removerUsuario(String userId) throws UsuarioNaoCadastrado {
        userDeletionController.deleteUser(userId);
    }

    /**
     * Solicita ou confirma amizade entre dois usuários.
     *
     * @param userId identificador do usuário que executa a ação.
     * @param friendUserName login do usuário a ser adicionado como amigo.
     * @throws UsuarioNaoCadastrado se algum usuário envolvido não estiver cadastrado.
     * @throws AdicionarASiMesmoRelationship se o usuário tentar adicionar a si mesmo.
     * @throws UsuarioJaAdicionadoRelationship se os usuários já forem amigos.
     * @throws EsperandoAceitacaoRelationship se já existir solicitação pendente de aceite.
     * @throws FuncaoInvalida se o destinatário tiver marcado o usuário como inimigo.
     */
    public void adicionarAmigo(String userId, String friendUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship,
            FuncaoInvalida {

        userController.addFriendship(userId, friendUserName);
    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param userName login do primeiro usuário.
     * @param friendUsername login do segundo usuário.
     * @return {@code true} se os usuários forem amigos; {@code false} caso contrário.
     * @throws UsuarioNaoCadastrado se algum dos usuários não estiver cadastrado.
     */
    public boolean ehAmigo(String userName, String friendUsername) throws
            UsuarioNaoCadastrado{

        return userController.isFriend(userName,friendUsername);
    }

    /**
     * Retorna a lista de amigos de um usuário.
     *
     * @param userName login do usuário consultado.
     * @return representação textual da lista de amigos do usuário.
     * @throws UsuarioNaoCadastrado se o usuário informado não estiver cadastrado.
     */
    public String getAmigos(String userName) throws UsuarioNaoCadastrado{
        return userController.getFriends(userName);
    }

    /**
     * Registra que um usuario e fa de outro usuario.
     *
     * @param userId identificador do fa.
     * @param idolUserName login do idolo.
     * @throws UsuarioNaoCadastrado se algum usuario nao estiver cadastrado.
     * @throws AdicionarASiMesmoRelationship se o usuario indicar a si mesmo.
     * @throws UsuarioJaAdicionadoRelationship se o relacionamento ja existir.
     * @throws EsperandoAceitacaoRelationship se houver solicitacao pendente.
     * @throws FuncaoInvalida se a interacao estiver bloqueada.
     */    public void adicionarIdolo(String userId, String idolUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship,
            FuncaoInvalida {
        userController.addIdol(userId, idolUserName);
    }

    /**
     * Verifica se um usuario e fa de outro.
     *
     * @param userName login do possivel fa.
     * @param idolUserName login do possivel idolo.
     * @return {@code true} quando o relacionamento existir.
     * @throws UsuarioNaoCadastrado se algum usuario nao estiver cadastrado.
     */    public boolean ehFa(String userName, String idolUserName) throws UsuarioNaoCadastrado {
        return userController.isFan(userName, idolUserName);
    }

    /**
     * Retorna os fas de um usuario.
     *
     * @param userName login do usuario consultado.
     * @return representacao textual dos fas.
     * @throws UsuarioNaoCadastrado se o usuario nao estiver cadastrado.
     */    public String getFas(String userName) throws UsuarioNaoCadastrado {
        return userController.getFans(userName);
    }

    /**
     * Adiciona uma paquera e notifica os envolvidos quando ela for reciproca.
     *
     * @param userId identificador do usuario que adiciona a paquera.
     * @param crushUserName login da paquera.
     * @throws UsuarioNaoCadastrado se algum usuario nao estiver cadastrado.
     * @throws AdicionarASiMesmoRelationship se o usuario indicar a si mesmo.
     * @throws UsuarioJaAdicionadoRelationship se o relacionamento ja existir.
     * @throws EsperandoAceitacaoRelationship se houver solicitacao pendente.
     * @throws FuncaoInvalida se a interacao estiver bloqueada.
     * @throws EnviarRecadoParaSiMesmo se o envio de recado for invalido.
     */    public void adicionarPaquera(String userId, String crushUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship,
            FuncaoInvalida, EnviarRecadoParaSiMesmo {
        userController.addCrush(userId, crushUserName);
    }

    /**
     * Verifica se existe uma paquera entre dois usuarios.
     *
     * @param userId identificador do usuario consultado.
     * @param crushUserName login da paquera.
     * @return {@code true} quando o relacionamento existir.
     * @throws UsuarioNaoCadastrado se algum usuario nao estiver cadastrado.
     */    public boolean ehPaquera(String userId, String crushUserName) throws UsuarioNaoCadastrado {
        return userController.isCrush(userId, crushUserName);
    }

    /**
     * Retorna as paqueras registradas por um usuario.
     *
     * @param userId identificador do usuario consultado.
     * @return representacao textual das paqueras.
     * @throws UsuarioNaoCadastrado se o usuario nao estiver cadastrado.
     */    public String getPaqueras(String userId) throws UsuarioNaoCadastrado {
        return userController.getCrushes(userId);
    }

    /**
     * Registra um usuario como inimigo de outro.
     *
     * @param userId identificador do usuario que executa a acao.
     * @param enemyUserName login do inimigo.
     * @throws UsuarioNaoCadastrado se algum usuario nao estiver cadastrado.
     * @throws AdicionarASiMesmoRelationship se o usuario indicar a si mesmo.
     * @throws UsuarioJaAdicionadoRelationship se o relacionamento ja existir.
     * @throws EsperandoAceitacaoRelationship se houver solicitacao pendente.
     */    public void adicionarInimigo(String userId, String enemyUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship {
        userController.addEnemy(userId, enemyUserName);
    }

    /**
     * Cria uma comunidade.
     *
     * @param userId identificador do usuário dono da comunidade.
     * @param name nome único da comunidade.
     * @param description descrição da comunidade.
     * @throws UsuarioNaoCadastrado se o usuário dono não estiver cadastrado.
     * @throws ComunidadeComEsseNomeJaExiste se já existir comunidade com o nome informado.
     * @throws UsuarioJaFazParteDessaComunidade se a comunidade já estiver vinculada ao usuário.
     */
    public void criarComunidade(String userId, String name, String description)
            throws UsuarioNaoCadastrado, ComunidadeComEsseNomeJaExiste,
            UsuarioJaFazParteDessaComunidade {
        communityController.createCommunity(userId, name, description);
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
    public void adicionarComunidade(String userId, String name)
            throws UsuarioNaoCadastrado, ComunidadeNaoExiste,
            UsuarioJaFazParteDessaComunidade {
        communityController.addCommunity(userId, name);
    }

    /**
     * Recupera a descrição de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return descrição da comunidade.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getDescricaoComunidade(String name) throws ComunidadeNaoExiste {
        return communityController.getCommunityDescription(name);
    }

    /**
     * Recupera o dono de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return login do dono da comunidade.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getDonoComunidade(String name) throws ComunidadeNaoExiste {
        return communityController.getCommunityOwner(name);
    }

    /**
     * Recupera os membros de uma comunidade.
     *
     * @param name nome da comunidade.
     * @return membros da comunidade em formato textual.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public String getMembrosComunidade(String name) throws ComunidadeNaoExiste {
        return communityController.getCommunityMembers(name);
    }

    /**
     * Recupera as comunidades das quais um usuário participa.
     *
     * @param userName login do usuário.
     * @return comunidades do usuário em formato textual.
     * @throws UsuarioNaoCadastrado se o usuário não estiver cadastrado.
     */
    public String getComunidades(String userName) throws UsuarioNaoCadastrado {
        return userController.getCommunities(userName);
    }

    /**
     * Envia uma mensagem para uma comunidade.
     *
     * @param userId identificador do usuário remetente.
     * @param communityName nome da comunidade.
     * @param message conteúdo textual da mensagem.
     * @throws UsuarioNaoCadastrado se o usuário remetente não estiver cadastrado.
     * @throws ComunidadeNaoExiste se a comunidade não existir.
     */
    public void enviarMensagem(String userId, String communityName, String message)
            throws UsuarioNaoCadastrado, ComunidadeNaoExiste {
        communityController.sendCommunityMessage(userId, communityName, message);
    }

    /**
     * Lê a próxima mensagem de comunidade pendente para o usuário.
     *
     * @param userId identificador do usuário leitor.
     * @return conteúdo textual da próxima mensagem de comunidade.
     * @throws UsuarioNaoCadastrado se o usuário não estiver cadastrado.
     * @throws NaoHaMensagens se o usuário não possuir mensagens pendentes.
     */
    public String lerMensagem(String userId) throws UsuarioNaoCadastrado, NaoHaMensagens {
        return communityController.readCommunityMessage(userId);
    }

    /**
     * Envia um recado de um usuário para outro.
     *
     * @param senderId identificador do usuário remetente.
     * @param receiverUserName login do usuário destinatário.
     * @param messenger conteúdo textual do recado.
     * @throws UsuarioNaoCadastrado se o destinatário não estiver cadastrado.
     * @throws EnviarRecadoParaSiMesmo se o remetente tentar enviar recado para si mesmo.
     * @throws FuncaoInvalida se o destinatario tiver marcado o remetente como inimigo.
     */
    public void enviarRecado(String senderId, String receiverUserName, String messenger) throws
            UsuarioNaoCadastrado, EnviarRecadoParaSiMesmo, FuncaoInvalida {
        chatMessengerController.SendMessenger(messenger,senderId,receiverUserName);
    }

    /**
     * Lê o próximo recado disponível para o usuário.
     *
     * @param userId identificador do usuário que deseja ler o recado.
     * @return conteúdo textual do próximo recado não lido.
     * @throws UsuarioNaoCadastrado se o usuário informado não estiver cadastrado.
     * @throws NaoHaRecados se o usuário não possuir recados pendentes.
     */
    public String lerRecado(String userId)
            throws UsuarioNaoCadastrado,NaoHaRecados{
        return chatMessengerController.readMessenger(userId);
    }


}
