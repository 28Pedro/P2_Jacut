package br.ufal.ic.p2.jackut.repositories.users;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.Friendship;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório responsável por persistir e recuperar estruturas de amizade.
 */
public class FriendshipRepository extends AbstractRepository<Friendship> {

    private Map<String,String> friendshipByUserId;
    private static FriendshipRepository instance;

    /**
     * Cria o repositório de amizades e reconstrói o índice por usuário.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar amizades persistidas.
     */
    private FriendshipRepository() throws SaveError, FileError {
        super(XMLController.getInstance(),"friendship.xml");

        this.friendshipByUserId = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach(((id, friendship) ->{
                friendshipByUserId.put(friendship.getUserId(),id);
            } ));
        }
    }

    /**
     * Retorna a instância única do repositório de amizades.
     *
     * @return instância compartilhada do repositório.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar amizades persistidas.
     */
    public static FriendshipRepository getInstance() throws SaveError, FileError{
        if(instance == null){
            instance = new FriendshipRepository();
        }
        return instance;
    }

    /**
     * Salva uma estrutura de amizade e atualiza o índice por usuário.
     *
     * @param friendship estrutura de amizade salva.
     */
    public void saveFriendship(Friendship friendship){
        String id = friendship.getId();
        addObject(id,friendship);

        friendshipByUserId.put(friendship.getUserId(),id);
    }

    /**
     * Recupera uma estrutura de amizade por ID.
     *
     * @param friendshipId identificador da estrutura de amizade.
     * @return estrutura de amizade encontrada.
     * @throws UsuarioNaoCadastrado se a estrutura não for encontrada.
     */
    public Friendship getFriendshipById(String friendshipId) throws
            UsuarioNaoCadastrado{

        return Optional.
                ofNullable(entityMap.get(friendshipId)).
                orElseThrow(UsuarioNaoCadastrado::new);
    }

    /**
     * Recupera a estrutura de amizade associada a um usuário.
     *
     * @param userId identificador do usuário dono da estrutura.
     * @return estrutura de amizade encontrada.
     * @throws UsuarioNaoCadastrado se a estrutura não for encontrada.
     */
    public Friendship getFriendshipByUserId(String userId) throws
            UsuarioNaoCadastrado{
        return getFriendshipById(friendshipByUserId.get(userId));
    }

    /**
     * Limpa amizades e índice por usuário.
     */
    @Override
    public void resetData(){
        super.resetData();
        friendshipByUserId.clear();
    }

}
