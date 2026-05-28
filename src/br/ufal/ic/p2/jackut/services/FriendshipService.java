package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;
import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.User;
import br.ufal.ic.p2.jackut.repositories.UserRepository;

public class FriendshipService {

    private UserRepository userRepository;

    public FriendshipService() throws FileError, SaveError {
        this.userRepository = UserRepository.getInstance();
    }


    public void addFriendship(String userId, String friendUserName)
    throws UsuarioNaoCadastrado,UsuarioNaoPodsAdicionarASiMesmoComoAmigo,
            UsuarioJaEstaAdicionadoComoAmigo,UsuarioJaEstaAdicionadoComoAmigoEsperandoAceitacao

            {
        User user = userRepository.findUserOrThrow(userId);

        if(friendUserName.equals(user.getName())){
            throw new UsuarioNaoPodsAdicionarASiMesmoComoAmigo();

        }else if(user.friendshipListContainsUser(friendUserName,
                FriendshipStates.CURRENT)){
           throw new UsuarioJaEstaAdicionadoComoAmigo();

        }else if(user.friendshipListContainsUser(friendUserName,
                FriendshipStates.SENT)){
            throw new UsuarioJaEstaAdicionadoComoAmigoEsperandoAceitacao();
        }else if(user.friendshipListContainsUser(friendUserName,
                FriendshipStates.REQUESTED)){

            //add_relationship();
        }else{
            //send_FriendShipRequest()
        }

    }
}
