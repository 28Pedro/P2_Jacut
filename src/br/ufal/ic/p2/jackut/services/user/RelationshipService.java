package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.enums.RelationshipState;
import br.ufal.ic.p2.jackut.enums.RelationshipType;
import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.user.relationship.CrushRelationship;
import br.ufal.ic.p2.jackut.models.user.relationship.EnemyRelationship;
import br.ufal.ic.p2.jackut.models.user.relationship.FanRelationship;
import br.ufal.ic.p2.jackut.models.user.relationship.FriendshipRelationship;
import br.ufal.ic.p2.jackut.models.user.relationship.Relationship;
import br.ufal.ic.p2.jackut.repositories.users.RelationshipRepository;
import br.ufal.ic.p2.jackut.services.user.relationshipStateHandles.CurrentRelationshipState;
import br.ufal.ic.p2.jackut.services.user.relationshipStateHandles.NoRelationshipState;
import br.ufal.ic.p2.jackut.services.user.relationshipStateHandles.ReceivedRequestRelationshipState;
import br.ufal.ic.p2.jackut.services.user.relationshipStateHandles.RelationshipStateHandle;
import br.ufal.ic.p2.jackut.services.user.relationshipStateHandles.SentRequestRelationshipState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável pelas regras dos relacionamentos de usuários.
 */
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;

    /**
     * Cria o servico de relacionamentos.
     *
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     * @throws SaveError se a infraestrutura de persistencia nao puder ser preparada.
     */
    public RelationshipService() throws FileError, SaveError {
        this.relationshipRepository = RelationshipRepository.getInstance();
    }

    /**
     * Cria as estruturas de todos os tipos de relacionamento para um usuario.
     *
     * @param userId identificador do usuario.
     */
    public void buildRelationshipObjects(String userId) {
        relationshipRepository.saveRelationship(
                new FriendshipRelationship(userId, UUID.randomUUID().toString()));
        relationshipRepository.saveRelationship(
                new FanRelationship(userId, UUID.randomUUID().toString()));
        relationshipRepository.saveRelationship(
                new CrushRelationship(userId, UUID.randomUUID().toString()));
        relationshipRepository.saveRelationship(
                new EnemyRelationship(userId, UUID.randomUUID().toString()));
    }

    /**
     * Cria ou atualiza um relacionamento entre dois usuarios.
     *
     * @param userId identificador do usuario que inicia a acao.
     * @param relatedUserId identificador do usuario relacionado.
     * @param type tipo de relacionamento.
     * @throws UsuarioNaoCadastrado se alguma estrutura de relacionamento nao existir.
     * @throws AdicionarASiMesmoRelationship se os identificadores forem iguais.
     * @throws UsuarioJaAdicionadoRelationship se o relacionamento ja existir.
     * @throws EsperandoAceitacaoRelationship se uma solicitacao de amizade estiver pendente.
     */
    public void addRelationship(String userId, String relatedUserId, RelationshipType type)
            throws UsuarioNaoCadastrado, AdicionarASiMesmoRelationship,
            UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship {

        validateSelfRelationship(userId, relatedUserId, type);

        if (type == RelationshipType.FRIENDSHIP) {
            addBidirectionalRelationship(userId, relatedUserId);
            return;
        }

        addSimpleRelationship(userId, relatedUserId, type);
    }

    /**
     * Verifica a existencia de um relacionamento atual.
     *
     * @param userId identificador do usuario consultado.
     * @param relatedUserId identificador do usuario relacionado.
     * @param type tipo de relacionamento.
     * @return {@code true} quando o relacionamento estiver ativo.
     * @throws UsuarioNaoCadastrado se a estrutura do usuario nao existir.
     */
    public boolean hasRelationship(String userId, String relatedUserId, RelationshipType type)
            throws UsuarioNaoCadastrado {
        Relationship relationship = relationshipRepository.getRelationshipByUserId(userId, type);
        return relationship.contains(relatedUserId, RelationshipState.CURRENT);
    }

    /**
     * Retorna os usuarios relacionados de um tipo.
     *
     * @param userId identificador do usuario consultado.
     * @param type tipo de relacionamento.
     * @return identificadores dos usuarios relacionados.
     * @throws UsuarioNaoCadastrado se a estrutura do usuario nao existir.
     */
    public List<String> getRelatedUsers(String userId, RelationshipType type)
            throws UsuarioNaoCadastrado {
        Relationship relationship = relationshipRepository.getRelationshipByUserId(userId, type);
        return new ArrayList<>(relationship.getStateList(RelationshipState.CURRENT));
    }

    /**
     * Retorna usuarios que possuem relacionamento ativo com o alvo informado.
     *
     * @param relatedUserId identificador do usuario alvo.
     * @param type tipo de relacionamento.
     * @return identificadores dos usuarios relacionados ao alvo.
     */
    public List<String> getReverseRelatedUsers(String relatedUserId, RelationshipType type) {
        List<String> users = new ArrayList<>();

        for (Relationship relationship : relationshipRepository.getRelationshipsByType(type)) {
            if (relationship.contains(relatedUserId, RelationshipState.CURRENT)) {
                users.add(relationship.getUserId());
            }
        }

        return users;
    }

    /**
     * Verifica se uma interacao e bloqueada pela relacao de inimizade.
     *
     * @param userId identificador do usuario que inicia a interacao.
     * @param targetUserId identificador do usuario alvo.
     * @return {@code true} se o alvo marcou o usuario como inimigo.
     * @throws UsuarioNaoCadastrado se a estrutura do alvo nao existir.
     */
    public boolean isBlockedByEnemy(String userId, String targetUserId)
            throws UsuarioNaoCadastrado {
        return hasRelationship(targetUserId, userId, RelationshipType.ENEMY);
    }

    /**
     * Persiste os relacionamentos.
     *
     * @throws SaveError se ocorrer falha na gravacao.
     */
    public void saveData() throws SaveError {
        relationshipRepository.saveData();
    }

    /**
     * Limpa os relacionamentos persistidos e em memoria.
     */
    public void resetData() {
        relationshipRepository.resetData();
    }

    private void addSimpleRelationship(String userId, String relatedUserId, RelationshipType type)
            throws UsuarioNaoCadastrado, UsuarioJaAdicionadoRelationship {
        Relationship relationship = relationshipRepository.getRelationshipByUserId(userId, type);

        if (relationship.contains(relatedUserId, RelationshipState.CURRENT)) {
            throw new UsuarioJaAdicionadoRelationship(type);
        }

        relationship.addState(RelationshipState.CURRENT, relatedUserId);
    }

    private void addBidirectionalRelationship(String userId, String friendUserId)
            throws UsuarioNaoCadastrado, UsuarioJaAdicionadoRelationship,
            EsperandoAceitacaoRelationship {
        Relationship user = relationshipRepository.getRelationshipByUserId(
                userId, RelationshipType.FRIENDSHIP);
        Relationship friend = relationshipRepository.getRelationshipByUserId(
                friendUserId, RelationshipType.FRIENDSHIP);

        RelationshipStateHandle stateHandle = getRelationshipStateHandle(user, friendUserId);
        stateHandle.addRelationship(user, friend);
    }

    private RelationshipStateHandle getRelationshipStateHandle(
            Relationship user, String relatedUserId) {

        if (user.contains(relatedUserId, RelationshipState.CURRENT)) {
            return new CurrentRelationshipState();
        }

        if (user.contains(relatedUserId, RelationshipState.SENT)) {
            return new SentRequestRelationshipState();
        }

        if (user.contains(relatedUserId, RelationshipState.REQUESTED)) {
            return new ReceivedRequestRelationshipState();
        }

        return new NoRelationshipState();
    }

    private void validateSelfRelationship(String userId, String relatedUserId, RelationshipType type)
            throws AdicionarASiMesmoRelationship {
        if (userId.equals(relatedUserId)) {
            throw new AdicionarASiMesmoRelationship(type);
        }
    }
}
