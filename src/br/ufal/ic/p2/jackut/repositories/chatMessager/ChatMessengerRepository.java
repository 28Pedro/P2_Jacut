package br.ufal.ic.p2.jackut.repositories.chatMessager;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatMessenger;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatParticipantsKey;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Reposit�rio respons�vel por persistir e recuperar chats.
 */
public class ChatMessengerRepository extends AbstractRepository<ChatMessenger> {

    private static ChatMessengerRepository instance;
    private Map<ChatParticipantsKey,String> chatMessengerList;

    /**
     * Cria o reposit�rio de chats e reconstr�i o �ndice por participantes.
     *
     * @throws FileError se ocorrer falha ao carregar chats persistidos.
     * @throws SaveError se a infraestrutura de persist�ncia n�o puder ser preparada.
     */
    private ChatMessengerRepository() throws FileError, SaveError {
        super(XMLController.getInstance(),"chat.xml");

        this.chatMessengerList = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach(
                    (chatId, chatMessenger) ->
                            chatMessengerList.put(chatMessenger.getUsersId(),
                                    chatMessenger.getId())
            );
        }

    }

    /**
     * Retorna a inst�ncia �nica do reposit�rio de chats.
     *
     * @return inst�ncia compartilhada do reposit�rio.
     * @throws SaveError se a infraestrutura de persist�ncia n�o puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar chats persistidos.
     */
    public static ChatMessengerRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new ChatMessengerRepository();
        }
        return instance;
    }

    /**
     * Salva um chat e atualiza o �ndice por participantes.
     *
     * @param chatMessenger chat a ser salvo.
     */
    public void saveChatMessenger(ChatMessenger chatMessenger){
        addObject(chatMessenger.getId(),chatMessenger);
        chatMessengerList.put(chatMessenger.getUsersId(),
                chatMessenger.getId());
    }

    /**
     * Recupera um chat pela chave de participantes.
     *
     * @param chatParticipantsKey chave formada pelos participantes do chat.
     * @return chat encontrado, ou vazio se n�o existir.
     */
    public Optional<ChatMessenger> getChatByUserIds(ChatParticipantsKey chatParticipantsKey){
        return Optional.ofNullable(
                entityMap.get(
                        chatMessengerList.get(chatParticipantsKey)
                )
        );
    }

    /**
     * Remove chats e retorna as mensagens que ainda estavam pendentes neles.
     *
     * @param chatIds identificadores dos chats removidos.
     * @return identificadores das mensagens não lidas removidas.
     */
    public Set<String> deleteChatsAndCollectUnreadMessages(Collection<String> chatIds) {
        Set<String> unreadMessageIds = new LinkedHashSet<>();

        for (String chatId : chatIds) {
            ChatMessenger chatMessenger = entityMap.remove(chatId);

            if (chatMessenger != null) {
                unreadMessageIds.addAll(chatMessenger.getUnreadMessageIds());
                chatMessengerList.remove(chatMessenger.getUsersId());
            }
        }

        return unreadMessageIds;
    }

    /**
     * Remove um participante dos chats que permanecerão ativos.
     *
     * @param userId identificador do participante removido.
     * @param preservedChatIds chats que devem permanecer e sofrer apenas remoção do participante.
     */
    public void removeUserFromChats(String userId, Collection<String> preservedChatIds) {
        for (String chatId : preservedChatIds) {
            ChatMessenger chatMessenger = entityMap.get(chatId);

            if (chatMessenger != null) {
                chatMessengerList.remove(chatMessenger.getUsersId());
                chatMessenger.removeParticipant(userId);
                chatMessengerList.put(chatMessenger.getUsersId(), chatId);
            }
        }
    }

    /**
     * Retorna os chats dos quais o usuário participa.
     *
     * @param userId identificador do usuário procurado.
     * @return identificadores dos chats participantes.
     */
    public Set<String> getChatIdsByParticipant(String userId) {
        Set<String> chatIds = new LinkedHashSet<>();
        entityMap.forEach((chatId, chatMessenger) -> {
            if (chatMessenger.getUsersId().getUserIds().contains(userId)) {
                chatIds.add(chatId);
            }
        });
        return chatIds;
    }

    /**
     * Limpa chats e �ndice por participantes.
     */
    @Override
    public void resetData(){
        super.resetData();
        chatMessengerList.clear();
    }




}
