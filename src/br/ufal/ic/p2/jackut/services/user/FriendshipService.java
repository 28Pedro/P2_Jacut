package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;
import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.UserRepository;
import br.ufal.ic.p2.jackut.services.user.friendshipStateHandles.*;

import java.util.List;

public class FriendshipService {

    private UserRepository userRepository;

    public FriendshipService() throws FileError, SaveError {
        this.userRepository = UserRepository.getInstance();
    }

    public void addFriendship(String userId, String friendUserName)
    throws UsuarioNaoCadastrado, AdicionarASiMesmoAmigo,
            UsuarioJaAdicionadoAmigo, EsperandoAceitacaoAmigo{

        User user = userRepository.findUserOrThrow(userId);
        User friend = userRepository.getUserByName(friendUserName);

        if(user.getUserName().equals(friendUserName)){
            throw new AdicionarASiMesmoAmigo();
        }

        FriendShipStateHandle shipStateHandle = getStateHandle(user,friendUserName);

        shipStateHandle.addFriendship(user,friend);

    }

    public boolean isFriend(String userName, String friendUsername) throws
            UsuarioNaoCadastrado{

        User user = userRepository.getUserByName(userName);

        return user.
                friendshipListContainsUser(friendUsername,
                        FriendshipStates.CURRENT);

    }

    private FriendShipStateHandle getStateHandle(User user, String friendUsername){

        if(user.friendshipListContainsUser(friendUsername,FriendshipStates.CURRENT)){
            return new CurrentFriendState();
        }
        if(user.friendshipListContainsUser(friendUsername,FriendshipStates.SENT)){
            return new SentRequestState();
        }
        if(user.friendshipListContainsUser(friendUsername,FriendshipStates.REQUESTED)){
            return new ReceivedRequestState();
        }

        return new NoFriendshipState();
    }

    public String getFriends(String UserName) throws UsuarioNaoCadastrado{
        User user = userRepository.getUserByName(UserName);

        List<String> friendshipList = user.
                getFriendShipSateList(FriendshipStates.CURRENT);

        StringBuilder str = new StringBuilder();
        str.append('{');

        for (int i = 0; i < friendshipList.size(); i++) {
            str.append(friendshipList.get(i));

            if(i < friendshipList.size() - 1){
                str.append(',');
            }
        }

        str.append('}');

        return str.toString();
    }

}
