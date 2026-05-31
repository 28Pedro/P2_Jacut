package br.ufal.ic.p2.jackut.services.chatMessenger;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.NaoHaRecados;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatMessenger;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatParticipantsKey;
import br.ufal.ic.p2.jackut.repositories.chatMessager.ChatMessengerRepository;
import br.ufal.ic.p2.jackut.wrappers.DoubleClassReturn;

import java.util.*;

public class ChatMessengerService {

    private final ChatMessengerRepository chatMessengerRepository;

    public ChatMessengerService() throws FileError, SaveError {
        this.chatMessengerRepository = ChatMessengerRepository.getInstance();
    }

    public List<String> SendMessenger(String messageId, String senderId, ChatMessenger chatMessenger){

        chatMessenger.sendMessenger(messageId, senderId);

        return chatMessenger.getUsersId().getUserList()
                .stream()
                .filter(userId -> !userId.equals(senderId))
                .toList();
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
