package br.ufal.ic.p2.jackut.models.chatmessenger;

import java.util.*;

/**
 * Representa um chat entre participantes.
 */
public class ChatMessenger {

    private ChatParticipantsKey usersId;
    private String id;
    private Map<String, ChatUserState> messengerStates;

    /**
     * Cria um chat vazio para uso por mecanismos de serialização.
     */
    public ChatMessenger() {
        this.messengerStates = new HashMap<>();
    }

    /**
     * Cria um chat com identificador e chave de participantes.
     *
     * @param id identificador único do chat.
     * @param chatParticipantsKey chave com os participantes do chat.
     */
    public ChatMessenger(String id, ChatParticipantsKey chatParticipantsKey){
        this();
        this.id = id;
        this.usersId = chatParticipantsKey;

        List<String> userList = this.usersId.getUserList();

        userList.stream().forEach(
                user -> messengerStates.put(user, new ChatUserState())
        );
    }

    /**
     * Adiciona um participante ao chat, preservando o estado de leitura individual.
     *
     * @param userId identificador do usuário participante.
     */
    public void addParticipant(String userId) {
        if (!usersId.getUserIds().contains(userId)) {
            usersId.getUserIds().add(userId);
        }

        messengerStates.putIfAbsent(userId, new ChatUserState());
    }

    /**
     * Remove um participante e o respectivo estado de leitura do chat.
     *
     * @param userId identificador do participante removido.
     */
    public void removeParticipant(String userId) {
        usersId.getUserIds().remove(userId);
        messengerStates.remove(userId);
    }
    
    /**
     * Reúne os identificadores de mensagens que permanecem não lidas no chat.
     *
     * @return identificadores das mensagens pendentes de leitura.
     */
    public Set<String> getUnreadMessageIds() {
        Set<String> messageIds = new HashSet<>();
        messengerStates.values().forEach(state -> messageIds.addAll(state.getUnreadMessengers()));
        return messageIds;
    }

    /**
     * Envia uma mensagem para os participantes do chat, exceto o remetente.
     *
     * @param messenger identificador da mensagem enviada.
     * @param senderId identificador do usuário remetente.
     */
    public void sendMessenger(String messenger, String senderId) {

        messengerStates.forEach((userId, chatUserState) -> {
                    if (!userId.equals(senderId)) {
                        chatUserState.receiveMessenger(messenger);
                    }
                }
        );
    }

    /**
     * Envia uma mensagem para todos os participantes do chat.
     *
     * @param messenger identificador da mensagem enviada.
     */
    public void sendMessengerToAll(String messenger) {
        messengerStates.
                forEach((userId, chatUserState) ->
                        chatUserState.receiveMessenger(messenger));
    }

    /**
     * Lê a próxima mensagem não lida de um participante.
     *
     * @param receiverId identificador do participante leitor.
     * @return identificador da mensagem lida, ou vazio se não houver mensagem.
     */
    public Optional<String> readMessage(String receiverId){
        ChatUserState chatUserState = messengerStates.get(receiverId);

        if (chatUserState == null) {
            return Optional.empty();
        }

        return chatUserState.readMessenger();
    }

    /**
     * Retorna a chave dos participantes do chat.
     *
     * @return chave dos participantes.
     */
    public ChatParticipantsKey getUsersId() {
        return usersId;
    }

    /**
     * Define a chave dos participantes do chat.
     *
     * @param usersId chave dos participantes.
     */
    public void setUsersId(ChatParticipantsKey usersId) {
        this.usersId = usersId;
    }

    /**
     * Retorna o identificador único do chat.
     *
     * @return identificador do chat.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único do chat.
     *
     * @param id identificador do chat.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna os estados de leitura dos participantes.
     *
     * @return mapa entre usuários e seus estados de leitura.
     */
    public Map<String, ChatUserState> getMessengerStates() {
        return messengerStates;
    }

    /**
     * Define os estados de leitura dos participantes.
     *
     * @param messengerStates mapa entre usuários e seus estados de leitura.
     */
    public void setMessengerStates(Map<String, ChatUserState> messengerStates) {
        this.messengerStates = messengerStates;
    }
}
