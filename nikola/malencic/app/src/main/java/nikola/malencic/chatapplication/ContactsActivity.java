package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsActivity extends Activity implements View.OnClickListener {

    private Button logoutButton;
    private ContactDbHelper contactDb_helper;
    private TextView user_textView;
    private static final String PREFS_NAME = "PREFS";
    private Contact[] contacts;
    private ContactAdapter adapter;
    private SharedPreferences pref;
    private String another_temp_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        logoutButton = (Button) findViewById(R.id.contact_logoutbutton);

        logoutButton.setOnClickListener(this);

        user_textView = (TextView) findViewById(R.id.contact_logged_user);

        ListView list = (ListView) findViewById(R.id.contact_list);
        adapter = new ContactAdapter(this);

        contactDb_helper = new ContactDbHelper(this);

        pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        String temp_string = new String();
        another_temp_string = pref.getString("logged_user_id", null);



        list.setAdapter(adapter);

        contacts = contactDb_helper.readContacts();

        for(int i=0;i<contacts.length;i++){
            if(contacts[i].getId().equals(another_temp_string)){
                temp_string = contacts[i].getFirstname();

                break;
            }
        }

        user_textView.setText(temp_string);




    }



    @Override
    protected void onResume() {
        super.onResume();

        adapter.update(contacts);

        if(contacts != null) {
            for (int i = 0; i < contacts.length; i++) {
                if(contacts[i].getId().equals(another_temp_string)){
                    adapter.removeContact(i);
                    break;
                }
            }
        }
    }



    @Override
    public void onClick(View view){
        if(view.getId() == R.id.contact_logoutbutton){
            Intent intent = new Intent (ContactsActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }
}
