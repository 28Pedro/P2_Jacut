package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.users.UserRepository;

import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras de negócio de contas de usuário.
 */
public class UserService {

    private final UserRepository userRepository;

    /**
     * Cria o serviço de usuários.
     *
     * @throws FileError se ocorrer falha ao carregar os dados de usuários.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    public UserService() throws FileError,SaveError {
        this.userRepository = UserRepository.getInstance();
    }

    /**
     * Cria uma conta de usuário.
     *
     * @param userName login único do usuário.
     * @param password senha do usuário.
     * @return identificador único do usuário criado.
     * @throws LoginInvalido se o login estiver vazio ou inválido.
     * @throws SenhaInvalida se a senha estiver vazia ou inválida.
     * @throws ContaComEsseNomeJaExiste se já existir usuário com o login informado.
     */
  public String CreateUser(String userName, String password)
  throws LoginInvalido, SenhaInvalida, ContaComEsseNomeJaExiste {

        if(fieldIsEmpty(userName)){
            throw new LoginInvalido();
        }

        if(fieldIsEmpty(password)){
            throw new SenhaInvalida();
        }

        if(userRepository.UserNameExists(userName)){
            throw new ContaComEsseNomeJaExiste();
        }

        String id = UUID.randomUUID().toString();
        User user = new User(userName, password, id);

        userRepository.saveUser(user,id);

        return id;
    }

    /**
     * Autentica um usuário e abre uma sessão.
     *
     * @param userName login do usuário.
     * @param password senha do usuário.
     * @return identificador do usuário autenticado.
     * @throws LoginOuSenhaInvalidos se login ou senha forem inválidos.
     */
    public String openSession(String userName, String password) throws
            LoginOuSenhaInvalidos{

        if(fieldIsEmpty(userName) || fieldIsEmpty(password)){
            throw new LoginOuSenhaInvalidos();
        }

        try {
            User user = userRepository.getUserByName(userName);
            return user.validateSection(password)
                    .orElseThrow(LoginOuSenhaInvalidos::new);

        } catch (UsuarioNaoCadastrado e) {
            throw new LoginOuSenhaInvalidos();
        }
    }

    /**
     * Constrói uma representação textual de logins a partir de IDs de usuário.
     *
     * @param userIds lista de identificadores de usuários.
     * @return representação textual contendo os logins dos usuários.
     * @throws UsuarioNaoCadastrado se algum ID não corresponder a um usuário cadastrado.
     */
    public String buildUsernameListById(List<String> userIds)
    throws UsuarioNaoCadastrado{

        StringBuilder str = new StringBuilder();
        str.append('{');

        for (int i = 0; i < userIds.size(); i++) {

            User user = userRepository.findUserOrThrow(userIds.get(i));

            str.append(user.getUserName());

            if(i < userIds.size() - 1){
                str.append(',');
            }
        }

        str.append('}');

        return str.toString();
    }

    /**
     * Salva os dados de usuários.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError{
        userRepository.saveData();
    }

    /**
     * Limpa os dados de usuários.
     */
    public void resetData(){
        userRepository.resetData();
    }

    private boolean fieldIsEmpty(String field){
        return field == null || field.isBlank();
    }

}
