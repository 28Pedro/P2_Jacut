package br.ufal.ic.p2.jackut.models.user;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * Representa a caixa de notificações de mensagens de um usuário.
 */
public class MessengerBox {

    private Queue<String> messengerNotifications;
    private String userId;
    private String id;

    /**
     * Cria uma caixa vazia para uso por mecanismos de serialização.
     */
    public MessengerBox(){
        this.messengerNotifications = new ArrayDeque<>();
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
     * Adiciona uma notificação à fila da caixa.
     *
     * @param chatMessengerId identificador da mensagem ou referência de chat notificada.
     */
    public void addNotification(String chatMessengerId){
        messengerNotifications.add(chatMessengerId);
    }

    /**
     * Remove e retorna a próxima notificação pendente.
     *
     * @return próxima notificação, ou vazio se não houver notificações.
     */
    public Optional<String> popNotification(){
        return Optional.ofNullable(messengerNotifications.poll());
    }


    /**
     * Retorna a fila de notificações.
     *
     * @return fila de notificações.
     */
    public Queue<String> getMessengerNotifications() {
        return messengerNotifications;
    }

    /**
     * Define a fila de notificações.
     *
     * @param messengerNotifications fila de notificações.
     */
    public void setMessengerNotifications(Queue<String> messengerNotifications) {
        this.messengerNotifications = messengerNotifications;
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
