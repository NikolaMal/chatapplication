package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsActivity extends Activity implements View.OnClickListener {

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        logoutButton = (Button) findViewById(R.id.contact_logoutbutton);

        logoutButton.setOnClickListener(this);

        ListView list = (ListView) findViewById(R.id.contact_list);

        ContactAdapter adapter = new ContactAdapter(this);

        list.setAdapter(adapter);

        adapter.addContact(new Contact( getResources().getString(R.string.Names_nikola).toString()));
        adapter.addContact(new Contact( getResources().getString(R.string.Names_bane).toString()));
        adapter.addContact(new Contact( getResources().getString(R.string.Names_dejan).toString()));
        adapter.addContact(new Contact( getResources().getString(R.string.Names_dusan).toString()));
        adapter.addContact(new Contact( getResources().getString(R.string.Names_stevan).toString()));
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.contact_logoutbutton){
            Intent intent = new Intent (ContactsActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }
}
