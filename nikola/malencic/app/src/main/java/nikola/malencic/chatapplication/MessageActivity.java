package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MessageActivity extends Activity implements View.OnClickListener{
    private EditText messageText;
    private Button logoutButton;
    private Button refreshButton;
    private Button sendButton;
    private TextView labelText;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "PREFS";
    private static final String BASE_URL = "http://18.205.194.168:80";
    private static final String LOGOUT_URL = BASE_URL + "/logout";
    private static final String POST_MESSAGE_URL = BASE_URL + "/message";
    private static final String GET_MESSAGE_URL = BASE_URL + "/message/";

    private HTTPHelper http_helper;
    private Handler handler;
    private String receiver;
    MessageAdapter myAdapter = new MessageAdapter(this);
    private String sender_id;
    private String receiver_id;

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            Message[] messages;
            @Override
            public void run() {
                try {
                    final JSONArray array = http_helper.getMessagesFromServer(MessageActivity.this, GET_MESSAGE_URL + receiver);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(array !=null) {
                                JSONObject temp;
                                messages = new Message[array.length()];

                                for (int i = 0; i < array.length(); i++) {
                                    try {
                                        temp = array.getJSONObject(i);
                                        messages[i] = new Message(temp.getString("sender"), temp.getString("data"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                myAdapter.update(messages);
                            }
                            else {
                                Toast.makeText(MessageActivity.this, prefs.getString("getMessages_error", null), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (JSONException e){
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageText = (EditText) findViewById(R.id.message_messagetext);
        logoutButton = (Button) findViewById(R.id.message_logout);
        sendButton = (Button) findViewById(R.id.message_sendbutton);
        refreshButton = (Button) findViewById(R.id.message_refresh);
        labelText = (TextView) findViewById(R.id.message_label);
        sendButton.setEnabled(false);
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        http_helper = new HTTPHelper();
        handler = new Handler();

        receiver = prefs.getString("clicked_contact", null);

        labelText.setText(receiver);

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
        refreshButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.message_logout:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final boolean response = http_helper.logoutFromServer(MessageActivity.this, LOGOUT_URL);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(response){
                                        Toast.makeText(MessageActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }

                                    else {
                                        Toast.makeText(MessageActivity.this, prefs.getString("logout_error", null), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.message_sendbutton:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject();
                            object.put("receiver",  receiver);
                            object.put("data", messageText.getText().toString());

                            final boolean response = http_helper.sendMessageToServer(MessageActivity.this, POST_MESSAGE_URL, object);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(response){
                                        Toast.makeText(MessageActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();

                                    }

                                    else {
                                        Toast.makeText(MessageActivity.this, prefs.getString("sendMessage_error", null), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } catch (JSONException e){
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;

            case R.id.message_refresh:
                new Thread(new Runnable() {
                    Message[] messages;
                    @Override
                    public void run() {
                        try {
                            final JSONArray array = http_helper.getMessagesFromServer(MessageActivity.this, GET_MESSAGE_URL + receiver);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(array !=null) {
                                        JSONObject temp;
                                        messages = new Message[array.length()];

                                        for (int i = 0; i < array.length(); i++) {
                                            try {
                                                temp = array.getJSONObject(i);
                                                messages[i] = new Message(temp.getString("sender"), temp.getString("data"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        myAdapter.update(messages);
                                    }
                                    else {
                                        Toast.makeText(MessageActivity.this, prefs.getString("getMessages_error", null), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } catch (JSONException e){
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
        }
    }
}
