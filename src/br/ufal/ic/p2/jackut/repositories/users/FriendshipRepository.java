package br.ufal.ic.p2.jackut.repositories.users;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.Friendship;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FriendshipRepository extends AbstractRepository<Friendship> {

    private Map<String,String> friendshipByUserId;
    private static FriendshipRepository instance;

    private FriendshipRepository() throws SaveError, FileError {
        super(XMLController.getInstance(),"friendship.xml");

        this.friendshipByUserId = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach(((id, friendship) ->{
                friendshipByUserId.put(friendship.getUserId(),id);
            } ));
        }
    }

    public static FriendshipRepository getInstance() throws SaveError, FileError{
        if(instance == null){
            instance = new FriendshipRepository();
        }
        return instance;
    }

    public void saveFriendship(Friendship friendship){
        String id = friendship.getId();
        addObject(id,friendship);

        friendshipByUserId.put(friendship.getUserId(),id);
    }

    public Friendship getFriendshipById(String friendshipId) throws
            UsuarioNaoCadastrado{

        return Optional.
                ofNullable(entityMap.get(friendshipId)).
                orElseThrow(UsuarioNaoCadastrado::new);
    }

    public Friendship getFriendshipByUserId(String userId) throws
            UsuarioNaoCadastrado{
        return getFriendshipById(friendshipByUserId.get(userId));
    }

    @Override
    public void resetData(){
        super.resetData();
        friendshipByUserId.clear();
    }

}
