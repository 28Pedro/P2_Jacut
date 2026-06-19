package br.ufal.ic.p2.jackut.services.user.relationshipStateHandles;

import br.ufal.ic.p2.jackut.enums.RelationshipType;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaAdicionadoRelationship;
import br.ufal.ic.p2.jackut.models.user.relationship.Relationship;

/**
 * Estado usado quando a relação já está confirmada.
 */
public class CurrentRelationshipState implements RelationshipStateHandle {

    @Override
    public void addRelationship(Relationship user, Relationship relatedUser)
            throws UsuarioJaAdicionadoRelationship {
        throw new UsuarioJaAdicionadoRelationship(RelationshipType.FRIENDSHIP);
    }
}
