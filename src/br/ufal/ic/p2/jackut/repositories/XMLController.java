package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class XMLController {

    private static XMLController instance;

    private final Path dataFolderPath = Paths.get(System.getProperty("user.dir"), "data");
    private final String FILE_PATH = dataFolderPath.toString() + File.separator;

    private XMLController() throws SaveError {
        createDirectoryIfNotExists();
    }

    public static XMLController getInstance() throws SaveError{
        if(instance == null){
            instance = new XMLController();
        }
        return instance;
    }

    private void createDirectoryIfNotExists() throws SaveError {
        try {
            // Files.createDirectories cria a pasta "data" caso ela ainda não exista
            if (!Files.exists(dataFolderPath)) {
                Files.createDirectories(dataFolderPath);
            }
        } catch (IOException e) {
            throw new SaveError();
        }
    }

    // 3. Agora passamos apenas o nome do arquivo (ex: "clientes.xml"), o Controller cuida do caminho
    public <K, V> void saveMapToXML(Map<K, V> map, String fileName) throws SaveError {
        createDirectoryIfNotExists(); // Garantia extra de que a pasta existe antes de salvar
        String fullPath = FILE_PATH + fileName;

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fullPath)))) {
            encoder.writeObject(map);
        } catch (FileNotFoundException e) {
            throw new SaveError(); // Assegure-se de que essa classe existe no pacote exceptions
        }
    }

    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> loadMapFromXML(String fileName) throws FileError {
        String fullPath = FILE_PATH + fileName;
        File file = new File(fullPath);

        if (!file.exists() || file.length() == 0) {
            return new HashMap<>();
        }

        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)))) {
            Object data = decoder.readObject();
            return (Map<K, V>) data;
        } catch (Exception e) {
            throw new FileError(fullPath);
        }
    }

    public void resetFiles(String... fileNames) {
        for (String fileName : fileNames) {
            File file = new File(FILE_PATH + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public String getFILE_PATH(){
        return FILE_PATH;
    }
}
