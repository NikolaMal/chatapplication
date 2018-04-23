package nikola.malencic.chatapplication;

import android.widget.ImageView;

/**
 * Created by mace on 29.3.18..
 */

public class Contact {

    private String contact_id;
    private String username;
    private String firstname;
    private String lastname;

    public Contact(String id, String user, String first, String last){
        this.contact_id = id;
        this.username = user;
        this.firstname = first;
        this.lastname = last;
    }

    public String getId() {
        return this.contact_id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

}



