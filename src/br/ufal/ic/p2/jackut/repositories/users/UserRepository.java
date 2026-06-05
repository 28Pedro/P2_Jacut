package br.ufal.ic.p2.jackut.repositories.users;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Map;

/**
 * Repositório responsável por persistir e recuperar usuários.
 */
public class UserRepository extends AbstractRepository<User> {

    private Map<String, String> userByUserName;
    private static UserRepository instance;

    /**
     * Cria o repositório de usuários e reconstrói o índice por login.
     *
     * @throws FileError se ocorrer falha ao carregar usuários persistidos.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    private UserRepository() throws FileError,SaveError {
        super(XMLController.getInstance(),"user.xml");

        userByUserName = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach((id,user) -> {
                userByUserName.put(user.getUserName(),user.getId());
            } );
        }
    }

    /**
     * Retorna a instância única do repositório de usuários.
     *
     * @return instância compartilhada do repositório.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar usuários persistidos.
     */
    public static UserRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    /**
     * Salva um usuário e atualiza o índice por login.
     *
     * @param user usuário salvo.
     * @param id identificador do usuário.
     */
    public void saveUser(User user, String id){
        userByUserName.put(user.getUserName(), user.getId());
        addObject(id, user);
    }

    /**
     * Recupera um usuário por ID ou lança exceção.
     *
     * @param userId identificador do usuário.
     * @return usuário encontrado.
     * @throws UsuarioNaoCadastrado se o usuário não existir.
     */
    public User findUserOrThrow(String userId) throws UsuarioNaoCadastrado {
        return getObject(userId)
                .orElseThrow(UsuarioNaoCadastrado::new);
    }

    /**
     * Verifica se já existe usuário com determinado login.
     *
     * @param userName login consultado.
     * @return {@code true} se o login já estiver cadastrado.
     */
    public boolean UserNameExists(String userName){
        return userByUserName.containsKey(userName);
    }

    /**
     * Recupera um usuário pelo login.
     *
     * @param userName login do usuário.
     * @return usuário encontrado.
     * @throws UsuarioNaoCadastrado se o login não estiver cadastrado.
     */
    public User getUserByName(String userName) throws UsuarioNaoCadastrado{

        return findUserOrThrow(userByUserName.get(userName));
    }

    /**
     * Limpa usuários e índice por login.
     */
    @Override
    public void resetData(){
        super.resetData();
        userByUserName.clear();
    }

}
