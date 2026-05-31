package br.ufal.ic.p2.jackut.services.chatMessenger;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecados;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatMessenger;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatParticipantsKey;
import br.ufal.ic.p2.jackut.repositories.ChatMessengerRepository;
import br.ufal.ic.p2.jackut.wrappers.DoubleClassReturn;

import java.util.*;

public class ChatMessengerService {

    private final ChatMessengerRepository chatMessengerRepository;

    public ChatMessengerService() throws FileError, SaveError {
        this.chatMessengerRepository = ChatMessengerRepository.getInstance();
    }

    public DoubleClassReturn<List<String>, String>
    SendMessenger(String messenger, String senderId, String receiverId){

        ChatParticipantsKey chatParticipantsKey =
                new ChatParticipantsKey(senderId,receiverId);

        Optional<ChatMessenger> chatMessengerO =
                chatMessengerRepository.getChatByUserIds(chatParticipantsKey);

        ChatMessenger chatMessenger = getOrBuild(chatMessengerO,chatParticipantsKey);

        chatMessenger.sendMessenger(messenger, senderId);

        return new DoubleClassReturn<List<String>,String>
                ( chatParticipantsKey.getUserList(),chatMessenger.getId());
    }

    public String receiveMessenger(String chatMessengerId, String receiverId)
            throws NaoHaRecados{

        Optional<ChatMessenger> chatMessengerO =
                chatMessengerRepository.getObject(chatMessengerId);
       ChatMessenger chatMessenger = chatMessengerO.orElseThrow(NaoHaRecados::new);

       return chatMessenger.readMessage(receiverId).orElseThrow(NaoHaRecados::new);
    }

    public void saveData() throws SaveError{
        chatMessengerRepository.saveData();
    }

    public void resetData(){
        chatMessengerRepository.resetData();
    }

    private ChatMessenger getOrBuild(Optional<ChatMessenger> chatMessengerO,
                                     ChatParticipantsKey chatParticipantsKey ){
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
