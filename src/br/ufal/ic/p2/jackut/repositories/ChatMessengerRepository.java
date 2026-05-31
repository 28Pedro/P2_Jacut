package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatMessenger;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatParticipantsKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChatMessengerRepository extends AbstractRepository<ChatMessenger> {

    private static ChatMessengerRepository instance;
    private Map<ChatParticipantsKey,String> chatMessengerList;

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

    public static ChatMessengerRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new ChatMessengerRepository();
        }
        return instance;
    }

    public void saveChatMessenger(ChatMessenger chatMessenger){
        addObject(chatMessenger.getId(),chatMessenger);
        chatMessengerList.put(chatMessenger.getUsersId(),
                chatMessenger.getId());
    }

    public Optional<ChatMessenger> getChatByUserIds(ChatParticipantsKey chatParticipantsKey){
        return Optional.ofNullable(
                entityMap.get(
                        chatMessengerList.get(chatParticipantsKey)
                )
        );
    }

    @Override
    public void resetData(){
        super.resetData();
        chatMessengerList.clear();
    }




}
