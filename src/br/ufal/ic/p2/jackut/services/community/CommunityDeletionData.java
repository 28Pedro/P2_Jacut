package br.ufal.ic.p2.jackut.services.community;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transporta os vínculos de chat afetados pela remoção de um usuário das comunidades.
 */
public class CommunityDeletionData {

    private final Set<String> communityChatsToKeep;

    /**
     * Cria o resultado da remoção em comunidades.
     *
     * @param communityChatsToKeep chats de comunidades existentes dos quais o usuário saiu.
     */
    public CommunityDeletionData(Set<String> communityChatsToKeep) {
        this.communityChatsToKeep = new LinkedHashSet<>(communityChatsToKeep);
    }

    /**
     * Retorna os chats que continuam existentes e dos quais o usuário deve ser removido.
     *
     * @return identificadores de chats de comunidades preservadas.
     */
    public Set<String> getCommunityChatsToKeep() {
        return Collections.unmodifiableSet(communityChatsToKeep);
    }
}
