package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;
import br.ufal.ic.p2.jackut.models.user.User;

public class NoFriendshipState implements FriendShipStateHandle {

    @Override
    public void addFriendship(User user, User friend){

        user.addFriendshipState(friend.getUserName(), FriendshipStates.SENT);
        friend.addFriendshipState(user.getUserName(),FriendshipStates.REQUESTED);
    }

}
