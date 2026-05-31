package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;
import br.ufal.ic.p2.jackut.models.user.Friendship;

public class NoFriendshipState implements FriendShipStateHandle {

    @Override
    public void addFriendship(Friendship user, Friendship friend){

        user.addFriendshipState(friend.getId(), FriendshipStates.SENT);
        friend.addFriendshipState(user.getId(),FriendshipStates.REQUESTED);
    }

}
