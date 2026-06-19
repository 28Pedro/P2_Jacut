package br.ufal.ic.p2.jackut.enums;

/**
 * Tipos de relacionamento aceitos pela rede social.
 */
public enum RelationshipType {
    FRIENDSHIP("amigo", "amigo", "adicionar a si mesmo como amigo"),
    FAN("ídolo", "fã", "fã"),
    CRUSH("paquera", "paquera", "paquera"),
    ENEMY("inimigo", "inimigo", "inimigo");

    private final String addedAsWord;
    private final String roleWord;
    private final String selfRelationshipPhrase;

    RelationshipType(String addedAsWord, String roleWord, String selfRelationshipPhrase) {
        this.addedAsWord = addedAsWord;
        this.roleWord = roleWord;
        this.selfRelationshipPhrase = selfRelationshipPhrase;
    }

    public String getAddedAsWord() {
        return addedAsWord;
    }

    public String getRoleWord() {
        return roleWord;
    }

    public String getSelfRelationshipPhrase() {
        return selfRelationshipPhrase;
    }
}
