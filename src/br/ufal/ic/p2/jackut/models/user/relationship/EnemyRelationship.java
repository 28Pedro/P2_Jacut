package br.ufal.ic.p2.jackut.models.user.relationship;

import br.ufal.ic.p2.jackut.enums.RelationshipType;

/**
 * Relacionamento usado para registrar inimizade.
 */
public class EnemyRelationship extends Relationship {

    public EnemyRelationship() {
        super();
        setType(RelationshipType.ENEMY);
    }

    public EnemyRelationship(String userId, String id) {
        super(userId, id, RelationshipType.ENEMY);
    }
}
