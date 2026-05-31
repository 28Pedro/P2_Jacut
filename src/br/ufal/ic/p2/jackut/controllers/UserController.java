package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.services.user.*;

public class UserController {

    UserService userService;
    ProfileService profileService;
    FriendshipService friendshipService;
    UserIntegrator userIntegrator;
    MessageBoxService messageBoxService;

    public UserController() throws SaveError, FileError {
        this.userService = new UserService();
        this.profileService = new ProfileService();
        this.friendshipService = new FriendshipService();
        this.userIntegrator = UserIntegrator.getInstance();
        this.messageBoxService = MessageBoxService.getInstance();
    }

    public String CreateUser(String userName, String password, String name)
    throws LoginInvalido, SenhaInvalida, ContaComEsseNomeJaExiste {

         String userId = userService.CreateUser(userName,password);
         profileService.createProfile(userId,name);
         friendshipService.buildFriendshipObject(userId);
         messageBoxService.buildMessageBoxObject(userId);

         return userId;
    }

    public String getUserAttribute(String userName, String attributeName)
    throws UsuarioNaoCadastrado, AtributoNaoPreenchido{

        String userId = userIntegrator.getUserByName(userName);
        return profileService.getUserAttribute(userId,attributeName);
    }

    public String openSession(String userName, String password) throws
            LoginOuSenhaInvalidos{
        return userService.openSession(userName,password);
    }

    public void editProfile(String UserId, String attribute,
                            String attributeValue) throws UsuarioNaoCadastrado{

        profileService.editProfile(UserId,attribute,attributeValue);
    }

    public void addFriendship(String userId, String friendUserName)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoAmigo,
            UsuarioJaAdicionadoAmigo, EsperandoAceitacaoAmigo{

        String friendUserId = userIntegrator.getUserByName(friendUserName);

        if(userId.equals(friendUserId)){
            throw new AdicionarASiMesmoAmigo();
        }

        friendshipService.addFriendship(userId,friendUserId);
    }

    public boolean isFriend(String userName, String friendUsername) throws
            UsuarioNaoCadastrado{

        String friendId = userIntegrator.getUserByName(friendUsername);
        String userId = userIntegrator.getUserByName(userName);

         return friendshipService.isFriend(userId,friendId);
    }

    public String getFriends(String userName) throws UsuarioNaoCadastrado{

        String userId = userIntegrator.getUserByName(userName);

        return userService.buildUsernameListById(
                friendshipService.getFriends(userId)
        );

    }

    public void saveData() throws SaveError{
        userService.saveData();
        profileService.saveData();
        friendshipService.saveData();
        messageBoxService.saveData();
    }

    public void resetData(){
        userService.resetData();
        profileService.resetData();
        friendshipService.resetData();
        messageBoxService.resetData();
    }
}
