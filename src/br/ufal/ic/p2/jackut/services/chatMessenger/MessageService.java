package br.ufal.ic.p2.jackut.services.chatMessenger;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaMensagens;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecados;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.Message;
import br.ufal.ic.p2.jackut.repositories.chatMessager.MessageRepository;

import java.util.UUID;
import java.util.Collection;

/**
 * Serviço responsável pelas regras de criação, consulta e persistência de mensagens.
 */
public class MessageService {

    private final MessageRepository messageRepository;

    /**
     * Cria o serviço de mensagens.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar mensagens persistidas.
     */
    public MessageService() throws SaveError, FileError {
        this.messageRepository = MessageRepository.getInstance();
    }

    /**
     * Cria uma nova mensagem associada a um chat.
     *
     * @param chatMessageId identificador do chat ao qual a mensagem pertence.
     * @param content conteúdo textual da mensagem.
     * @return identificador único da mensagem criada.
     */
    public String createMessage(String chatMessageId, String content){

        String id =  UUID.randomUUID().toString();
        Message message = new Message(content,chatMessageId,id);
        messageRepository.saveMessage(message);

        return id;
    }

    /**
     * Recupera o identificador do chat associado a uma mensagem privada.
     *
     * @param messageId identificador da mensagem.
     * @return identificador do chat da mensagem.
     * @throws NaoHaRecados se a mensagem não for encontrada.
     */
    public String getChatIdByMessage(String messageId) throws NaoHaRecados{
        Message message = messageRepository.getMessageById(messageId);
        return message.getChatMessageId();
    }

    /**
     * Recupera o identificador do chat associado a uma mensagem de comunidade.
     *
     * @param messageId identificador da mensagem.
     * @return identificador do chat da mensagem.
     * @throws NaoHaMensagens se a mensagem não for encontrada.
     */
    public String getCommunityChatIdByMessage(String messageId) throws NaoHaMensagens{
        try {
            Message message = messageRepository.getMessageById(messageId);
            return message.getChatMessageId();
        } catch (NaoHaRecados e) {
            throw new NaoHaMensagens();
        }
    }

    /**
     * Recupera o conteúdo textual de uma mensagem privada.
     *
     * @param messageId identificador da mensagem.
     * @return conteúdo textual da mensagem.
     * @throws NaoHaRecados se a mensagem não for encontrada.
     */
    public String showMessage(String messageId) throws NaoHaRecados {
        return messageRepository.getMessageById(messageId).getContent();
    }

    /**
     * Recupera o conteúdo textual de uma mensagem de comunidade.
     *
     * @param messageId identificador da mensagem.
     * @return conteúdo textual da mensagem.
     * @throws NaoHaMensagens se a mensagem não for encontrada.
     */
    public String showCommunityMessage(String messageId) throws NaoHaMensagens {
        try {
            return messageRepository.getMessageById(messageId).getContent();
        } catch (NaoHaRecados e) {
            throw new NaoHaMensagens();
        }
    }

    /**
     * Exclui mensagens que não podem mais ser entregues.
     *
     * @param messageIds identificadores das mensagens removidas.
     */
    public void deleteMessages(Collection<String> messageIds) {
        messageRepository.deleteMessages(messageIds);
    }

    /**
     * Salva os dados de mensagens.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError{
        messageRepository.saveData();
    }

    /**
     * Limpa os dados de mensagens.
     */
    public void resetData(){
        messageRepository.resetData();
    }
}
