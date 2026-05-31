package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.exceptions.UsuarioJaAdicionadoAmigo;
import br.ufal.ic.p2.jackut.models.user.Friendship;

public class CurrentFriendState implements FriendShipStateHandle {

    @Override
    public void addFriendship(Friendship user, Friendship friend)
            throws UsuarioJaAdicionadoAmigo {

        throw new UsuarioJaAdicionadoAmigo();
    }
}
