package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.services.UserService;

public class UserController {

    UserService userService;

    public UserController() throws SaveError, FileError {
        this.userService = new UserService();
    }

    public String CreateUser(String userName, String password, String name)
    throws LoginInvalido, SenhaInvalida, ContaComEsseNomeJaExiste {
         return userService.CreateUser(userName,password,name);
    }

    public String getUserAttribute(String userName, String attributeName)
    throws UsuarioNaoCadastrado{

        return userService.getUserAttribute(userName,attributeName);
    }

    public String openSession(String userName, String password) throws
            LoginOuSenhaInvalidos{
        return userService.openSession(userName,password);
    }

    public void saveData() throws SaveError{
        userService.saveData();
    }

    public void resetData(){
        userService.resetData();
    }
}
