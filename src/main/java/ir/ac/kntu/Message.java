package ir.ac.kntu;

import ir.ac.kntu.userAndHisChildren.User;

import java.io.Serializable;
import java.util.Objects;

public class Message <T extends Acceptable> implements Serializable {

    private String messageText;

    private T acceptable;

    private User sender;

    private User targetUser;

    public Message(String messageText, T acceptable, User sender, User targetUser) {
        this.messageText = messageText;
        this.acceptable = acceptable;
        this.sender = sender;
        this.targetUser = targetUser;
    }

    public Message(Message<T> m) {
        this.messageText = m.messageText;
        this.acceptable = m.acceptable;
        this.targetUser = m.targetUser;
        this.sender = m.sender;
    }

    public void accept() {
        acceptable.accept(targetUser);
    }

    public void sendMessage() {
        targetUser.addMessage(this);
    }

    public String getMessageText() {
        return messageText;
    }

    public T getAcceptable() {
        return acceptable;
    }

    public User getSender() {
        return sender;
    }

    public User getTargetUser() {
        return targetUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message<?> message = (Message<?>) o;
        return Objects.equals(messageText, message.messageText) && Objects.equals(acceptable, message.acceptable) && Objects.equals(sender, message.sender) && Objects.equals(targetUser, message.targetUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageText, acceptable, sender, targetUser);
    }

    @Override
    public String toString() {
        return "Message{" +
                " messageText: " + messageText +
                "| acceptable: " + acceptable +
                "| sender: " + sender.getUserName() +
                "| targetUser: " + targetUser.getUserName() +
                " }";
    }
}
