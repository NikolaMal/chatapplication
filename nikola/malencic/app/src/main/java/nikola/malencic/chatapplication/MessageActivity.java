package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends Activity implements View.OnClickListener{
    private EditText messageText;
    private Button logoutButton;
    private Button sendButton;
    private TextView labelText;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "PREFS";
    private ContactDbHelper contactDb_helper;
    MessageAdapter myAdapter = new MessageAdapter(this);
    private String sender_id;
    private String receiver_id;

    @Override
    protected void onResume() {
        super.onResume();
        Message[] messages = contactDb_helper.readMessages(sender_id, receiver_id);
        myAdapter.update(messages);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageText = (EditText) findViewById(R.id.message_messagetext);
        logoutButton = (Button) findViewById(R.id.message_logout);
        sendButton = (Button) findViewById(R.id.message_sendbutton);
        labelText = (TextView) findViewById(R.id.message_label);
        sendButton.setEnabled(false);
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        contactDb_helper = new ContactDbHelper(this);

        sender_id = prefs.getString("logged_user_id", null);
        receiver_id = prefs.getString("receiver_id", null);

        Intent contact_intent = getIntent();

        Message wlcm = new Message(null, sender_id, receiver_id, "Hello");
        contactDb_helper.insertMessage(wlcm);

        labelText.setText(contact_intent.getStringExtra("clickedContactName"));

        ListView messageList = (ListView) findViewById(R.id.message_list);

        messageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                myAdapter.removeMessage(position);
                return true;
            }});

        messageList.setAdapter(myAdapter);



        messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(messageText.getText().toString().length() != 0){
                    sendButton.setEnabled(true);
                }

                else {
                    sendButton.setEnabled(false);
                }

            }
        });

        logoutButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.message_logout:
                Intent logoutIntent = new Intent(MessageActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                break;
            case R.id.message_sendbutton:
                contactDb_helper.insertMessage(new Message(null, sender_id, receiver_id, messageText.getText().toString()));
                Toast.makeText(MessageActivity.this, "Message sent!",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
