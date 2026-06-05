package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.User;
import br.ufal.ic.p2.jackut.repositories.users.UserRepository;

import java.util.Optional;

/**
 * Serviço auxiliar para integração com o repositório de usuários.
 *
 * <p>Centraliza a conversão de login em identificador interno, evitando que
 * controladores de outros domínios acessem diretamente o repositório de
 * usuários.</p>
 */
public class UserIntegrator {


   private static UserIntegrator instance;
   private final UserRepository userRepository;


   /**
    * Cria o integrador de usuários.
    *
    * @throws FileError se ocorrer falha ao carregar dados de usuários.
    * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
    */
   private UserIntegrator() throws FileError, SaveError {
       this.userRepository = UserRepository.getInstance();
   }

   /**
    * Retorna a instância única do integrador de usuários.
    *
    * @return instância compartilhada do integrador.
    * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
    * @throws FileError se ocorrer falha ao carregar dados de usuários.
    */
   public static UserIntegrator getInstance() throws SaveError, FileError{

       if(instance == null){
           instance = new UserIntegrator();
       }

       return instance;
   }

    /**
     * Recupera o identificador interno de um usuário a partir do login.
     *
     * @param userName login do usuário.
     * @return identificador interno do usuário.
     * @throws UsuarioNaoCadastrado se não existir usuário com o login informado.
     */
    public String getUserByName(String userName) throws UsuarioNaoCadastrado {
       User user = userRepository.getUserByName(userName);
        return user.getId();
    }
}
