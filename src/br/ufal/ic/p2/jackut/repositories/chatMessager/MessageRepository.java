package br.ufal.ic.p2.jackut.repositories.chatMessager;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecados;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.Message;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.Optional;
import java.util.Collection;

/**
 * Reposit�rio respons�vel por persistir e recuperar mensagens.
 */
public class MessageRepository extends AbstractRepository<Message> {

    private static MessageRepository instance;

    /**
     * Cria o reposit�rio de mensagens.
     *
     * @throws FileError se ocorrer falha ao carregar mensagens persistidas.
     * @throws SaveError se a infraestrutura de persist�ncia n�o puder ser preparada.
     */
    private MessageRepository() throws FileError, SaveError {
        super(XMLController.getInstance(),"messages.xml");
    }

    /**
     * Retorna a inst�ncia �nica do reposit�rio de mensagens.
     *
     * @return inst�ncia compartilhada do reposit�rio.
     * @throws SaveError se a infraestrutura de persist�ncia n�o puder ser preparada.
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
     * @throws NaoHaRecados se a mensagem n�o for encontrada.
     */
    public Message getMessageById(String messageId) throws NaoHaRecados{
        return Optional.ofNullable(
                entityMap.get(messageId)).orElseThrow(NaoHaRecados::new);
    }

    /**
     * Remove mensagens pelos respectivos identificadores.
     *
     * @param messageIds identificadores das mensagens removidas.
     */
    public void deleteMessages(Collection<String> messageIds) {
        messageIds.forEach(entityMap::remove);
    }

}
