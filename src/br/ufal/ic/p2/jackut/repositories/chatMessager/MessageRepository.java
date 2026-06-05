package br.ufal.ic.p2.jackut.repositories.chatMessager;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecados;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.Message;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.Optional;

/**
 * Repositório responsável por persistir e recuperar mensagens.
 */
public class MessageRepository extends AbstractRepository<Message> {

    private static MessageRepository instance;

    /**
     * Cria o repositório de mensagens.
     *
     * @throws FileError se ocorrer falha ao carregar mensagens persistidas.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    private MessageRepository() throws FileError, SaveError {
        super(XMLController.getInstance(),"messages.xml");
    }

    /**
     * Retorna a instância única do repositório de mensagens.
     *
     * @return instância compartilhada do repositório.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar mensagens persistidas.
     */
    public static MessageRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new MessageRepository();
        }
        return instance;
    }

    /**
     * Salva uma mensagem.
     *
     * @param message mensagem a ser salva.
     */
    public void saveMessage(Message message){
        addObject(message.getId(), message);
    }

    /**
     * Recupera uma mensagem por ID.
     *
     * @param messageId identificador da mensagem.
     * @return mensagem encontrada.
     * @throws NaoHaRecados se a mensagem não for encontrada.
     */
    public Message getMessageById(String messageId) throws NaoHaRecados{
        return Optional.ofNullable(
                entityMap.get(messageId)).orElseThrow(NaoHaRecados::new);
    }

}
