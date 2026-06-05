package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.FileError;
import br.ufal.ic.p2.jackut.exceptions.SaveError;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.user.MessengerBox;
import br.ufal.ic.p2.jackut.repositories.users.MessengerBoxRepository;

import java.util.Optional;
import java.util.UUID;


/**
 * Serviço responsável por caixas de notificações de mensagens dos usuários.
 */
public class MessageBoxService {

    private static MessageBoxService instance;
    private final MessengerBoxRepository messengerBoxRepository;

    /**
     * Cria o serviço de caixas de mensagem.
     *
     * @throws FileError se ocorrer falha ao carregar caixas persistidas.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    private MessageBoxService() throws FileError, SaveError {
        this.messengerBoxRepository = MessengerBoxRepository.getInstance();
    }

    /**
     * Cria uma caixa de mensagens para um usuário.
     *
     * @param userId identificador do usuário dono da caixa.
     */
    public void buildMessageBoxObject(String userId){
        MessengerBox messengerBox = new MessengerBox(userId, UUID.randomUUID().toString());
        messengerBoxRepository.saveMessageBox(messengerBox);
    }

    /**
     * Retorna a instância única do serviço de caixas de mensagem.
     *
     * @return instância compartilhada do serviço.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se ocorrer falha ao carregar caixas persistidas.
     */
    public static MessageBoxService getInstance() throws SaveError, FileError{

        if(instance == null){
            instance = new MessageBoxService();
        }
        return instance;
    }

    /**
     * Adiciona uma notificação de mensagem à caixa de um usuário.
     *
     * @param userId identificador do usuário notificado.
     * @param chatMessengerId identificador da mensagem ou referência de chat notificada.
     * @throws UsuarioNaoCadastrado se a caixa do usuário não for encontrada.
     */
    public void notifyUser(String userId, String chatMessengerId) throws UsuarioNaoCadastrado {

        MessengerBox messengerBox= messengerBoxRepository.getMessengerBoxByUserId(userId);
        messengerBox.addNotification(chatMessengerId);
    }

    /**
     * Obtém a próxima notificação pendente de um usuário.
     *
     * @param userId identificador do usuário consultado.
     * @return notificação pendente, ou {@link Optional#empty()} se não houver notificação.
     * @throws UsuarioNaoCadastrado se a caixa do usuário não for encontrada.
     */
    public Optional<String> getNotificationUser(String userId)
            throws UsuarioNaoCadastrado{

        MessengerBox messengerBox= messengerBoxRepository.getMessengerBoxByUserId(userId);
        return messengerBox.popNotification();
    }


    /**
     * Salva os dados das caixas de mensagem.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError{
        messengerBoxRepository.saveData();
    }

    /**
     * Limpa os dados das caixas de mensagem.
     */
    public void resetData(){
        messengerBoxRepository.resetData();
    }

}
