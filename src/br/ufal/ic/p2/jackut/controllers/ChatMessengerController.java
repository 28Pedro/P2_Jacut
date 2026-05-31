package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.services.chatMessenger.ChatMessengerService;
import br.ufal.ic.p2.jackut.services.user.UserIntegrator;
import br.ufal.ic.p2.jackut.wrappers.DoubleClassReturn;

import java.util.List;
import java.util.Optional;

public class ChatMessengerController {

    private final UserIntegrator userIntegrator;
    private final ChatMessengerService chatMessengerService;

    public ChatMessengerController() throws SaveError, FileError {
        this.userIntegrator = new UserIntegrator();
        this.chatMessengerService = new ChatMessengerService();
    }

    public void SendMessenger(String messenger, String senderId, String receiverUserName) throws
            UsuarioNaoCadastrado,EnviarRecadoParaSiMesmo {

        String receiverId = userIntegrator.getUserByName(receiverUserName);

        if(receiverId.equals(senderId)){
            throw new EnviarRecadoParaSiMesmo();
        }

        DoubleClassReturn<List<String>,String> pair =
                chatMessengerService.SendMessenger(messenger,senderId,receiverId);

        for(String userId : pair.getFirst()){
            userIntegrator.notifyUser(userId,pair.getSecond());
        }

    }

    public String readMessenger(String userId)
            throws UsuarioNaoCadastrado,NaoHaRecados{

        Optional<String> chatIdO = userIntegrator.getNotificationUser(userId);
        String chatId = chatIdO.orElseThrow(NaoHaRecados::new);

        return chatMessengerService.receiveMessenger(chatId,userId);
    }

    public void saveData() throws SaveError{
       chatMessengerService.saveData();
    }

    public void resetData(){
        chatMessengerService.resetData();
    }

}
