package br.ufal.ic.p2.jackut.services.chatMessenger;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaMensagens;
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
     * Registra uma mensagem privada em um chat para todos os destinatários.
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
     * Registra uma mensagem de comunidade para todos os participantes do chat.
     *
     * @param messageId identificador da mensagem enviada.
     * @param chatMessengerId identificador do chat da comunidade.
     * @return lista de identificadores dos usuários que devem ser notificados.
     */
    public List<String> sendCommunityMessenger(String messageId, String chatMessengerId){
        Optional<ChatMessenger> chatMessengerO = chatMessengerRepository.getObject(chatMessengerId);

        if (chatMessengerO.isEmpty()) {
            return List.of();
        }

        ChatMessenger chatMessenger = chatMessengerO.get();
        chatMessenger.sendMessengerToAll(messageId);

        return chatMessenger.getUsersId().getUserList();
    }

    /**
     * Lê a próxima mensagem não lida de um usuário em um chat privado.
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
     * Lê a próxima mensagem de comunidade não lida de um usuário.
     *
     * @param chatMessengerId identificador do chat da comunidade.
     * @param receiverId identificador do usuário leitor.
     * @return identificador da mensagem lida.
     * @throws NaoHaMensagens se o chat não existir ou não houver mensagens não lidas.
     */
    public String receiveCommunityMessenger(String chatMessengerId, String receiverId)
            throws NaoHaMensagens{

        Optional<ChatMessenger> chatMessengerO =
                chatMessengerRepository.getObject(chatMessengerId);
        ChatMessenger chatMessenger = chatMessengerO.orElseThrow(NaoHaMensagens::new);

        return chatMessenger.readMessage(receiverId).orElseThrow(NaoHaMensagens::new);
    }

    /**
     * Cria um chat de comunidade com o dono como primeiro participante.
     *
     * @param ownerUserId identificador do dono da comunidade.
     * @return identificador do chat criado.
     */
    public String buildCommunityChat(String ownerUserId) {
        String id = UUID.randomUUID().toString();
        ChatMessenger chatMessenger = new ChatMessenger(id, new ChatParticipantsKey(ownerUserId));
        chatMessengerRepository.saveChatMessenger(chatMessenger);
        return id;
    }

    /**
     * Adiciona um participante a um chat existente.
     *
     * @param chatMessengerId identificador do chat.
     * @param userId identificador do usuário adicionado.
     */
    public void addParticipant(String chatMessengerId, String userId) {
        Optional<ChatMessenger> chatMessengerO = chatMessengerRepository.getObject(chatMessengerId);

        if (chatMessengerO.isPresent()) {
            ChatMessenger chatMessenger = chatMessengerO.get();
            chatMessenger.addParticipant(userId);
            chatMessengerRepository.saveChatMessenger(chatMessenger);
        }
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
