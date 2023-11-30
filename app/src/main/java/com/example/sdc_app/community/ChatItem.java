package com.example.sdc_app.community;

public class ChatItem {
    private String messageId;
    private String message;
    private String previousMessageId;
    private String userId;
    private String previousMessageText;
    public ChatItem(){

    }

    public ChatItem(String messageId, String message, String previousMessageId, String userId, String previousMessageText) {
        this.messageId = messageId;
        this.message = message;
        this.previousMessageId = previousMessageId;
        this.userId = userId;
        this.previousMessageText = previousMessageText;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public String getPreviousMessageId() {
        return previousMessageId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPreviousMessageText() {
        return previousMessageText;
    }
}
