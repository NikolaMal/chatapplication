package nikola.malencic.chatapplication;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mace on 15.5.18..
 */

public class HTTPHelper {

    private static final String PREFS_NAME = "PREFS";

    public boolean registerOnServer(Context context, String URL, JSONObject jsonObject) throws IOException{
        HttpURLConnection connection;
        java.net.URL url= new URL(URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setReadTimeout(1000);
        connection.setConnectTimeout(10000);

        connection.setDoOutput(true);
        connection.setDoInput(true);

        try {
            connection.connect();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        DataOutputStream ostream = new DataOutputStream(connection.getOutputStream());

        ostream.writeBytes(jsonObject.toString());
        ostream.flush();
        ostream.close();

        int response = connection.getResponseCode();

        if(response != 200){
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString("register_error", connection.getResponseMessage().toString());
            editor.apply();
            return false;
        }

        connection.disconnect();

        return true;


    }

    public boolean logInOnServer(Context context, String URL, JSONObject jsonObject) throws IOException {
        HttpURLConnection connection;
        java.net.URL url = new URL(URL);

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");

        connection.setReadTimeout(1000);
        connection.setConnectTimeout(10000);

        connection.setDoOutput(true);
        connection.setDoInput(true);

        try {
            connection.connect();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        DataOutputStream ostream = new DataOutputStream(connection.getOutputStream());

        ostream.writeBytes(jsonObject.toString());
        ostream.flush();
        ostream.close();

        int response = connection.getResponseCode();

        String sessionId = connection.getHeaderField("sessionid");

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        if(response != 200){
            editor.putString("login_error", connection.getResponseMessage());
            editor.apply();
            return false;
        }

        else {
            editor.putString("sessionId", sessionId);
            editor.apply();
        }

        connection.disconnect();

        return true;

    }

    public boolean logoutFromServer(Context context, String URL) throws IOException {
        HttpURLConnection connection;
        java.net.URL url = new URL(URL);

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String sessionId = prefs.getString("sessionId", null);


        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("sessionId", sessionId);

        connection.setReadTimeout(1000);
        connection.setConnectTimeout(10000);

        connection.setDoOutput(true);
        connection.setDoInput(true);

        try {
            connection.connect();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        int response = connection.getResponseCode();
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        if(response != 200){
            editor.putString("logout_error", connection.getResponseMessage());
            editor.apply();
            return false;
        }

        connection.disconnect();

        return true;
    }

    public boolean sendMessageToServer(Context context, String URL, JSONObject jsonObject) throws IOException, JSONException{
        HttpURLConnection connection;
        java.net.URL url= new URL(URL);

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String sessionId = prefs.getString("sessionId", null);

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("sessionid", sessionId);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setReadTimeout(1000);
        connection.setConnectTimeout(10000);

        connection.setDoOutput(true);
        connection.setDoInput(true);

        try {
            connection.connect();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        DataOutputStream ostream = new DataOutputStream(connection.getOutputStream());

        ostream.writeBytes(jsonObject.toString());
        ostream.flush();
        ostream.close();

        int response = connection.getResponseCode();

        if(response != 200){
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString("sendMessage_error", connection.getResponseMessage());
            editor.apply();
            return false;
        }

        connection.disconnect();

        return true;
    }

    public JSONArray getContactsFromServer(Context context, String URL) throws IOException, JSONException {
        HttpURLConnection connection;
        java.net.URL url = new URL(URL);

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String sessionId = prefs.getString("sessionId", null);


        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("sessionid", sessionId);

        connection.setReadTimeout(10000);
        connection.setConnectTimeout(10000);

        try {
            connection.connect();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null){
            builder.append(line + "\n");
        }

        reader.close();

        String jsonString = builder.toString();

        int response = connection.getResponseCode();

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        connection.disconnect();

        if (response != 200){
            editor.putString("getContacts_error", connection.getResponseMessage());
            editor.apply();
            return null;
        }

        else {
            return new JSONArray(jsonString);
        }




    }

    public JSONArray getMessagesFromServer(Context context, String URL) throws IOException, JSONException{
        HttpURLConnection connection;
        java.net.URL url = new URL(URL);

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String sessionId = prefs.getString("sessionId", null);


        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("sessionid", sessionId);

        connection.setReadTimeout(10000);
        connection.setConnectTimeout(10000);

        try {
            connection.connect();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null){
            builder.append(line + "\n");
        }

        reader.close();

        String jsonString = builder.toString();

        int response = connection.getResponseCode();

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        connection.disconnect();

        if (response != 200){
            editor.putString("getMessages_error", connection.getResponseMessage());
            editor.apply();
            return null;
        }

        else {
            return new JSONArray(jsonString);
        }
    }

    public boolean getUnreadMessageBool(Context context, String URL) throws IOException, JSONException{
        HttpURLConnection connection;
        java.net.URL url = new URL(URL);

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String sessionId = prefs.getString("sessionId", null);

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("sessionid", sessionId);

        connection.setReadTimeout(10000);
        connection.setConnectTimeout(10000);

        try {
            connection.connect();
        } catch (IOException e){
            e.printStackTrace();

        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        Boolean response = Boolean.valueOf(reader.readLine());

        reader.close();

        connection.disconnect();

        return response;


    }


}
