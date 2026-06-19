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

    /**
     * Retorna a palavra usada quando o relacionamento e adicionado.
     *
     * @return palavra associada ao tipo.
     */
    public String getAddedAsWord() {
        return addedAsWord;
    }

    /**
     * Retorna o nome do papel exercido no relacionamento.
     *
     * @return nome do papel.
     */
    public String getRoleWord() {
        return roleWord;
    }

    /**
     * Retorna a descricao usada para tentativa de relacionamento consigo mesmo.
     *
     * @return descricao do tipo de relacionamento.
     */
    public String getSelfRelationshipPhrase() {
        return selfRelationshipPhrase;
    }
}
