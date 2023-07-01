package Client.Model.Chat;

import Client.Model.User;

public class Message {
    String message;
    String time;
    boolean isSeen;
    User sender;

    public Message() {}

    public Message(String message, String time, boolean isSeen, User sender) {
        this.message = message;
        this.time = time;
        this.isSeen = isSeen;
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public User getSender() {
        return sender;
    }
}
