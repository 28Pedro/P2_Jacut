package br.ufal.ic.p2.jackut.repositories.chatMessager;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecados;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.Message;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.Optional;

public class MessageRepository extends AbstractRepository<Message> {

    private static MessageRepository instance;

    private MessageRepository() throws FileError, SaveError {
        super(XMLController.getInstance(),"messages.xml");
    }

    public static MessageRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new MessageRepository();
        }
        return instance;
    }

    public void saveMessage(Message message){
        addObject(message.getId(), message);
    }

    public Message getMessageById(String messageId) throws NaoHaRecados{
        return Optional.ofNullable(
                entityMap.get(messageId)).orElseThrow(NaoHaRecados::new);
    }

}
