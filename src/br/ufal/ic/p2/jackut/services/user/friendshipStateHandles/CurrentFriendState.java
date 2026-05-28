package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.exceptions.UsuarioJaAdicionadoAmigo;
import br.ufal.ic.p2.jackut.models.User;

public class CurrentFriendState implements FriendShipStateHandle {

    @Override
    public void addFriendship(User user, User friend)
            throws UsuarioJaAdicionadoAmigo {

        throw new UsuarioJaAdicionadoAmigo();
    }
}
