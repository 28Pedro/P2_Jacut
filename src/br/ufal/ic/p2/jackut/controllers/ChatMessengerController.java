package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.chatmessenger.ChatMessenger;
import br.ufal.ic.p2.jackut.services.chatMessenger.ChatMessengerService;
import br.ufal.ic.p2.jackut.services.chatMessenger.MessageService;
import br.ufal.ic.p2.jackut.services.user.MessageBoxService;
import br.ufal.ic.p2.jackut.services.user.MessageBoxIntegrator;
import br.ufal.ic.p2.jackut.services.user.InteractionIntegrator;
import br.ufal.ic.p2.jackut.services.user.UserIntegrator;

import java.util.List;
import java.util.Optional;

/**
 * Controlador responsável pelos casos de uso de recados, chats e mensagens.
 *
 * <p>Esta classe coordena a criação de mensagens, associação com chats,
 * notificação dos destinatários e leitura dos recados pendentes.</p>
 */
public class ChatMessengerController {

    private final UserIntegrator userIntegrator;
    private final MessageBoxService messageBoxService;
    private final MessageBoxIntegrator messageBoxIntegrator;
    private final InteractionIntegrator interactionIntegrator;
    private final ChatMessengerService chatMessengerService;
    private final MessageService messageService;

    /**
     * Cria o controlador e inicializa os serviços de chat, mensagem e usuário.
     *
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     * @throws FileError se houver falha ao carregar dados persistidos.
     */
    public ChatMessengerController() throws SaveError, FileError {
        this.userIntegrator = UserIntegrator.getInstance();
        this.chatMessengerService = new ChatMessengerService();
        this.messageBoxService = MessageBoxService.getInstance();
        this.messageBoxIntegrator = new MessageBoxIntegrator();
        this.interactionIntegrator = new InteractionIntegrator();
        this.messageService = new MessageService();
    }

    /**
     * Envia uma mensagem de um usuário para outro.
     *
     * @param messengerContent conteúdo textual da mensagem.
     * @param senderId identificador do usuário remetente.
     * @param receiverUserName login do usuário destinatário.
     * @throws UsuarioNaoCadastrado se o destinatário não estiver cadastrado.
     * @throws EnviarRecadoParaSiMesmo se o remetente tentar enviar mensagem para si mesmo.
     * @throws FuncaoInvalida se o destinatario tiver marcado o remetente como inimigo.
     */
    public void SendMessenger(String messengerContent, String senderId, String receiverUserName) throws
            UsuarioNaoCadastrado, EnviarRecadoParaSiMesmo, FuncaoInvalida {

        String receiverId = userIntegrator.getUserByName(receiverUserName);

        if(receiverId.equals(senderId)){
            throw new EnviarRecadoParaSiMesmo();
        }

        interactionIntegrator.assertCanInteract(senderId, receiverUserName);
        messageBoxIntegrator.sendPrivateMessage(messengerContent, senderId, receiverId);
    }

    /**
     * Lê a próxima mensagem pendente de um usuário.
     *
     * @param userId identificador do usuário que realizará a leitura.
     * @return conteúdo textual da próxima mensagem não lida.
     * @throws UsuarioNaoCadastrado se o usuário não estiver cadastrado.
     * @throws NaoHaRecados se não houver mensagens pendentes.
     */
    public String readMessenger(String userId)
            throws UsuarioNaoCadastrado,NaoHaRecados{

        Optional<String> messageIdO = messageBoxService.getNotificationUser(userId);

        String messageId = messageIdO.orElseThrow(NaoHaRecados::new);

        String chatId = messageService.getChatIdByMessage(messageId);

        String unreadMessageId = chatMessengerService.receiveMessenger(chatId,userId);

        return messageService.showMessage(unreadMessageId);
    }

    /**
     * Salva os dados de chats e mensagens.
     *
     * @throws SaveError se ocorrer falha durante a persistência.
     */
    public void saveData() throws SaveError{
       chatMessengerService.saveData();
       messageService.saveData();
    }

    /**
     * Limpa os dados de chats e mensagens.
     */
    public void resetData(){
        chatMessengerService.resetData();
        messageService.resetData();
    }

}
