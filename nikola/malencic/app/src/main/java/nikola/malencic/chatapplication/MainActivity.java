package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText username;
    private EditText password;

    private Button LoginButton;
    private Button RegisterButton;

    private ContactDbHelper contactDb_helper;

    private boolean loginOK;

    private static final String PREFS_NAME = "PREFS";

    Contact[] contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        LoginButton = (Button) findViewById(R.id.login_loginbutton);
        RegisterButton = (Button) findViewById(R.id.login_registerbutton);

        contactDb_helper = new ContactDbHelper(this);

        contacts = contactDb_helper.readContacts();


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
                boolean pass = false;
                if(contacts == null){
                    Toast.makeText(MainActivity.this, "User list is empty! Please register.", Toast.LENGTH_LONG).show();
                    break;
                }
                for(int i=0;i<contacts.length;i++){
                    if(contacts[i].getUsername().equals(username.getText().toString())){
                        pass = true;
                        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("logged_user_id", contacts[i].getId());
                        editor.apply();
                        Intent loginIntent = new Intent(MainActivity.this, ContactsActivity.class);
                        this.startActivity(loginIntent);
                        break;
                    }
                }

                if(!pass){
                    Toast.makeText(MainActivity.this, "Username not found. Please register.", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.login_registerbutton:

                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                this.startActivity(registerIntent);
                break;
        }

        }
    }

