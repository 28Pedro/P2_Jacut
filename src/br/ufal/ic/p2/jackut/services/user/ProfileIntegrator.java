package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.repositories.users.ProfileRepository;

/**
 * Integrador para operações externas sobre perfis.
 */
public class ProfileIntegrator {

    private static ProfileIntegrator instance;
    private final ProfileRepository profileRepository;

    private ProfileIntegrator() throws FileError, SaveError {
        this.profileRepository = ProfileRepository.getInstance();
    }

    /**
     * Retorna a instancia compartilhada do integrador de perfis.
     *
     * @return integrador de perfis.
     * @throws SaveError se a infraestrutura de persistencia nao puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     */    public static ProfileIntegrator getInstance() throws SaveError, FileError {
        if (instance == null) {
            instance = new ProfileIntegrator();
        }

        return instance;
    }

    /**
     * Remove o perfil associado a um usuario.
     *
     * @param userId identificador do usuario removido.
     * @throws UsuarioNaoCadastrado se o perfil nao estiver cadastrado.
     */    public void deleteProfile(String userId) throws UsuarioNaoCadastrado {
        profileRepository.deleteProfileByUserId(userId);
    }
}
