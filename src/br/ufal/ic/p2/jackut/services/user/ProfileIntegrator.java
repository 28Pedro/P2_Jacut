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

    public static ProfileIntegrator getInstance() throws SaveError, FileError {
        if (instance == null) {
            instance = new ProfileIntegrator();
        }

        return instance;
    }

    public void deleteProfile(String userId) throws UsuarioNaoCadastrado {
        profileRepository.deleteProfileByUserId(userId);
    }
}
