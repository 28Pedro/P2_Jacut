package br.ufal.ic.p2.jackut.services.user.relationshipStateHandles;

import br.ufal.ic.p2.jackut.exceptions.EsperandoAceitacaoRelationship;
import br.ufal.ic.p2.jackut.exceptions.UsuarioJaAdicionadoRelationship;
import br.ufal.ic.p2.jackut.models.user.relationship.Relationship;

/**
 * Contrato para tratar a inclusão de um relacionamento conforme seu estado atual.
 */
public interface RelationshipStateHandle {

    void addRelationship(Relationship user, Relationship relatedUser)
            throws UsuarioJaAdicionadoRelationship, EsperandoAceitacaoRelationship;
}
