package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository extends AbstractRepository<User> {

    private Map<String, User> userByUserName;

    public UserRepository() throws FileError,SaveError {
        super(XMLController.getInstance(),"user.xml");

        userByUserName = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach((id,user) -> {
                userByUserName.put(user.getUserName(),user);
            } );
        }
    }

    public void saveUser(User user, String id){
        userByUserName.put(user.getUserName(), user);
        addObject(id, user);
    }

    public boolean UserNameExists(String userName){
        return userByUserName.containsKey(userName);
    }

    public User getUserByName(String userName){
        return userByUserName.get(userName);
    }

    @Override
    public void resetData(){
        super.resetData();
        userByUserName.clear();
    }

}
