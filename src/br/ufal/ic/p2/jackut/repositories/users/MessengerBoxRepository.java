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
 * Repositório responsável por persistir e recuperar caixas de mensagem.
 */
public class MessengerBoxRepository extends AbstractRepository<MessengerBox> {

    private Map<String,String> messengerBoxByUserId;
    private static MessengerBoxRepository instance;

    /**
     * Cria o repositório de caixas de mensagem e reconstrói o índice por usuário.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
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
     * Retorna a instância única do repositório de caixas de mensagem.
     *
     * @return instância compartilhada do repositório.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar caixas persistidas.
     */
    public static MessengerBoxRepository getInstance() throws SaveError, FileError{
        if(instance == null){
            instance = new MessengerBoxRepository();
        }
        return instance;
    }

    /**
     * Salva uma caixa de mensagem e atualiza o índice por usuário.
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
     * @param friendshipId identificador da caixa de mensagem.
     * @return caixa de mensagem encontrada.
     * @throws UsuarioNaoCadastrado se a caixa não for encontrada.
     */
    public MessengerBox getMessengerBoxById(String friendshipId) throws
            UsuarioNaoCadastrado {

        return Optional.
                ofNullable(entityMap.get(friendshipId)).
                orElseThrow(UsuarioNaoCadastrado::new);
    }

    /**
     * Recupera a caixa de mensagem associada a um usuário.
     *
     * @param userId identificador do usuário dono da caixa.
     * @return caixa de mensagem encontrada.
     * @throws UsuarioNaoCadastrado se a caixa não for encontrada.
     */
    public MessengerBox getMessengerBoxByUserId(String userId) throws
            UsuarioNaoCadastrado{
        return getMessengerBoxById(messengerBoxByUserId.get(userId));
    }

    /**
     * Limpa caixas de mensagem e índice por usuário.
     */
    @Override
    public void resetData(){
        super.resetData();
        messengerBoxByUserId.clear();
    }
}
