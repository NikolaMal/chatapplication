package nikola.malencic.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mace on 29.3.18..
 */

public class ContactAdapter extends BaseAdapter implements View.OnClickListener{

    private Context aContext;
    private ArrayList<Contact> contacts;
    private SharedPreferences.Editor editor;
    private static final String PREFS_NAME = "PREFS";

    String whichContactClicked;


    public ContactAdapter(Context context){
        aContext = context;
        contacts = new ArrayList<Contact>();
        editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
    }

    public void addContact (Contact contact){
        contacts.add(contact);
        notifyDataSetChanged();
    }

    public void removeContact(int position){
        contacts.remove(position);
        notifyDataSetChanged();
    }

    public void update(Contact[] new_contacts){
        contacts.clear();
        if(new_contacts != null){
            for(Contact contact : new_contacts){
                contacts.add(contact);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        Object obj = null;

        try {
            obj = contacts.get(position);
        }

        catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public long getItemId(int position) {
       return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_layout, null);
            ViewHolder holder = new ViewHolder();
            holder.initial = (TextView) convertView.findViewById(R.id.itemInitial);
            holder.name = (TextView) convertView.findViewById(R.id.itemName);
            holder.image = (ImageView) convertView.findViewById(R.id.itemSend);
            holder.image.setOnClickListener(this);
            holder.image.setTag(position);
            convertView.setTag(holder);

        }

        Contact contact = (Contact) getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.initial.setText(contact.getFirstname().substring(0, 1).toUpperCase());
        Random rand = new Random();
        holder.initial.setBackgroundColor(Color.rgb( rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        holder.name.setText(contact.getFirstname());

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int clickedPosition = Integer.parseInt(v.getTag().toString());
        whichContactClicked = contacts.get(clickedPosition).getFirstname();

        if(v.getId() == R.id.itemSend){

            editor.putString("receiver_id", null);
            editor.apply();
            Intent intent = new Intent(aContext.getApplicationContext(), MessageActivity.class);
            intent.putExtra("clickedContactName", whichContactClicked);
            aContext.startActivity(intent);
        }

    }

    public class ViewHolder {
        public TextView initial = null;
        public TextView name = null;
        public ImageView image = null;
    }
}
