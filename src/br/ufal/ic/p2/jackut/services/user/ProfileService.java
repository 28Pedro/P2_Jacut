package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchido;
import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.UserRepository;

public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService() throws FileError, SaveError {
        this.userRepository = UserRepository.getInstance();
    }

    public String getUserAttribute(String userName, String attributeName)
            throws UsuarioNaoCadastrado, AtributoNaoPreenchido {

        User user = userRepository.getUserByName(userName);

        return user.getUserAttribute(attributeName).
                orElseThrow(AtributoNaoPreenchido::new);
    }

    public void editProfile(String userId, String attribute,
                            String attributeValue) throws UsuarioNaoCadastrado{

        User user = userRepository.findUserOrThrow(userId);
        user.addAttribute(attribute,attributeValue);
    }
}
