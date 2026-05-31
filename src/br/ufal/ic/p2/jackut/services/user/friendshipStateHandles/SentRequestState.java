package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.exceptions.EsperandoAceitacaoAmigo;
import br.ufal.ic.p2.jackut.models.user.Friendship;

public class SentRequestState implements FriendShipStateHandle {

    @Override
    public void addFriendship(Friendship user, Friendship friend)
            throws EsperandoAceitacaoAmigo{
        throw new EsperandoAceitacaoAmigo();
    }
}
