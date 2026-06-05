package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;

import java.lang.reflect.InaccessibleObjectException;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório base genérico para entidades persistidas em XML.
 *
 * @param <T> tipo da entidade armazenada pelo repositório.
 */
public abstract class AbstractRepository<T> {

    protected final Map<String, T> entityMap;
    protected final XMLController xmlController;
    protected final String fileName;

    /**
     * Cria o repositório carregando o mapa persistido no arquivo informado.
     *
     * @param xmlController controlador XML usado para leitura e escrita.
     * @param fileName nome do arquivo de persistência.
     * @throws FileError se ocorrer falha ao carregar os dados do arquivo.
     */
    public AbstractRepository(XMLController xmlController, String fileName) throws FileError {
        this.xmlController = xmlController;
        this.fileName = fileName;
        this.entityMap = xmlController.loadMapFromXML(fileName);
    }

    /**
     * Adiciona ou substitui uma entidade no mapa.
     *
     * @param id identificador da entidade.
     * @param object entidade armazenada.
     */
    public void addObject(String id, T object){
        entityMap.put(id,object);
    }

    /**
     * Recupera uma entidade pelo identificador.
     *
     * @param id identificador da entidade.
     * @return entidade encontrada, ou vazio se não existir.
     */
    public Optional<T> getObject(String id) {
        return Optional.ofNullable(entityMap.get(id));
    }

    /**
     * Remove uma entidade pelo identificador.
     *
     * @param id identificador da entidade removida.
     * @throws InaccessibleObjectException se o identificador não existir no mapa.
     */
    public void removeObject(String id) throws InaccessibleObjectException{
        if(!entityMap.containsKey(id)){
            throw new InaccessibleObjectException();
        }
        entityMap.remove(id);
    }

    /**
     * Salva o mapa de entidades no arquivo XML associado ao repositório.
     *
     * @throws SaveError se ocorrer falha durante a gravação.
     */
    public void saveData() throws SaveError {
        xmlController.saveMapToXML(entityMap, fileName);
    }


    /**
     * Remove o arquivo persistido e limpa o mapa em memória.
     */
    public void resetData(){
        xmlController.resetFiles(fileName);
        entityMap.clear();
    }
}
