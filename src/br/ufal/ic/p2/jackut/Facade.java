package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.ChatMessengerController;
import br.ufal.ic.p2.jackut.controllers.UserController;
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

    /**
     * Cria uma nova fachada e inicializa os controladores principais.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     */
    public Facade() throws SaveError, FileError {
        this.userController = new UserController();
        this.chatMessengerController = new ChatMessengerController();
    }

    /**
     * Remove os dados persistidos e reinicia as estruturas em memória.
     */
    public void zerarSistema(){
        userController.resetData();
        chatMessengerController.resetData();
    }

    /**
     * Persiste os dados atuais da aplicação.
     *
     * @throws SaveError se ocorrer falha durante a gravação dos dados.
     */
    public void encerrarSistema() throws SaveError{
        userController.saveData();
        chatMessengerController.saveData();
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
     * Solicita ou confirma amizade entre dois usuários.
     *
     * @param userId identificador do usuário que executa a ação.
     * @param friendUserName login do usuário a ser adicionado como amigo.
     * @throws UsuarioNaoCadastrado se algum usuário envolvido não estiver cadastrado.
     * @throws AdicionarASiMesmoAmigo se o usuário tentar adicionar a si mesmo.
     * @throws UsuarioJaAdicionadoAmigo se os usuários já forem amigos.
     * @throws EsperandoAceitacaoAmigo se já existir solicitação pendente de aceite.
     */
    public void adicionarAmigo(String userId, String friendUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoAmigo,
            UsuarioJaAdicionadoAmigo, EsperandoAceitacaoAmigo{

        userController.addFriendship(userId,friendUserName);
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
     * Envia um recado de um usuário para outro.
     *
     * @param senderId identificador do usuário remetente.
     * @param receiverUserName login do usuário destinatário.
     * @param messenger conteúdo textual do recado.
     * @throws UsuarioNaoCadastrado se o destinatário não estiver cadastrado.
     * @throws EnviarRecadoParaSiMesmo se o remetente tentar enviar recado para si mesmo.
     */
    public void enviarRecado(String senderId, String receiverUserName, String messenger) throws
            UsuarioNaoCadastrado,EnviarRecadoParaSiMesmo {
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
