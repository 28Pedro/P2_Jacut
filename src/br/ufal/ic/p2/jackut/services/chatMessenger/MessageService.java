package br.ufal.ic.p2.jackut.services.chatMessenger;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecados;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.Message;
import br.ufal.ic.p2.jackut.repositories.chatMessager.MessageRepository;

import java.util.UUID;

public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService() throws SaveError, FileError {
        this.messageRepository = MessageRepository.getInstance();
    }

    public String createMessage(String chatMessageId, String content){

        String id =  UUID.randomUUID().toString();
        Message message = new Message(content,chatMessageId,id);
        messageRepository.saveMessage(message);

        return id;
    }

    public String getChatIdByMessage(String messageId) throws NaoHaRecados{
        Message message = messageRepository.getMessageById(messageId);
        return message.getChatMessageId();
    }

    public String showMessage(String messageId) throws NaoHaRecados {
        return messageRepository.getMessageById(messageId).getContent();
    }

    public void saveData() throws SaveError{
        messageRepository.saveData();
    }

    public void resetData(){
        messageRepository.resetData();
    }
}
