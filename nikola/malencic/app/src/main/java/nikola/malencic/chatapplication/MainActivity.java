package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText username;
    private EditText password;

    private Button LoginButton;
    private Button RegisterButton;

    private HTTPHelper http_helper;
    private Handler handler;

    private boolean loginOK;

    private static final String PREFS_NAME = "PREFS";
    private static final String BASE_URL = "http://18.205.194.168:80";
    private static final String LOGIN_URL = BASE_URL + "/login";

    Contact[] contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        http_helper = new HTTPHelper();
        handler = new Handler();

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        LoginButton = (Button) findViewById(R.id.login_loginbutton);
        RegisterButton = (Button) findViewById(R.id.login_registerbutton);


        //contacts = contactDb_helper.readContacts();


        LoginButton.setOnClickListener(this);
        LoginButton.setEnabled(false);
        RegisterButton.setOnClickListener(this);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {



                if (username.getText().toString().length() != 0) {
                    if (password.getText().toString().length() >= 6) {
                        LoginButton.setEnabled(true);
                    } else {
                        LoginButton.setEnabled(false);
                    }
                } else {
                    LoginButton.setEnabled(false);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(username.getText().toString().length() != 0){
                    if (password.getText().toString().length() >= 6){
                        LoginButton.setEnabled(true);
                    }
                    else {
                        LoginButton.setEnabled(false);
                    }
                }
                else {
                    LoginButton.setEnabled(false);
                }


            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_loginbutton:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject object = new JSONObject();

                        try {

                            object.put("username", username.getText().toString());
                            object.put("password", password.getText().toString());

                            final boolean response = http_helper.logInOnServer(MainActivity.this, LOGIN_URL, object);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(response){
                                        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("logged_username", username.getText().toString());
                                        editor.apply();

                                        Toast.makeText(MainActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                                        startActivity(intent);
                                    }

                                    else {
                                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                        Toast.makeText(MainActivity.this, prefs.getString("login_error", null), Toast.LENGTH_SHORT).show();

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

            case R.id.login_registerbutton:

                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                this.startActivity(registerIntent);
                break;
        }

        }
    }

