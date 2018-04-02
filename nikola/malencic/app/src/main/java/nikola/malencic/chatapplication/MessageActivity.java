package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    MessageAdapter myAdapter = new MessageAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageText = (EditText) findViewById(R.id.message_messagetext);
        logoutButton = (Button) findViewById(R.id.message_logout);
        sendButton = (Button) findViewById(R.id.message_sendbutton);
        labelText = (TextView) findViewById(R.id.message_label);
        sendButton.setEnabled(false);

        Intent contact_intent = getIntent();

        labelText.setText(contact_intent.getStringExtra("clickedContactName"));

        ListView messageList = (ListView) findViewById(R.id.message_list);

        messageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                myAdapter.removeMessage(position);
                return true;
            }});

        messageList.setAdapter(myAdapter);

        myAdapter.addMessage(new Message(getResources().getString(R.string.Misc_other).toString(), getResources().getString(R.string.Misc_dummy)));
        myAdapter.addMessage(new Message(getResources().getString(R.string.Misc_other).toString(), getResources().getString(R.string.Misc_dummy)));
        myAdapter.addMessage(new Message(getResources().getString(R.string.Misc_user).toString(), getResources().getString(R.string.Misc_dummy)));
        myAdapter.addMessage(new Message(getResources().getString(R.string.Misc_other).toString(), getResources().getString(R.string.Misc_dummy)));
        myAdapter.addMessage(new Message(getResources().getString(R.string.Misc_user).toString(), getResources().getString(R.string.Misc_dummy)));
        myAdapter.addMessage(new Message(getResources().getString(R.string.Misc_user).toString(), getResources().getString(R.string.Misc_dummy)));
        myAdapter.addMessage(new Message(getResources().getString(R.string.Misc_other).toString(), getResources().getString(R.string.Misc_dummy)));

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
                myAdapter.addMessage(new Message(getResources().getString(R.string.Misc_user).toString(), messageText.getText().toString() ));
                Toast.makeText(MessageActivity.this, "Message sent!",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
