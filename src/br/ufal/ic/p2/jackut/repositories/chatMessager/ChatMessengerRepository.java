package br.ufal.ic.p2.jackut.repositories.chatMessager;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatMessenger;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatParticipantsKey;
import br.ufal.ic.p2.jackut.repositories.AbstractRepository;
import br.ufal.ic.p2.jackut.repositories.XMLController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório responsável por persistir e recuperar chats.
 */
public class ChatMessengerRepository extends AbstractRepository<ChatMessenger> {

    private static ChatMessengerRepository instance;
    private Map<ChatParticipantsKey,String> chatMessengerList;

    /**
     * Cria o repositório de chats e reconstrói o índice por participantes.
     *
     * @throws FileError se ocorrer falha ao carregar chats persistidos.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
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

    /**
     * Retorna a instância única do repositório de chats.
     *
     * @return instância compartilhada do repositório.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar chats persistidos.
     */
    public static ChatMessengerRepository getInstance() throws SaveError,FileError{
        if(instance == null){
            instance = new ChatMessengerRepository();
        }
        return instance;
    }

    /**
     * Salva um chat e atualiza o índice por participantes.
     *
     * @param chatMessenger chat a ser salvo.
     */
    public void saveChatMessenger(ChatMessenger chatMessenger){
        addObject(chatMessenger.getId(),chatMessenger);
        chatMessengerList.put(chatMessenger.getUsersId(),
                chatMessenger.getId());
    }

    /**
     * Recupera um chat pela chave de participantes.
     *
     * @param chatParticipantsKey chave formada pelos participantes do chat.
     * @return chat encontrado, ou vazio se não existir.
     */
    public Optional<ChatMessenger> getChatByUserIds(ChatParticipantsKey chatParticipantsKey){
        return Optional.ofNullable(
                entityMap.get(
                        chatMessengerList.get(chatParticipantsKey)
                )
        );
    }

    /**
     * Limpa chats e índice por participantes.
     */
    @Override
    public void resetData(){
        super.resetData();
        chatMessengerList.clear();
    }




}
