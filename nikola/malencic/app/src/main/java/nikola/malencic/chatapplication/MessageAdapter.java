package nikola.malencic.chatapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mace on 2.4.18..
 */

public class MessageAdapter extends BaseAdapter {

    private Context aContext;
    private ArrayList<Message> messages;

    public MessageAdapter(Context context){
        aContext = context;
        messages = new ArrayList<Message>();
    }

    public void addMessage(Message msg){
        messages.add(msg);
        notifyDataSetChanged();
    }

    public void removeMessage(int pos){
        messages.remove(pos);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        Object msg = null;
        try {
            msg = messages.get(position);
        }

        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        return msg;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.message_layout, null);
            MessageHolder holder = new MessageHolder();
            holder.msg = (TextView) convertView.findViewById(R.id.message_item);

            convertView.setTag(holder);
        }

        Message message = (Message) getItem(position);
        MessageHolder holder = (MessageHolder) convertView.getTag();

        holder.msg.setText(message.getMsg());
        holder.name = message.getName();

        if(holder.name.contentEquals("User")){
            holder.msg.setGravity(Gravity.END|Gravity.CENTER);
            holder.msg.setTextColor(Color.rgb(0, 0, 0));
            holder.msg.setBackgroundColor(Color.rgb(170, 170, 170));
        }

        else if (holder.name.contentEquals("Other")){
            holder.msg.setGravity(Gravity.START|Gravity.CENTER);
            holder.msg.setTextColor(Color.rgb(0, 0, 0));
            holder.msg.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        return convertView;
    }

    public class MessageHolder {
        public TextView msg = null;
        public String name = null;
    }
}
