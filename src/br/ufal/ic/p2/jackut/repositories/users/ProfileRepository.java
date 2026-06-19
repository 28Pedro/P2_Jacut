package br.ufal.ic.p2.jackut.repositories.users;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.Profile;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório responsável por persistir e recuperar perfis.
 */
public class ProfileRepository extends AbstractRepository<Profile>{

    private static ProfileRepository instance;
    private Map<String,String> profileByUserId;

    /**
     * Cria o repositório de perfis e reconstrói o índice por usuário.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar perfis persistidos.
     */
    private ProfileRepository() throws SaveError, FileError {
        super(XMLController.getInstance(),"profile.xml");
        this.profileByUserId = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach((id, profile) -> {
                profileByUserId.put(profile.getUserId(),id);
            });
        }
    }

    /**
     * Salva um perfil e atualiza o índice por usuário.
     *
     * @param profile perfil salvo.
     */
    public void saveProfile(Profile profile){
        String id = profile.getId();

        super.addObject(id,profile);
        profileByUserId.put(profile.getUserId(), id);
    }

    /**
     * Retorna a instência única do repositório de perfis.
     *
     * @return inst?ncia compartilhada do repositório.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar perfis persistidos.
     */
    public static ProfileRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new ProfileRepository();
        }
        return instance;
    }

    /**
     * Recupera o perfil associado a um usuário.
     *
     * @param userId identificador do usuário dono do perfil.
     * @return perfil encontrado.
     * @throws UsuarioNaoCadastrado se o perfil do usuário não for encontrado.
     */
    public Profile profileById(String userId) throws UsuarioNaoCadastrado{
        Optional<Profile> profileO = Optional.ofNullable(entityMap.get(
                profileByUserId.get(userId)
        ));

        return profileO.orElseThrow(UsuarioNaoCadastrado::new);
    }

    public void deleteProfileByUserId(String userId) throws UsuarioNaoCadastrado {
        Profile profile = profileById(userId);
        entityMap.remove(profile.getId());
        profileByUserId.remove(userId);
    }

    /**
     * Limpa perfis e índice por usuário.
     */
    @Override
    public void resetData(){
        super.resetData();
        profileByUserId.clear();
    }


}
