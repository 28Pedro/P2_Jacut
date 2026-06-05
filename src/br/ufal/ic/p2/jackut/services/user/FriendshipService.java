package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.enums.FriendshipStates;
import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.user.Friendship;
import br.ufal.ic.p2.jackut.repositories.users.FriendshipRepository;
import br.ufal.ic.p2.jackut.services.user.friendshipStateHandles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio de amizades.
 */
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    /**
     * Cria o serviço de amizades.
     *
     * @throws FileError se ocorrer falha ao carregar dados de amizades.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    public FriendshipService() throws FileError, SaveError {
        this.friendshipRepository = FriendshipRepository.getInstance();
    }

    /**
     * Cria a estrutura de amizade associada a um usuário.
     *
     * @param userId identificador do usuário dono da estrutura.
     */
    public void buildFriendshipObject(String userId){
        Friendship friendship = new Friendship(userId, UUID.randomUUID().toString());
        friendshipRepository.saveFriendship(friendship);
    }

    /**
     * Solicita ou confirma amizade entre dois usuários.
     *
     * @param userId identificador do usuário que executa a ação.
     * @param friendUserId identificador do usuário relacionado.
     * @throws UsuarioNaoCadastrado se alguma estrutura de amizade não for encontrada.
     * @throws UsuarioJaAdicionadoAmigo se a amizade já estiver confirmada.
     * @throws EsperandoAceitacaoAmigo se já existir solicitação pendente.
     */
    public void addFriendship(String userId, String friendUserId)
    throws UsuarioNaoCadastrado,UsuarioJaAdicionadoAmigo,
            EsperandoAceitacaoAmigo{

        Friendship user = friendshipRepository.getFriendshipByUserId(userId);
        Friendship friend = friendshipRepository.getFriendshipByUserId(friendUserId);

        FriendShipStateHandle shipStateHandle = getStateHandle(user, friend.getId());

        shipStateHandle.addFriendship(user,friend);

    }

    /**
     * Verifica se dois usuários são amigos.
     *
     * @param userId identificador do primeiro usuário.
     * @param friendUserId identificador do segundo usuário.
     * @return {@code true} se os usuários forem amigos; {@code false} caso contrário.
     * @throws UsuarioNaoCadastrado se alguma estrutura de amizade não for encontrada.
     */
    public boolean isFriend(String userId, String friendUserId) throws
            UsuarioNaoCadastrado{

        Friendship user = friendshipRepository.getFriendshipByUserId(userId);

        return user.
                friendshipListContainsUser( friendshipRepository.
                        getFriendshipByUserId(friendUserId).getId(),
                        FriendshipStates.CURRENT);

    }

    private FriendShipStateHandle getStateHandle(Friendship user, String friendUserId){

        if(user.friendshipListContainsUser(friendUserId,FriendshipStates.CURRENT)){
            return new CurrentFriendState();
        }
        if(user.friendshipListContainsUser(friendUserId,FriendshipStates.SENT)){
            return new SentRequestState();
        }
        if(user.friendshipListContainsUser(friendUserId,FriendshipStates.REQUESTED)){
            return new ReceivedRequestState();
        }

        return new NoFriendshipState();
    }

    /**
     * Retorna os IDs dos usuários amigos de um usuário.
     *
     * @param UserId identificador do usuário consultado.
     * @return lista de identificadores dos usuários amigos.
     * @throws UsuarioNaoCadastrado se a estrutura de amizade do usuário não for encontrada.
     */
    public List<String> getFriends(String UserId) throws UsuarioNaoCadastrado{

        Friendship user = friendshipRepository.getFriendshipByUserId(UserId);


        List<String> friendUsersIds = new ArrayList<>();

        List<String> frinedshipIdList =  user.getFriendShipSateList(FriendshipStates.CURRENT);

        for (String friendshipId : frinedshipIdList) {

            Friendship friend = friendshipRepository.getFriendshipById(friendshipId);
            friendUsersIds.add( friend.getUserId());
        }

        return friendUsersIds;
    }

    /**
     * Salva os dados de amizades.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError{
        friendshipRepository.saveData();
    }

    /**
     * Limpa os dados de amizades.
     */
    public void resetData(){
        friendshipRepository.resetData();
    }

}
