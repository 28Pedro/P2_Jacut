package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository extends AbstractRepository<User> {

    private Map<String, String> userByUserName;
    private static UserRepository instance;

    private UserRepository() throws FileError,SaveError {
        super(XMLController.getInstance(),"user.xml");

        userByUserName = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach((id,user) -> {
                userByUserName.put(user.getUserName(),user.getId());
            } );
        }
    }

    public static UserRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public void saveUser(User user, String id){
        userByUserName.put(user.getUserName(), user.getId());
        addObject(id, user);
    }

    public User findUserOrThrow(String userId) throws UsuarioNaoCadastrado {
        return getObject(userId)
                .orElseThrow(UsuarioNaoCadastrado::new);
    }

    public boolean UserNameExists(String userName){
        return userByUserName.containsKey(userName);
    }

    public User getUserByName(String userName) throws UsuarioNaoCadastrado{

        return findUserOrThrow(userByUserName.get(userName));
    }

    @Override
    public void resetData(){
        super.resetData();
        userByUserName.clear();
    }

}
