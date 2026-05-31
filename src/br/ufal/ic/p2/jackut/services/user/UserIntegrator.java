package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.users.UserRepository;

import java.util.Optional;

public class UserIntegrator {

   private final UserRepository userRepository;

   public UserIntegrator() throws FileError, SaveError {
       this.userRepository = UserRepository.getInstance();
   }

    public String getUserByName(String userName) throws UsuarioNaoCadastrado {
       User user = userRepository.getUserByName(userName);
        return user.getId();
    }

    public void notifyUser(String userId,String chatMessengerId) throws UsuarioNaoCadastrado{
       User user = userRepository.findUserOrThrow(userId);
       user.addNotification(chatMessengerId);
    }

    public Optional<String> getNotificationUser(String userId)
    throws UsuarioNaoCadastrado{
       User user = userRepository.findUserOrThrow(userId);
       return user.popNotification();
    }

}
