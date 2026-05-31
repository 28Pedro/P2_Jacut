package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.MessengerBox;
import br.ufal.ic.p2.jackut.repositories.users.MessengerBoxRepository;

import java.util.Optional;
import java.util.UUID;


public class MessageBoxService {

    private static MessageBoxService instance;
    private final MessengerBoxRepository messengerBoxRepository;

    private MessageBoxService() throws FileError, SaveError {
        this.messengerBoxRepository = MessengerBoxRepository.getInstance();
    }

    public void buildMessageBoxObject(String userId){
        MessengerBox messengerBox = new MessengerBox(userId, UUID.randomUUID().toString());
        messengerBoxRepository.saveMessageBox(messengerBox);
    }

    public static MessageBoxService getInstance() throws SaveError, FileError{

        if(instance == null){
            instance = new MessageBoxService();
        }
        return instance;
    }

    public void notifyUser(String userId, String chatMessengerId) throws UsuarioNaoCadastrado {

        MessengerBox messengerBox= messengerBoxRepository.getMessengerBoxByUserId(userId);
        messengerBox.addNotification(chatMessengerId);
    }

    public Optional<String> getNotificationUser(String userId)
            throws UsuarioNaoCadastrado{

        MessengerBox messengerBox= messengerBoxRepository.getMessengerBoxByUserId(userId);
        return messengerBox.popNotification();
    }


    public void saveData() throws SaveError{
        messengerBoxRepository.saveData();
    }

    public void resetData(){
        messengerBoxRepository.resetData();
    }

}
