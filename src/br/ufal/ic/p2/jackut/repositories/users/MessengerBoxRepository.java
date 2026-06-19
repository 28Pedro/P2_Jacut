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

/**
 * Reposit�rio respons�vel por persistir e recuperar caixas de mensagem.
 */
public class MessengerBoxRepository extends AbstractRepository<MessengerBox> {

    private Map<String,String> messengerBoxByUserId;
    private static MessengerBoxRepository instance;

    /**
     * Cria o reposit�rio de caixas de mensagem e reconstr�i o �ndice por usu�rio.
     *
     * @throws SaveError se a infraestrutura de persist�ncia n�o puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar caixas persistidas.
     */
    private MessengerBoxRepository() throws SaveError, FileError {
        super(XMLController.getInstance(),"messengerBox.xml");

        this.messengerBoxByUserId = new HashMap<>();

        if(!entityMap.isEmpty()){
            entityMap.forEach(((id, messengerBox) ->{
                messengerBoxByUserId.put(messengerBox.getUserId(),id);
            } ));
        }
    }

    /**
     * Retorna a inst�ncia �nica do reposit�rio de caixas de mensagem.
     *
     * @return inst�ncia compartilhada do reposit�rio.
     * @throws SaveError se a infraestrutura de persist�ncia n�o puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar caixas persistidas.
     */
    public static MessengerBoxRepository getInstance() throws SaveError, FileError{
        if(instance == null){
            instance = new MessengerBoxRepository();
        }
        return instance;
    }

    /**
     * Salva uma caixa de mensagem e atualiza o �ndice por usu�rio.
     *
     * @param messengerBox caixa de mensagem salva.
     */
    public void saveMessageBox(MessengerBox messengerBox){
        String id = messengerBox.getId();

        addObject(id,messengerBox);

        messengerBoxByUserId.put(messengerBox.getUserId(),id);
    }

    /**
     * Recupera uma caixa de mensagem por ID.
     *
     * @param messageBoxId identificador da caixa de mensagem.
     * @return caixa de mensagem encontrada.
     * @throws UsuarioNaoCadastrado se a caixa n�o for encontrada.
     */
    public MessengerBox getMessengerBoxById(String messageBoxId) throws
            UsuarioNaoCadastrado {

        return Optional.
                ofNullable(entityMap.get(messageBoxId)).
                orElseThrow(UsuarioNaoCadastrado::new);
    }

    /**
     * Recupera a caixa de mensagem associada a um usu�rio.
     *
     * @param userId identificador do usu�rio dono da caixa.
     * @return caixa de mensagem encontrada.
     * @throws UsuarioNaoCadastrado se a caixa n�o for encontrada.
     */
    public MessengerBox getMessengerBoxByUserId(String userId) throws
            UsuarioNaoCadastrado{
        return getMessengerBoxById(messengerBoxByUserId.get(userId));
    }

    /**
     * Limpa caixas de mensagem e �ndice por usu�rio.
     */
    @Override
    public void resetData(){
        super.resetData();
        messengerBoxByUserId.clear();
    }
}

