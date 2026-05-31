package br.ufal.ic.p2.jackut.models.chatmessenger;

public class Message {

    private String content;
    private String chatMessageId;
    public String id;

    public Message() {
    }

    public Message(String content, String chatMessageId, String id) {
        this.content = content;
        this.chatMessageId = chatMessageId;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChatMessageId() {
        return chatMessageId;
    }

    public void setChatMessageId(String chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
