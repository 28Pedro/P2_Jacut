package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;
import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.user.Friendship;
import br.ufal.ic.p2.jackut.repositories.users.FriendshipRepository;
import br.ufal.ic.p2.jackut.services.user.friendshipStateHandles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public FriendshipService() throws FileError, SaveError {
        this.friendshipRepository = FriendshipRepository.getInstance();
    }

    public void buildFriendshipObject(String userId){
        Friendship friendship = new Friendship(userId, UUID.randomUUID().toString());
        friendshipRepository.saveFriendship(friendship);
    }

    public void addFriendship(String userId, String friendUserId)
    throws UsuarioNaoCadastrado,UsuarioJaAdicionadoAmigo,
            EsperandoAceitacaoAmigo{

        Friendship user = friendshipRepository.getFriendshipByUserId(userId);
        Friendship friend = friendshipRepository.getFriendshipByUserId(friendUserId);

        FriendShipStateHandle shipStateHandle = getStateHandle(user, friend.getId());

        shipStateHandle.addFriendship(user,friend);

    }

    public boolean isFriend(String userId, String friendUserId) throws
            UsuarioNaoCadastrado{

        Friendship user = friendshipRepository.getFriendshipByUserId(userId);

        return user.
                friendshipListContainsUser( friendshipRepository.
                        getFriendshipByUserId(friendUserId).getId(),
                        FriendshipStates.CURRENT);

    }

    private FriendShipStateHandle getStateHandle(Friendship user, String friendUserId){

        if(user.friendshipListContainsUser(friendUserId,FriendshipStates.CURRENT)){
            return new CurrentFriendState();
        }
        if(user.friendshipListContainsUser(friendUserId,FriendshipStates.SENT)){
            return new SentRequestState();
        }
        if(user.friendshipListContainsUser(friendUserId,FriendshipStates.REQUESTED)){
            return new ReceivedRequestState();
        }

        return new NoFriendshipState();
    }

    public List<String> getFriends(String UserId) throws UsuarioNaoCadastrado{

        Friendship user = friendshipRepository.getFriendshipByUserId(UserId);


        List<String> friendUsersIds = new ArrayList<>();

        List<String> frinedshipIdList =  user.getFriendShipSateList(FriendshipStates.CURRENT);

        for (String friendshipId : frinedshipIdList) {

            Friendship friend = friendshipRepository.getFriendshipById(friendshipId);
            friendUsersIds.add( friend.getUserId());
        }

        return friendUsersIds;
    }

    public void saveData() throws SaveError{
        friendshipRepository.saveData();
    }

    public void resetData(){
        friendshipRepository.resetData();
    }

}
