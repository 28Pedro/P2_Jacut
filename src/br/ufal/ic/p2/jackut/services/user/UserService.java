package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.UserRepository;

import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    public UserService() throws FileError,SaveError {
        this.userRepository = UserRepository.getInstance();
    }

  public String CreateUser(String userName, String password, String name)
  throws LoginInvalido, SenhaInvalida, ContaComEsseNomeJaExiste {

        if(fieldIsEmpty(userName)){
            throw new LoginInvalido();
        }

        if(fieldIsEmpty(password)){
            throw new SenhaInvalida();
        }

        if(userRepository.UserNameExists(userName)){
            throw new ContaComEsseNomeJaExiste();
        }

        String id = UUID.randomUUID().toString();
        User user = new User(userName, password, name, id);

        userRepository.saveUser(user,id);

        return id;
    }

    public String openSession(String userName, String password) throws
            LoginOuSenhaInvalidos{

        if(fieldIsEmpty(userName) || fieldIsEmpty(password)){
            throw new LoginOuSenhaInvalidos();
        }

        try {
            User user = userRepository.getUserByName(userName);
            return user.validateSection(password)
                    .orElseThrow(LoginOuSenhaInvalidos::new);

        } catch (UsuarioNaoCadastrado e) {
            throw new LoginOuSenhaInvalidos();
        }
    }

    public void saveData() throws SaveError{
        userRepository.saveData();
    }

    public void resetData(){
        userRepository.resetData();
    }

    private boolean fieldIsEmpty(String field){
        return field == null || field.isBlank();
    }

}
