package br.ufal.ic.p2.jackut.models.user;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Optional;
import java.util.Queue;

/**
 * Representa a caixa de notificações de um usuário.
 *
 * <p>A caixa mantém filas separadas para recados privados e mensagens enviadas
 * a comunidades, preservando a diferença entre os comandos lerRecado e
 * lerMensagem.</p>
 */
public class MessengerBox {

    private Queue<String> messengerNotifications;
    private Queue<String> communityMessageNotifications;
    private String userId;
    private String id;

    /**
     * Cria uma caixa vazia para uso por mecanismos de serialização.
     */
    public MessengerBox(){
        this.messengerNotifications = new ArrayDeque<>();
        this.communityMessageNotifications = new ArrayDeque<>();
    }

    /**
     * Cria uma caixa de mensagens para um usuário.
     *
     * @param userId identificador do usuário dono da caixa.
     * @param id identificador único da caixa.
     */
    public MessengerBox(String userId, String id){
        this();
        this.userId = userId;
        this.id = id;
    }

    /**
     * Adiciona uma notificação de recado privado à fila da caixa.
     *
     * @param chatMessengerId identificador da mensagem ou referência de chat notificada.
     */
    public void addNotification(String chatMessengerId){
        messengerNotifications.add(chatMessengerId);
    }

    /**
     * Remove e retorna a próxima notificação de recado privado pendente.
     *
     * @return próxima notificação, ou vazio se não houver notificações.
     */
    public Optional<String> popNotification(){
        return Optional.ofNullable(messengerNotifications.poll());
    }

    /**
     * Adiciona uma notificação de mensagem de comunidade à fila da caixa.
     *
     * @param messageId identificador da mensagem de comunidade.
     */
    public void addCommunityMessageNotification(String messageId) {
        communityMessageNotifications.add(messageId);
    }

    /**
     * Remove e retorna a próxima notificação de mensagem de comunidade pendente.
     *
     * @return próxima notificação de comunidade, ou vazio se não houver notificações.
     */
    public Optional<String> popCommunityMessageNotification() {
        return Optional.ofNullable(communityMessageNotifications.poll());
    }

    /**
     * Remove notificações referentes às mensagens excluídas.
     *
     * @param messageIds identificadores das mensagens excluídas.
     */
    public void removeNotifications(Collection<String> messageIds) {
        messengerNotifications.removeIf(messageIds::contains);
        communityMessageNotifications.removeIf(messageIds::contains);
    }

    /**
     * Retorna a fila de notificações de recados privados.
     *
     * @return fila de notificações de recados.
     */
    public Queue<String> getMessengerNotifications() {
        return messengerNotifications;
    }

    /**
     * Define a fila de notificações de recados privados.
     *
     * @param messengerNotifications fila de notificações.
     */
    public void setMessengerNotifications(Queue<String> messengerNotifications) {
        this.messengerNotifications = messengerNotifications;
    }

    /**
     * Retorna a fila de notificações de mensagens de comunidade.
     *
     * @return fila de notificações de mensagens de comunidade.
     */
    public Queue<String> getCommunityMessageNotifications() {
        return communityMessageNotifications;
    }

    /**
     * Define a fila de notificações de mensagens de comunidade.
     *
     * @param communityMessageNotifications fila de notificações de mensagens de comunidade.
     */
    public void setCommunityMessageNotifications(Queue<String> communityMessageNotifications) {
        this.communityMessageNotifications = communityMessageNotifications;
    }

    /**
     * Retorna o identificador do usuário dono da caixa.
     *
     * @return identificador do usuário.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Define o identificador do usuário dono da caixa.
     *
     * @param userId identificador do usuário.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retorna o identificador único da caixa.
     *
     * @return identificador da caixa.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único da caixa.
     *
     * @param id identificador da caixa.
     */
    public void setId(String id) {
        this.id = id;
    }
}
