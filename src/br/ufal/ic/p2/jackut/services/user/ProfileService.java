package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchido;
import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.Profile;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.users.ProfileRepository;

import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio dos perfis de usuário.
 */
public class ProfileService {

    private final ProfileRepository profileRepository;

    /**
     * Cria o serviço de perfis.
     *
     * @throws FileError se ocorrer falha ao carregar perfis persistidos.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    public ProfileService() throws FileError, SaveError {
        this.profileRepository = ProfileRepository.getInstance();
    }

    /**
     * Cria um perfil para um usuário.
     *
     * @param userId identificador do usuário dono do perfil.
     * @param name nome inicial do usuário, armazenado no atributo {@code nome}.
     */
    public void createProfile(String userId, String name){
        Profile profile = new Profile(userId, UUID.randomUUID().toString());

        if(name != null){
            profile.addAttribute("nome",name);
        }

        profileRepository.saveProfile(profile);
    }

    /**
     * Recupera um atributo do perfil de um usuário.
     *
     * @param userId identificador do usuário dono do perfil.
     * @param attributeName nome do atributo solicitado.
     * @return valor textual do atributo.
     * @throws UsuarioNaoCadastrado se o perfil do usuário não for encontrado.
     * @throws AtributoNaoPreenchido se o atributo não estiver preenchido.
     */
    public String getUserAttribute(String userId, String attributeName)
            throws UsuarioNaoCadastrado, AtributoNaoPreenchido {

       Profile profile = profileRepository.profileById(userId);

       return profile.getUserAttribute(attributeName).orElseThrow
               (AtributoNaoPreenchido::new);
    }

    /**
     * Edita um atributo do perfil de um usuário.
     *
     * @param userId identificador do usuário dono do perfil.
     * @param attribute nome do atributo a ser editado.
     * @param attributeValue valor a ser armazenado.
     * @throws UsuarioNaoCadastrado se o perfil do usuário não for encontrado.
     */
    public void editProfile(String userId, String attribute,
                            String attributeValue) throws UsuarioNaoCadastrado{

        Profile profile = profileRepository.profileById(userId);
        profile.addAttribute(attribute,attributeValue);
    }

    /**
     * Salva os dados de perfis.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError{
        profileRepository.saveData();
    }

    /**
     * Limpa os dados de perfis.
     */
    public void resetData(){
        profileRepository.resetData();
    }
}
