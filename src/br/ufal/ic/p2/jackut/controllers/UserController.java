package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.services.user.FriendshipService;
import br.ufal.ic.p2.jackut.services.user.ProfileService;
import br.ufal.ic.p2.jackut.services.user.UserService;

public class UserController {

    UserService userService;
    ProfileService profileService;
    FriendshipService friendshipService;

    public UserController() throws SaveError, FileError {
        this.userService = new UserService();
        this.profileService = new ProfileService();
        this.friendshipService = new FriendshipService();
    }

    public String CreateUser(String userName, String password, String name)
    throws LoginInvalido, SenhaInvalida, ContaComEsseNomeJaExiste {
         return userService.CreateUser(userName,password,name);
    }

    public String getUserAttribute(String userName, String attributeName)
    throws UsuarioNaoCadastrado, AtributoNaoPreenchido{

        return profileService.getUserAttribute(userName,attributeName);
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

        friendshipService.addFriendship(userId,friendUserName);
    }

    public boolean isFriend(String userName, String friendUsername) throws
            UsuarioNaoCadastrado{

         return friendshipService.isFriend(userName,friendUsername);
    }

    public String getFriends(String userName) throws UsuarioNaoCadastrado{
        return friendshipService.getFriends(userName);
    }

    public void saveData() throws SaveError{
        userService.saveData();
    }

    public void resetData(){
        userService.resetData();
    }
}
