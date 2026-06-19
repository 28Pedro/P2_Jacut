package br.ufal.ic.p2.jackut.services.user;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.services.chatMessenger.ChatMessengerService;
import br.ufal.ic.p2.jackut.services.chatMessenger.MessageService;

import java.util.List;
import java.util.Optional;

/**
 * Integrador responsável por coordenar caixas de mensagem, chats e mensagens.
 */
public class MessageBoxIntegrator {

    private final ChatMessengerService chatMessengerService;
    private final MessageService messageService;
    private final MessageBoxService messageBoxService;

    /**
     * Cria o integrador de caixas de mensagem.
     *
     * @throws FileError se ocorrer falha ao carregar dados persistidos.
     * @throws SaveError se a infraestrutura de persistência não puder ser preparada.
     */
    public MessageBoxIntegrator() throws FileError, SaveError {
        this.chatMessengerService = new ChatMessengerService();
        this.messageService = new MessageService();
        this.messageBoxService = MessageBoxService.getInstance();
    }

    /**
     * Cria um chat para uma comunidade.
     *
     * @param ownerUserId identificador do dono da comunidade.
     * @return identificador do chat criado.
     */
    public String buildCommunityChat(String ownerUserId) {
        return chatMessengerService.buildCommunityChat(ownerUserId);
    }

    /**
     * Adiciona um usuário ao chat de uma comunidade.
     *
     * @param chatMessengerId identificador do chat da comunidade.
     * @param userId identificador do usuário adicionado.
     */
    public void addParticipantToCommunityChat(String chatMessengerId, String userId) {
        chatMessengerService.addParticipant(chatMessengerId, userId);
    }

    /**
     * Envia uma mensagem para todos os participantes de um chat de comunidade.
     *
     * @param chatMessengerId identificador do chat da comunidade.
     * @param messageContent conteúdo textual da mensagem.
     * @throws UsuarioNaoCadastrado se algum participante não possuir caixa de mensagem.
     */
    public void sendCommunityMessage(String chatMessengerId, String messageContent)
            throws UsuarioNaoCadastrado {
        String messageId = messageService.createMessage(chatMessengerId, messageContent);
        List<String> userIds = chatMessengerService.sendCommunityMessenger(messageId, chatMessengerId);

        for (String userId : userIds) {
            messageBoxService.notifyCommunityMessage(userId, messageId);
        }
    }

    /**
     * Lê a próxima mensagem de comunidade pendente de um usuário.
     *
     * @param userId identificador do usuário leitor.
     * @return conteúdo textual da mensagem.
     * @throws UsuarioNaoCadastrado se o usuário não possuir caixa de mensagem.
     * @throws NaoHaMensagens se não houver mensagens de comunidade pendentes.
     */
    public String readCommunityMessage(String userId)
            throws UsuarioNaoCadastrado, NaoHaMensagens {
        Optional<String> messageIdO = messageBoxService.getCommunityMessageNotificationUser(userId);
        String messageId = messageIdO.orElseThrow(NaoHaMensagens::new);
        String chatId = messageService.getCommunityChatIdByMessage(messageId);
        String unreadMessageId = chatMessengerService.receiveCommunityMessenger(chatId, userId);

        return messageService.showCommunityMessage(unreadMessageId);
    }
}
