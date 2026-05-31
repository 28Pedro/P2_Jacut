package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;
import br.ufal.ic.p2.jackut.models.user.User;

public class ReceivedRequestState implements FriendShipStateHandle {
    @Override
    public void addFriendship(User user, User friend){
        user.addFriendshipState(friend.getUserName(), FriendshipStates.CURRENT); // aqui o usuário aceita a ordem
        user.removeFridShipState(friend.getUserName(), FriendshipStates.REQUESTED); //aqui remove da lista de request do usuário
        friend.addFriendshipState(user.getUserName(),FriendshipStates.CURRENT); // aqui adiciona na lista de amigos do remetente
        friend.removeFridShipState(user.getUserName(),FriendshipStates.SENT); // aqui reteira da lista de enviados do remetente
    }
}
