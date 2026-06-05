package br.ufal.ic.p2.jackut.services.chatMessenger;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecados;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatMessenger;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatParticipantsKey;
import br.ufal.ic.p2.jackut.repositories.chatMessager.ChatMessengerRepository;

import java.util.*;

/**
 * Serviço responsável pelas regras de negócio de chats e estados de leitura.
 */
public class ChatMessengerService {

    private final ChatMessengerRepository chatMessengerRepository;

    /**
     * Cria o serviço de chats.
     *
     * @throws FileError se ocorrer falha ao carregar chats persistidos.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    public ChatMessengerService() throws FileError, SaveError {
        this.chatMessengerRepository = ChatMessengerRepository.getInstance();
    }

    /**
     * Registra uma mensagem em um chat para todos os destinatários.
     *
     * @param messageId identificador da mensagem enviada.
     * @param senderId identificador do usuário remetente.
     * @param chatMessenger chat em que a mensagem será registrada.
     * @return lista de identificadores dos usuários que devem ser notificados.
     */
    public List<String> SendMessenger(String messageId, String senderId, ChatMessenger chatMessenger){

        chatMessenger.sendMessenger(messageId, senderId);

        return chatMessenger.getUsersId().getUserList()
                .stream()
                .filter(userId -> !userId.equals(senderId))
                .toList();
    }

    /**
     * Lê a próxima mensagem não lida de um usuário em um chat.
     *
     * @param chatMessengerId identificador do chat.
     * @param receiverId identificador do usuário leitor.
     * @return identificador da mensagem lida.
     * @throws NaoHaRecados se o chat não existir ou não houver mensagens não lidas.
     */
    public String receiveMessenger(String chatMessengerId, String receiverId)
            throws NaoHaRecados{

        Optional<ChatMessenger> chatMessengerO =
                chatMessengerRepository.getObject(chatMessengerId);
       ChatMessenger chatMessenger = chatMessengerO.orElseThrow(NaoHaRecados::new);

       return chatMessenger.readMessage(receiverId).orElseThrow(NaoHaRecados::new);
    }

    /**
     * Salva os dados de chats.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError{
        chatMessengerRepository.saveData();
    }

    /**
     * Limpa os dados de chats.
     */
    public void resetData(){
        chatMessengerRepository.resetData();
    }

    /**
     * Recupera um chat existente ou cria um novo chat entre dois usuários.
     *
     * @param senderId identificador do usuário remetente.
     * @param receiverId identificador do usuário destinatário.
     * @return chat existente ou recém-criado para os participantes.
     */
    public ChatMessenger getOrBuild(String senderId, String receiverId ){

        ChatParticipantsKey chatParticipantsKey =
                new ChatParticipantsKey(senderId,receiverId);

        Optional<ChatMessenger> chatMessengerO =
                chatMessengerRepository.getChatByUserIds(chatParticipantsKey);

        if(chatMessengerO.isEmpty()){

            String id = UUID.randomUUID().toString();

            ChatMessenger chatMessenger = new ChatMessenger(id,chatParticipantsKey);

            chatMessengerRepository.saveChatMessenger(chatMessenger);

            return chatMessenger;

        } else{
            return chatMessengerO.get();
        }
    }

}
