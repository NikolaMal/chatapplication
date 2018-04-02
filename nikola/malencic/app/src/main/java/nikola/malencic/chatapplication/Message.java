package nikola.malencic.chatapplication;

/**
 * Created by mace on 2.4.18..
 */

public class Message {

    private String name;
    private String msg;

    public Message(String name, String msg){
        this.name = name;
        this.msg = msg;
    }

    public void setMsg(String msg){
        this.msg = msg;

    }

    public void setName(String name){
        this.name = name;
    }

    public String getMsg(){
        return this.msg;
    }

    public String getName(){
        return this.name;
    }

}
