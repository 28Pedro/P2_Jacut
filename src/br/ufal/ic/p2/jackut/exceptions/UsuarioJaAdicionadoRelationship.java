package br.ufal.ic.p2.jackut.exceptions;

import br.ufal.ic.p2.jackut.enums.RelationshipType;

public class UsuarioJaAdicionadoRelationship extends Exception {

    public UsuarioJaAdicionadoRelationship(RelationshipType type) {
        super("Usuário já está adicionado como " + type.getAddedAsWord() + ".");
    }
}
