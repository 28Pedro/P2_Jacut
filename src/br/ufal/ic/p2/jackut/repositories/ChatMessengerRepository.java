package br.ufal.ic.p2.jackut.repositories;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.ChatMessenger;

public class ChatMessengerRepository extends AbstractRepository<ChatMessenger> {

    private static ChatMessengerRepository instance;

    private ChatMessengerRepository() throws FileError, SaveError {
        super(XMLController.getInstance(),"chat.xml");
    }

    public static ChatMessengerRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new ChatMessengerRepository();
        }

        return instance;
    }


}
