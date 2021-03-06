package nikola.malencic.chatapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ContactsActivity extends Activity implements View.OnClickListener, ServiceConnection {

    private Button logoutButton;
    private Button refreshButton;
    private TextView user_textView;
    private static final String PREFS_NAME = "PREFS";
    private Contact[] contacts;
    private ContactAdapter adapter;
    private SharedPreferences pref;
    private String another_temp_string;

    private static final String BASE_URL = "http://18.205.194.168:80";
    private static final String LOGOUT_URL = BASE_URL + "/logout";
    private static final String CONTACTS_URL = BASE_URL + "/contacts";
    private static final String GET_UNREAD_URL = BASE_URL + "/getfromservice";

    private HTTPHelper http_helper;
    private Handler handler;
    private ImyBinder service = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        logoutButton = (Button) findViewById(R.id.contact_logoutbutton);
        refreshButton = (Button) findViewById(R.id.contact_refreshbutton);
        refreshButton.setOnClickListener(this);

        logoutButton.setOnClickListener(this);

        user_textView = (TextView) findViewById(R.id.contact_logged_user);

        ListView list = (ListView) findViewById(R.id.contact_list);
        adapter = new ContactAdapter(this);


        pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        http_helper = new HTTPHelper();
        handler = new Handler();


        another_temp_string = pref.getString("logged_username", null);



        list.setAdapter(adapter);


        user_textView.setText(another_temp_string);

        bindService(new Intent(ContactsActivity.this, UnreadMessageService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.update(contacts);

        if(contacts != null) {
            for (int i = 0; i < contacts.length; i++) {
                if(contacts[i].getUsername().equals(another_temp_string)){
                    adapter.removeContact(i);
                    break;
                }
            }
        }
    }



    @Override
    public void onClick(View view){
        if(view.getId() == R.id.contact_logoutbutton){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        final boolean response = http_helper.logoutFromServer(ContactsActivity.this, LOGOUT_URL);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(response){
                                    Toast.makeText(ContactsActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                else {
                                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                    Toast.makeText(ContactsActivity.this, prefs.getString("logout_error", null), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        else if(view.getId() == R.id.contact_refreshbutton){

            new Thread(new Runnable() {
                Contact[] contacts;
                @Override

                public void run() {
                    try {
                        final JSONArray array = http_helper.getContactsFromServer(ContactsActivity.this, CONTACTS_URL);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    if (array != null) {
                                        contacts = new Contact[array.length()];
                                        JSONObject temp;

                                        for (int i = 0; i < array.length(); i++) {
                                            temp = array.getJSONObject(i);
                                            if(!temp.getString("username").equals(pref.getString("logged_username", null))) {

                                                contacts[i] = new Contact(temp.getString("username"));
                                            }
                                        }
                                    }
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                                adapter.update(contacts);

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

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = myBinder.Stub.asInterface(binder);
        try {
            service.setCallback(new CallbackClass());
        } catch (RemoteException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
    }

    private class CallbackClass extends ICallback.Stub {

        @Override
        public void onCallback() throws RemoteException {

            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), null).setContentText("New message!")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher_background))
                    .setContentTitle("Chat application")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final boolean response = http_helper.getUnreadMessageBool(ContactsActivity.this, GET_UNREAD_URL);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(response){
                                    notificationManager.notify(1, notificationBuilder.build());

                                }
                            }
                        });
                    } catch (IOException e){
                        e.printStackTrace();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
}
