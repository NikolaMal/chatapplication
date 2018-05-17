package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends Activity implements TextWatcher{

    private Button registerButton;
    private DatePicker birthDatePicker;
    private EditText userEditText;
    private EditText passEditText;
    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;

    private boolean userOK = false;
    private boolean passOK = false;
    private boolean emailOK = false;

    private HTTPHelper http_helper;
    private Handler handler;

    private static final String BASE_URL = "http://18.205.194.168:80";
    private static final String REGISTER_URL = BASE_URL + "/register";
    private static final String PREFS_NAME = "PREFS";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        registerButton = (Button) findViewById(R.id.register_registerbutton);
        birthDatePicker = (DatePicker) findViewById(R.id.register_datepicker);
        userEditText = (EditText) findViewById(R.id.register_username);
        passEditText = (EditText) findViewById(R.id.register_password);
        emailEditText = (EditText) findViewById(R.id.register_email);
        firstNameEditText = (EditText) findViewById(R.id.register_firstname);
        lastNameEditText = (EditText) findViewById(R.id.register_lastname);

        userEditText.addTextChangedListener(this);
        passEditText.addTextChangedListener(this);
        emailEditText.addTextChangedListener(this);

        handler = new Handler();
        http_helper = new HTTPHelper();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("username", userEditText.getText().toString());
                            object.put("password", passEditText.getText().toString());
                            object.put("email", emailEditText.getText().toString());

                            final boolean response = http_helper.registerOnServer(RegisterActivity.this, REGISTER_URL, object);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(response) {
                                        Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }

                                    else {
                                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                        Toast.makeText(RegisterActivity.this, prefs.getString("register_error", null), Toast.LENGTH_SHORT).show();

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
        });

        registerButton.setEnabled(false);

        Date time = Calendar.getInstance().getTime();

        birthDatePicker.setMaxDate(time.getTime());

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(userEditText.getText().toString().length() != 0 ){
            userOK = true;

        }

        else {
            userOK = false;
        }

        if(passEditText.getText().toString().length() >= 6){
            passOK = true;

        }
        else {
            passOK = false;
        }

        if(emailEditText.getText().toString().length() !=0  && Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){
            emailOK = true;

        }

        else {
            emailOK = false;
        }



        if (userOK && passOK && emailOK){
            registerButton.setEnabled(true);
        }

        else {
            registerButton.setEnabled(false);
        }
    }
}
