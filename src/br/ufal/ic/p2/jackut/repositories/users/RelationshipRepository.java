package br.ufal.ic.p2.jackut.repositories.users;

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

    public static RelationshipRepository getInstance() throws SaveError, FileError {
        if (instance == null) {
            instance = new RelationshipRepository();
        }
        return instance;
    }

    public void saveRelationship(Relationship relationship) {
        addObject(relationship.getId(), relationship);
        addIndex(relationship);
    }

    public Relationship getRelationshipByUserId(String userId, RelationshipType type)
            throws UsuarioNaoCadastrado {
        Map<RelationshipType, String> userRelationships = relationshipByUserId.get(userId);

        if (userRelationships == null) {
            throw new UsuarioNaoCadastrado();
        }

        return getRelationshipById(userRelationships.get(type));
    }

    public Relationship getRelationshipById(String relationshipId)
            throws UsuarioNaoCadastrado {
        return Optional.ofNullable(entityMap.get(relationshipId))
                .orElseThrow(UsuarioNaoCadastrado::new);
    }

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

    @Override
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
}
