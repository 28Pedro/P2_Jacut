package br.ufal.ic.p2.jackut.repositories.users;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.MessengerBox;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessengerBoxRepository extends AbstractRepository<MessengerBox> {

    private Map<String,String> messengerBoxByUserId;
    private static MessengerBoxRepository instance;

    private MessengerBoxRepository() throws SaveError, FileError {
        super(XMLController.getInstance(),"messengerBox.xml");

        this.messengerBoxByUserId = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach(((id, messengerBox) ->{
                messengerBoxByUserId.put(messengerBox.getUserId(),id);
            } ));
        }
    }

    public static MessengerBoxRepository getInstance() throws SaveError, FileError{
        if(instance == null){
            instance = new MessengerBoxRepository();
        }
        return instance;
    }

    public void saveMessageBox(MessengerBox messengerBox){
        String id = messengerBox.getId();

        addObject(id,messengerBox);

        messengerBoxByUserId.put(messengerBox.getUserId(),id);
    }

    public MessengerBox getMessengerBoxById(String friendshipId) throws
            UsuarioNaoCadastrado {

        return Optional.
                ofNullable(entityMap.get(friendshipId)).
                orElseThrow(UsuarioNaoCadastrado::new);
    }

    public MessengerBox getMessengerBoxByUserId(String userId) throws
            UsuarioNaoCadastrado{
        return getMessengerBoxById(messengerBoxByUserId.get(userId));
    }

    @Override
    public void resetData(){
        super.resetData();
        messengerBoxByUserId.clear();
    }
}
