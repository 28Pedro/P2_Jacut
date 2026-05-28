package br.ufal.ic.p2.jackut.services.user.friendshipStateHandles;

import br.ufal.ic.p2.jackut.exceptions.EsperandoAceitacaoAmigo;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaAdicionadoAmigo;
import br.ufal.ic.p2.jackut.models.User;

public interface FriendShipStateHandle {

     void addFriendship(User user, User friend)
            throws UsuarioJaAdicionadoAmigo,
            EsperandoAceitacaoAmigo;
}

