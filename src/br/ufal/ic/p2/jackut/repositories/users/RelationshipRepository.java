package br.ufal.ic.p2.jackut.repositories.users;

import br.ufal.ic.p2.jackut.enums.RelationshipState;
import br.ufal.ic.p2.jackut.enums.RelationshipType;
import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.relationship.Relationship;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório dos relacionamentos dos usuários.
 */
public class RelationshipRepository extends AbstractRepository<Relationship> {

    private Map<String, Map<RelationshipType, String>> relationshipByUserId;
    private Map<RelationshipType, List<String>> relationshipIdsByType;
    private static RelationshipRepository instance;

    private RelationshipRepository() throws SaveError, FileError {
        super(XMLController.getInstance(), "relationship.xml");

        this.relationshipByUserId = new HashMap<>();
        this.relationshipIdsByType = new EnumMap<>(RelationshipType.class);

        if (!entityMap.isEmpty()) {
            entityMap.forEach((id, relationship) -> addIndex(relationship));
        }
    }

    /**
     * Retorna a instancia compartilhada do repositorio.
     *
     * @return repositorio de relacionamentos.
     * @throws SaveError se a infraestrutura de persistencia nao puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     */
    public static RelationshipRepository getInstance() throws SaveError, FileError {
        if (instance == null) {
            instance = new RelationshipRepository();
        }
        return instance;
    }

    /**
     * Salva um relacionamento e atualiza seus indices.
     *
     * @param relationship relacionamento armazenado.
     */
    public void saveRelationship(Relationship relationship) {
        addObject(relationship.getId(), relationship);
        addIndex(relationship);
    }

    /**
     * Recupera o relacionamento de determinado tipo pertencente a um usuario.
     *
     * @param userId identificador do usuario dono.
     * @param type tipo de relacionamento.
     * @return relacionamento encontrado.
     * @throws UsuarioNaoCadastrado se o usuario nao possuir estruturas cadastradas.
     */
    public Relationship getRelationshipByUserId(String userId, RelationshipType type)
            throws UsuarioNaoCadastrado {
        Map<RelationshipType, String> userRelationships = relationshipByUserId.get(userId);

        if (userRelationships == null) {
            throw new UsuarioNaoCadastrado();
        }

        return getRelationshipById(userRelationships.get(type));
    }

    /**
     * Recupera um relacionamento por identificador.
     *
     * @param relationshipId identificador do relacionamento.
     * @return relacionamento encontrado.
     * @throws UsuarioNaoCadastrado se o relacionamento nao existir.
     */
    public Relationship getRelationshipById(String relationshipId)
            throws UsuarioNaoCadastrado {
        return Optional.ofNullable(entityMap.get(relationshipId))
                .orElseThrow(UsuarioNaoCadastrado::new);
    }

    /**
     * Retorna os relacionamentos cadastrados de um tipo.
     *
     * @param type tipo de relacionamento.
     * @return relacionamentos do tipo informado.
     */
    public List<Relationship> getRelationshipsByType(RelationshipType type) {
        List<Relationship> relationships = new ArrayList<>();
        List<String> relationshipIds = relationshipIdsByType.get(type);

        if (relationshipIds == null) {
            return relationships;
        }

        for (String relationshipId : relationshipIds) {
            Relationship relationship = entityMap.get(relationshipId);
            if (relationship != null) {
                relationships.add(relationship);
            }
        }

        return relationships;
    }

    /**
     * Exclui todos os relacionamentos pertencentes a um usuario.
     *
     * @param userId identificador do usuario removido.
     * @throws UsuarioNaoCadastrado se o usuario nao possuir estruturas cadastradas.
     */
    public void deleteRelationshipsByUserId(String userId) throws UsuarioNaoCadastrado {
        Map<RelationshipType, String> userRelationships = relationshipByUserId.get(userId);

        if (userRelationships == null) {
            throw new UsuarioNaoCadastrado();
        }

        for (Map.Entry<RelationshipType, String> entry : userRelationships.entrySet()) {
            entityMap.remove(entry.getValue());
            removeTypeIndex(entry.getKey(), entry.getValue());
        }

        relationshipByUserId.remove(userId);
    }

    /**
     * Remove referencias a um usuario dos relacionamentos remanescentes.
     *
     * @param userId identificador do usuario removido.
     */
    public void removeUserFromAllRelationships(String userId) {
        for (Relationship relationship : entityMap.values()) {
            for (RelationshipState state : RelationshipState.values()) {
                relationship.removeState(state, userId);
            }
        }
    }

    @Override
    /**
     * Limpa os dados e os indices de relacionamentos.
     */
    public void resetData() {
        super.resetData();
        relationshipByUserId.clear();
        relationshipIdsByType.clear();
    }

    private void addIndex(Relationship relationship) {
        relationshipByUserId
                .computeIfAbsent(relationship.getUserId(), id -> new EnumMap<>(RelationshipType.class))
                .put(relationship.getType(), relationship.getId());
        relationshipIdsByType
                .computeIfAbsent(relationship.getType(), type -> new ArrayList<>())
                .add(relationship.getId());
    }

    private void removeTypeIndex(RelationshipType type, String relationshipId) {
        List<String> relationshipIds = relationshipIdsByType.get(type);

        if (relationshipIds != null) {
            relationshipIds.remove(relationshipId);
        }
    }
}
