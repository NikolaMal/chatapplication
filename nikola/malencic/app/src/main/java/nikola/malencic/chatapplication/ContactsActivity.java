package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactsActivity extends Activity implements View.OnClickListener {

    TextView friendText;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        logoutButton = (Button) findViewById(R.id.contact_logoutbutton);
        friendText = (TextView) findViewById(R.id.contact_friendtext);
        friendText.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.contact_friendtext:
                Intent messageIntent = new Intent(ContactsActivity.this, MessageActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.contact_logoutbutton:
                Intent logoutIntent = new Intent(ContactsActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                break;

        }
    }
}
