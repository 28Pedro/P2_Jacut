package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.exceptions.EsperandoAceitacaoAmigo;
import br.ufal.ic.p2.jackut.models.User;

public class SentRequestState implements FriendShipStateHandle {

    @Override
    public void addFriendship(User user, User friend)
            throws EsperandoAceitacaoAmigo{
        throw new EsperandoAceitacaoAmigo();
    }
}
