package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatMessenger;
import br.ufal.ic.p2.jackut.services.chatMessenger.ChatMessengerService;
import br.ufal.ic.p2.jackut.services.chatMessenger.MessageService;
import br.ufal.ic.p2.jackut.services.user.MessageBoxService;
import br.ufal.ic.p2.jackut.services.user.UserIntegrator;

import java.util.List;
import java.util.Optional;

public class ChatMessengerController {

    private final UserIntegrator userIntegrator;
    private final MessageBoxService messageBoxService;
    private final ChatMessengerService chatMessengerService;
    private final MessageService messageService;

    public ChatMessengerController() throws SaveError, FileError {
        this.userIntegrator = UserIntegrator.getInstance();
        this.chatMessengerService = new ChatMessengerService();
        this.messageBoxService = MessageBoxService.getInstance();
        this.messageService = new MessageService();
    }

    public void SendMessenger(String messengerContent, String senderId, String receiverUserName) throws
            UsuarioNaoCadastrado,EnviarRecadoParaSiMesmo {

        String receiverId = userIntegrator.getUserByName(receiverUserName);

        if(receiverId.equals(senderId)){
            throw new EnviarRecadoParaSiMesmo();
        }

        ChatMessenger chatMessenger = chatMessengerService.getOrBuild(senderId,receiverId);

        String messageId = messageService.
                createMessage(chatMessenger.getId(),messengerContent);

        List<String> receiversList = chatMessengerService.
                SendMessenger(messageId,senderId,chatMessenger);

        for(String userId : receiversList){
                messageBoxService.notifyUser(userId, messageId);
        }

    }

    public String readMessenger(String userId)
            throws UsuarioNaoCadastrado,NaoHaRecados{

        Optional<String> messageIdO = messageBoxService.getNotificationUser(userId);

        String messageId = messageIdO.orElseThrow(NaoHaRecados::new);

        String chatId = messageService.getChatIdByMessage(messageId);

        String unreadMessageId = chatMessengerService.receiveMessenger(chatId,userId);

        return messageService.showMessage(unreadMessageId);
    }

    public void saveData() throws SaveError{
       chatMessengerService.saveData();
       messageService.saveData();
    }

    public void resetData(){
        chatMessengerService.resetData();
        messageService.resetData();
    }

}
