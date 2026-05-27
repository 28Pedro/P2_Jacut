package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;

import java.lang.reflect.InaccessibleObjectException;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractRepository<T> {

    protected final Map<String, T> entityMap;
    protected final XMLController xmlController;
    protected final String fileName;

    public AbstractRepository(XMLController xmlController, String fileName) throws FileError {
        this.xmlController = xmlController;
        this.fileName = fileName;
        this.entityMap = xmlController.loadMapFromXML(fileName);
    }

    public void addObject(String id, T object){
        entityMap.put(id,object);
    }

    public Optional<T> getObject(String id) {
        return Optional.ofNullable(entityMap.get(id));
    }

    public void removeObject(String id) throws InaccessibleObjectException{
        if(!entityMap.containsKey(id)){
            throw new InaccessibleObjectException();
        }
        entityMap.remove(id);
    }

    public void saveData() throws SaveError {
        xmlController.saveMapToXML(entityMap, fileName);
    }


    public void resetData(){
        xmlController.resetFiles(fileName);
    }
}
