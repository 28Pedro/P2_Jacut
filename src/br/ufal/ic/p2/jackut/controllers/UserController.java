package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.services.FriendshipService;
import br.ufal.ic.p2.jackut.services.ProfileService;
import br.ufal.ic.p2.jackut.services.UserService;

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

    public void saveData() throws SaveError{
        userService.saveData();
    }

    public void resetData(){
        userService.resetData();
    }
}
