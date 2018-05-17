package nikola.malencic.chatapplication;

/**
 * Created by mace on 2.4.18..
 */

public class Message {

    private String sender;
    private String messageText;

    public Message(String sender, String txt){

        this.sender = sender;
        this.messageText = txt;
    }



    public String getSenderId(){
        return this.sender;
    }



    public String getMessageText(){
        return this.messageText;
    }

}
