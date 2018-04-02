package nikola.malencic.chatapplication;

import android.widget.ImageView;

/**
 * Created by mace on 29.3.18..
 */

public class Contact {

    String initial;
    String name;


    public Contact( String name){
        this.name = name;

        String firstChar = name.substring(0, 1).toUpperCase();
        this.initial = firstChar;
    }

    String getInitial(){
        return this.initial;
    }

    String getName(){
        return this.name;
    }

    void setInitial(String in){
        this.initial = in;
    }

    void setName(String name){
        this.name = name;
    }


}
