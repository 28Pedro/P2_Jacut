package br.ufal.ic.p2.jackut.services.user.relationshipStateHandles;

import br.ufal.ic.p2.jackut.exceptions.EsperandoAceitacaoRelationship;
import br.ufal.ic.p2.jackut.models.user.relationship.Relationship;

/**
 * Estado usado quando o convite já foi enviado e ainda aguarda aceite.
 */
public class SentRequestRelationshipState implements RelationshipStateHandle {

    @Override
    public void addRelationship(Relationship user, Relationship relatedUser)
            throws EsperandoAceitacaoRelationship {
        throw new EsperandoAceitacaoRelationship();
    }
}
