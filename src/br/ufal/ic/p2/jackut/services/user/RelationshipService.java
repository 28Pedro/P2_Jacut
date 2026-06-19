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

    public RelationshipService() throws FileError, SaveError {
        this.relationshipRepository = RelationshipRepository.getInstance();
    }

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

    public boolean hasRelationship(String userId, String relatedUserId, RelationshipType type)
            throws UsuarioNaoCadastrado {
        Relationship relationship = relationshipRepository.getRelationshipByUserId(userId, type);
        return relationship.contains(relatedUserId, RelationshipState.CURRENT);
    }

    public List<String> getRelatedUsers(String userId, RelationshipType type)
            throws UsuarioNaoCadastrado {
        Relationship relationship = relationshipRepository.getRelationshipByUserId(userId, type);
        return new ArrayList<>(relationship.getStateList(RelationshipState.CURRENT));
    }

    public List<String> getReverseRelatedUsers(String relatedUserId, RelationshipType type) {
        List<String> users = new ArrayList<>();

        for (Relationship relationship : relationshipRepository.getRelationshipsByType(type)) {
            if (relationship.contains(relatedUserId, RelationshipState.CURRENT)) {
                users.add(relationship.getUserId());
            }
        }

        return users;
    }

    public boolean isBlockedByEnemy(String userId, String targetUserId)
            throws UsuarioNaoCadastrado {
        return hasRelationship(targetUserId, userId, RelationshipType.ENEMY);
    }

    public void saveData() throws SaveError {
        relationshipRepository.saveData();
    }

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
