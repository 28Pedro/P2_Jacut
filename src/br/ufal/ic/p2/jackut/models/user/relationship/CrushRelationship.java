package br.ufal.ic.p2.jackut.models.user.relationship;

import br.ufal.ic.p2.jackut.enums.RelationshipType;

/**
 * Relacionamento privado de paquera.
 */
public class CrushRelationship extends Relationship {

    public CrushRelationship() {
        super();
        setType(RelationshipType.CRUSH);
    }

    public CrushRelationship(String userId, String id) {
        super(userId, id, RelationshipType.CRUSH);
    }
}
