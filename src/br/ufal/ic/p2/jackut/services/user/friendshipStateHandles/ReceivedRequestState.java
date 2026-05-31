package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;
import br.ufal.ic.p2.jackut.models.user.Friendship;

public class ReceivedRequestState implements FriendShipStateHandle {
    @Override
    public void addFriendship(Friendship user, Friendship friend){
        user.addFriendshipState(friend.getId(), FriendshipStates.CURRENT); // aqui o usuário aceita a ordem
        user.removeFridShipState(friend.getId(), FriendshipStates.REQUESTED); //aqui remove da lista de request do usuário
        friend.addFriendshipState(user.getId(),FriendshipStates.CURRENT); // aqui adiciona na lista de amigos do remetente
        friend.removeFridShipState(user.getId(),FriendshipStates.SENT); // aqui reteira da lista de enviados do remetente
    }
}
