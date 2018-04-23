package nikola.malencic.chatapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mace on 22.4.18..
 */

public class ContactDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contacts.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_CONTACT = "contact_table";
    public static final String COLUMN_CONTACT_ID = "contact_id";
    public static final String COLUMN_FIRST_NAME = "FirstName";
    public static final String COLUMN_LAST_NAME = "LastName";
    public static final String COLUMN_USER_NAME = "UserName";

    public static final String TABLE_NAME_MESSAGE = "message_table";
    public static final String COLUMN_MESSAGE_ID = "message_id";
    public static final String COLUMN_SENDER_ID = "sender_id";
    public static final String COLUMN_RECEIVER_ID = "receiver_id";
    public static final String COLUMN_MESSAGE = "message";

    private SQLiteDatabase Db = null;

    public ContactDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_CONTACT + " (" + COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_USER_NAME + " TEXT);" );
        db.execSQL("CREATE TABLE " + TABLE_NAME_MESSAGE + " (" + COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_SENDER_ID + " TEXT, "
                + COLUMN_RECEIVER_ID + " TEXT,"
                + COLUMN_MESSAGE + " TEXT);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_ID, contact.getId());
        values.put(COLUMN_FIRST_NAME, contact.getFirstname());
        values.put(COLUMN_LAST_NAME, contact.getLastname());
        values.put(COLUMN_USER_NAME, contact.getUsername());

        db.insert(TABLE_NAME_CONTACT, null, values);
        close();
    }

    public void insertMessage(Message message){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE_ID, message.getId());
        values.put(COLUMN_SENDER_ID, message.getSenderId());
        values.put(COLUMN_RECEIVER_ID, message.getReceiverId());
        values.put(COLUMN_MESSAGE, message.getMessageText());
        db.insert(TABLE_NAME_MESSAGE, null, values);
        close();
    }

    public Contact[] readContacts(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CONTACT, null, null, null, null, null, null, null);

        if(cursor.getCount() <= 0){
            return null;
        }

        Contact[] contacts = new Contact[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            contacts[i++] = createContact(cursor);
        }
        close();
        return contacts;
    }

    public Message[] readMessages(String sender, String receiver){
        SQLiteDatabase db = getReadableDatabase();
        //Cursor cursor = db.query(TABLE_NAME_MESSAGE,  null, "sender_id =? AND receiver_id =?" , new String[]{sender, receiver}, null, null, null, null);
        Cursor cursor = db.query(TABLE_NAME_MESSAGE, null, "sender_id=? AND receiver_id=?", new String[] {sender, receiver}, null, null, null, null);        if(cursor.getCount() <= 0){
            return null;
        }

        Message[] messages = new Message[cursor.getCount()];
        int i=0;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            messages[i++] = createMessage(cursor);
        }

        close();
        return messages;
    }

    public Contact readContact(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_CONTACT, null, COLUMN_CONTACT_ID + "=?", new String[] {id}, null, null, null);
        cursor.moveToFirst();
        Contact contact = createContact(cursor);
        close();
        return contact;
    }

    public Message readMessage(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_MESSAGE, null, COLUMN_MESSAGE_ID + "=?", new String[] {id}, null, null, null);
        cursor.moveToFirst();
        Message message = createMessage(cursor);
        close();
        return message;
    }

    private Contact createContact(Cursor cursor){
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_ID));
        String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
        String userName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));

        return new Contact(id, userName, firstName, lastName);

    }

    public void deleteContact(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_CONTACT, COLUMN_CONTACT_ID + "=?", new String[] {id});
        close();
    }

    private Message createMessage(Cursor cursor){
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_ID));
        String sender_id = cursor.getString(cursor.getColumnIndex(COLUMN_SENDER_ID));
        String receiver_id = cursor.getString(cursor.getColumnIndex(COLUMN_RECEIVER_ID));
        String message = cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE));

        return new Message(id, sender_id, receiver_id, message);
    }

    public void deleteMessage(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME_MESSAGE, COLUMN_MESSAGE_ID + "=?", new String[] {id});
        close();
    }
}
