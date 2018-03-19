package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageActivity extends Activity implements View.OnClickListener{
    EditText messageText;
    Button logoutButton;
    Button sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageText = (EditText) findViewById(R.id.message_messagetext);
        logoutButton = (Button) findViewById(R.id.message_logout);
        sendButton = (Button) findViewById(R.id.message_sendbutton);
        sendButton.setEnabled(false);



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
                Toast.makeText(MessageActivity.this, "Message sent!",
                        Toast.LENGTH_LONG).show();
        }
    }
}
