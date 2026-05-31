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

public class ProfileRepository extends AbstractRepository<Profile>{

    private static ProfileRepository instance;
    private Map<String,String> profileByUserId;

    private ProfileRepository() throws SaveError, FileError {
        super(XMLController.getInstance(),"profile.xml");
        this.profileByUserId = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach((id, profile) -> {
                profileByUserId.put(profile.getUserId(),id);
            });
        }
    }

    public void saveProfile(Profile profile){
        String id = profile.getId();

        super.addObject(id,profile);
        profileByUserId.put(profile.getUserId(), id);
    }

    public static ProfileRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new ProfileRepository();
        }
        return instance;
    }

    public Profile profileById(String userId) throws UsuarioNaoCadastrado{
        Optional<Profile> profileO = Optional.ofNullable(entityMap.get(
                profileByUserId.get(userId)
        ));

        return profileO.orElseThrow(UsuarioNaoCadastrado::new);
    }

    @Override
    public void resetData(){
        super.resetData();
        profileByUserId.clear();
    }


}
