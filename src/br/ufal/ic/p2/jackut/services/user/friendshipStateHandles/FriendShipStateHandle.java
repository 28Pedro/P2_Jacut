package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.exceptions.EsperandoAceitacaoAmigo;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaAdicionadoAmigo;
import br.ufal.ic.p2.jackut.models.user.Friendship;
import br.ufal.ic.p2.jackut.models.user.User;

public interface FriendShipStateHandle {

     void addFriendship(Friendship user, Friendship friend)
            throws UsuarioJaAdicionadoAmigo,
            EsperandoAceitacaoAmigo;
}

