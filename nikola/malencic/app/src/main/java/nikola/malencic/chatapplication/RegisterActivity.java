package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends Activity implements TextWatcher{

    Button registerButton;
    DatePicker birthDatePicker;
    EditText userEditText;
    EditText passEditText;
    EditText emailEditText;

    boolean userOK = false;
    boolean passOK = false;
    boolean emailOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.register_registerbutton);
        birthDatePicker = (DatePicker) findViewById(R.id.register_datepicker);
        userEditText = (EditText) findViewById(R.id.register_username);
        passEditText = (EditText) findViewById(R.id.register_password);
        emailEditText = (EditText) findViewById(R.id.register_email);

        userEditText.addTextChangedListener(this);
        passEditText.addTextChangedListener(this);
        emailEditText.addTextChangedListener(this);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerButtonIntent = new Intent(RegisterActivity.this, ContactsActivity.class);
                startActivity(registerButtonIntent);
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
