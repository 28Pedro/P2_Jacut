package br.ufal.ic.p2.jackut.exceptions;

import br.ufal.ic.p2.jackut.enums.RelationshipType;

public class AdicionarASiMesmoRelationship extends Exception {

    public AdicionarASiMesmoRelationship(RelationshipType type) {
        super(buildMessage(type));
    }

    private static String buildMessage(RelationshipType type) {
        if (type == RelationshipType.FRIENDSHIP) {
            return "Usuário não pode adicionar a si mesmo como amigo.";
        }

        return "Usuário não pode ser " + type.getSelfRelationshipPhrase() + " de si mesmo.";
    }
}
