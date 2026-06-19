package br.ufal.ic.p2.jackut.services.user.relationshipStateHandles;

import br.ufal.ic.p2.jackut.enums.RelationshipState;
import br.ufal.ic.p2.jackut.models.user.relationship.Relationship;

/**
 * Estado usado quando o usuário recebeu um convite e a nova chamada confirma a amizade.
 */
public class ReceivedRequestRelationshipState implements RelationshipStateHandle {

    @Override
    public void addRelationship(Relationship user, Relationship relatedUser) {
        user.removeState(RelationshipState.REQUESTED, relatedUser.getUserId());
        relatedUser.removeState(RelationshipState.SENT, user.getUserId());
        user.addState(RelationshipState.CURRENT, relatedUser.getUserId());
        relatedUser.addState(RelationshipState.CURRENT, user.getUserId());
    }
}
