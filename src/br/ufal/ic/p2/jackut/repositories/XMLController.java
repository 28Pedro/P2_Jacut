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


/**
 * Controlador de persistência XML da aplicação.
 */
public class XMLController {

    private static XMLController instance;

    private final Path dataFolderPath = Paths.get(System.getProperty("user.dir"), "data");
    private final String FILE_PATH = dataFolderPath.toString() + File.separator;

    /**
     * Cria o controlador XML e garante a existência da pasta de dados.
     *
     * @throws SaveError se a pasta de dados não puder ser criada.
     */
    private XMLController() throws SaveError {
        createDirectoryIfNotExists();
    }

    /**
     * Retorna a instância única do controlador XML.
     *
     * @return instância compartilhada do controlador XML.
     * @throws SaveError se a pasta de dados não puder ser criada.
     */
    public static XMLController getInstance() throws SaveError{
        if(instance == null){
            instance = new XMLController();
        }
        return instance;
    }

    private void createDirectoryIfNotExists() throws SaveError {
        try {
            if (!Files.exists(dataFolderPath)) {
                Files.createDirectories(dataFolderPath);
            }
        } catch (IOException e) {
            throw new SaveError();
        }
    }

    /**
     * Salva um mapa em arquivo XML.
     *
     * @param map mapa a ser persistido.
     * @param fileName nome do arquivo XML.
     * @param <K> tipo da chave do mapa.
     * @param <V> tipo do valor do mapa.
     * @throws SaveError se ocorrer falha durante a escrita do arquivo.
     */
    public <K, V> void saveMapToXML(Map<K, V> map, String fileName) throws SaveError {
        createDirectoryIfNotExists();
        String fullPath = FILE_PATH + fileName;

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fullPath)))) {
            encoder.writeObject(map);
        } catch (FileNotFoundException e) {
            throw new SaveError();
        }
    }

    /**
     * Carrega um mapa previamente salvo em XML.
     *
     * @param fileName nome do arquivo XML.
     * @param <K> tipo da chave do mapa.
     * @param <V> tipo do valor do mapa.
     * @return mapa carregado ou mapa vazio se o arquivo não existir.
     * @throws FileError se ocorrer falha durante a leitura ou decodificação.
     */
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

    /**
     * Remove arquivos de persistência.
     *
     * @param fileNames nomes dos arquivos a serem removidos.
     */
    public void resetFiles(String... fileNames) {
        for (String fileName : fileNames) {
            File file = new File(FILE_PATH + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Retorna o caminho base dos arquivos de dados.
     *
     * @return caminho da pasta de dados com separador final.
     */
    public String getFILE_PATH(){
        return FILE_PATH;
    }
}
