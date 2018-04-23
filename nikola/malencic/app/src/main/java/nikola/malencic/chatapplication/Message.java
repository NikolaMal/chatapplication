package nikola.malencic.chatapplication;

/**
 * Created by mace on 2.4.18..
 */

public class Message {

    private String id;
    private String sender_id;
    private String receiver_id;
    private String messageText;

    public Message(String id, String sender_id, String receiver_id, String msg){
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.messageText = msg;
    }

    public String getId(){
        return this.id;
    }

    public String getSenderId(){
        return this.sender_id;
    }

    public String getReceiverId(){
        return this.receiver_id;
    }

    public String getMessageText(){
        return this.messageText;
    }

}
