package br.ufal.ic.p2.jackut.models.chatmessenger;

/**
 * Representa uma mensagem enviada em um chat.
 */
public class Message {

    private String content;
    private String chatMessageId;
    public String id;

    /**
     * Cria uma mensagem vazia para uso por mecanismos de serialização.
     */
    public Message() {
    }

    /**
     * Cria uma mensagem com conteúdo, chat associado e identificador.
     *
     * @param content conteúdo textual da mensagem.
     * @param chatMessageId identificador do chat ao qual a mensagem pertence.
     * @param id identificador único da mensagem.
     */
    public Message(String content, String chatMessageId, String id) {
        this.content = content;
        this.chatMessageId = chatMessageId;
        this.id = id;
    }

    /**
     * Retorna o conteúdo textual da mensagem.
     *
     * @return conteúdo da mensagem.
     */
    public String getContent() {
        return content;
    }

    /**
     * Define o conteúdo textual da mensagem.
     *
     * @param content conteúdo da mensagem.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retorna o identificador do chat associado à mensagem.
     *
     * @return identificador do chat.
     */
    public String getChatMessageId() {
        return chatMessageId;
    }

    /**
     * Define o identificador do chat associado à mensagem.
     *
     * @param chatMessageId identificador do chat.
     */
    public void setChatMessageId(String chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    /**
     * Retorna o identificador único da mensagem.
     *
     * @return identificador da mensagem.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único da mensagem.
     *
     * @param id identificador da mensagem.
     */
    public void setId(String id) {
        this.id = id;
    }
}
