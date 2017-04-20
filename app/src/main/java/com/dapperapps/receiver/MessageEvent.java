package com.dapperapps.receiver;

/**
 * Created by usman on 4/17/17.
 */

public class MessageEvent {
    String message;
    public MessageEvent() {
    }
    public MessageEvent(String msg) {
        this.message=msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
