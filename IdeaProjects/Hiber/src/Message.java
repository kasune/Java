/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 19, 2008
 * Time: 3:20:22 PM
 * To change this template use File | Settings | File Templates.
 */
//package hello;

public class Message {
    private Long id;
    private String text;
    private Message nextMessage;

    private Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;

    }

    public void setText(String text) {
        this.text = text;
    }

    public Message getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(Message nextMessage) {
        this.nextMessage = nextMessage;
    }
}
