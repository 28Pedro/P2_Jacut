package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchido;
import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.Profile;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.users.ProfileRepository;

import java.util.UUID;

public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService() throws FileError, SaveError {
        this.profileRepository = ProfileRepository.getInstance();
    }

    public void createProfile(String userId, String name){
        Profile profile = new Profile(userId, UUID.randomUUID().toString());

        if(name != null){
            profile.addAttribute("nome",name);
        }

        profileRepository.saveProfile(profile);
    }

    public String getUserAttribute(String userId, String attributeName)
            throws UsuarioNaoCadastrado, AtributoNaoPreenchido {

       Profile profile = profileRepository.profileById(userId);

       return profile.getUserAttribute(attributeName).orElseThrow
               (AtributoNaoPreenchido::new);
    }

    public void editProfile(String userId, String attribute,
                            String attributeValue) throws UsuarioNaoCadastrado{

        Profile profile = profileRepository.profileById(userId);
        profile.addAttribute(attribute,attributeValue);
    }

    public void saveData() throws SaveError{
        profileRepository.saveData();
    }

    public void resetData(){
        profileRepository.resetData();
    }
}
