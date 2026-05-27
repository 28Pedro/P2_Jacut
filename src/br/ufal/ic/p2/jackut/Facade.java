package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.UserController;
import br.ufal.ic.p2.jackut.exceptions.*;
import easyaccept.EasyAccept;

public class Facade {

    UserController userController;

    public Facade() throws SaveError, FileError {
        this.userController = new UserController();
    }
    public void zerarSistema(){
        userController.resetData();
    }
    public void encerrarSistema() throws SaveError{
        userController.saveData();
    }

     public String criarUsuario(String userName, String passWorld, String name)
     throws SenhaInvalida, LoginInvalido, ContaComEsseNomeJaExiste {
            return userController.CreateUser(userName,passWorld,name);
     }

     public String getAtributoUsuario(String username, String attributeName)
     throws UsuarioNaoCadastrado{
        return userController.getUserAttribute(username,attributeName);
     }

     public String abrirSessao(String userName, String password) throws LoginOuSenhaInvalidos{
        return userController.openSession(userName,password);
     }

}
