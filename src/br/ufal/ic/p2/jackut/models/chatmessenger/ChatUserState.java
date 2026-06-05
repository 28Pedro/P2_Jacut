package br.ufal.ic.p2.jackut.models.chatmessenger;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * Representa o estado de leitura de um usuário dentro de um chat.
 */
public class ChatUserState {

    private Queue<String> ReadMessengers;
    private Queue<String> UnreadMessengers;

    /**
     * Cria um estado de leitura sem mensagens.
     */
    public ChatUserState() {
        ReadMessengers = new ArrayDeque<>();
        UnreadMessengers = new ArrayDeque<>();
    }

    /**
     * Registra uma mensagem como não lida.
     *
     * @param messenger identificador da mensagem recebida.
     */
    public void receiveMessenger(String messenger){
        this.UnreadMessengers.add(messenger);
    }

    /**
     * Lê a próxima mensagem não lida.
     *
     * @return identificador da mensagem lida, ou vazio se não houver mensagens.
     */
    public Optional<String> readMessenger(){
        if(UnreadMessengers.isEmpty()){
            return Optional.empty();
        }

        Optional<String> messengerO = Optional.of(UnreadMessengers.poll());
        ReadMessengers.add(messengerO.get());

        return messengerO;
    }

    /**
     * Retorna a fila de mensagens lidas.
     *
     * @return fila de mensagens lidas.
     */
    public Queue<String> getReadMessengers() {
        return ReadMessengers;
    }

    /**
     * Define a fila de mensagens lidas.
     *
     * @param readMessengers fila de mensagens lidas.
     */
    public void setReadMessengers(Queue<String> readMessengers) {
        ReadMessengers = readMessengers;
    }

    /**
     * Retorna a fila de mensagens não lidas.
     *
     * @return fila de mensagens não lidas.
     */
    public Queue<String> getUnreadMessengers() {
        return UnreadMessengers;
    }

    /**
     * Define a fila de mensagens não lidas.
     *
     * @param unreadMessengers fila de mensagens não lidas.
     */
    public void setUnreadMessengers(Queue<String> unreadMessengers) {
        UnreadMessengers = unreadMessengers;
    }
}
