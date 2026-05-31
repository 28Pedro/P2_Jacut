package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.ChatMessengerController;
import br.ufal.ic.p2.jackut.controllers.UserController;
import br.ufal.ic.p2.jackut.exceptions.*;


public class Facade {

    UserController userController;
    ChatMessengerController chatMessengerController;

    public Facade() throws SaveError, FileError {
        this.userController = new UserController();
        this.chatMessengerController = new ChatMessengerController();
    }
    public void zerarSistema(){
        userController.resetData();
        chatMessengerController.resetData();
    }
    public void encerrarSistema() throws SaveError{
        userController.saveData();
        chatMessengerController.saveData();
    }

     public String criarUsuario(String userName, String passWorld, String name)
     throws SenhaInvalida, LoginInvalido, ContaComEsseNomeJaExiste {
            return userController.CreateUser(userName,passWorld,name);
     }

     public String getAtributoUsuario(String username, String attributeName)
     throws UsuarioNaoCadastrado,AtributoNaoPreenchido{
        return userController.getUserAttribute(username,attributeName);
     }

     public String abrirSessao(String userName, String password) throws LoginOuSenhaInvalidos{
        return userController.openSession(userName,password);
     }

     public void editarPerfil (String userId, String attribute, String attributeValue)
     throws UsuarioNaoCadastrado{
        userController.editProfile(userId,attribute, attributeValue);
     }

    public void adicionarAmigo(String userId, String friendUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoAmigo,
            UsuarioJaAdicionadoAmigo, EsperandoAceitacaoAmigo{

        userController.addFriendship(userId,friendUserName);
    }

    public boolean ehAmigo(String userName, String friendUsername) throws
            UsuarioNaoCadastrado{

        return userController.isFriend(userName,friendUsername);
    }

    public String getAmigos(String userName) throws UsuarioNaoCadastrado{
        return userController.getFriends(userName);
    }

    public void enviarRecado(String senderId, String receiverUserName, String messenger) throws
            UsuarioNaoCadastrado,EnviarRecadoParaSiMesmo {
        chatMessengerController.SendMessenger(messenger,senderId,receiverUserName);
    }

    public String lerRecado(String userId)
            throws UsuarioNaoCadastrado,NaoHaRecados{
        return chatMessengerController.readMessenger(userId);
    }


}
