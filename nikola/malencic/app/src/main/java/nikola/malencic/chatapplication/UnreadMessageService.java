package nikola.malencic.chatapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by mace on 30.5.18..
 */

public class UnreadMessageService extends Service {

    private myBinder binder = null;

    @Override
    public IBinder onBind(Intent intent) {
        if(binder == null){
            binder = new myBinder();
        }
        return binder;
    }

    public boolean onUnbind(Intent intent){
        binder.stop();
        return super.onUnbind(intent);
    }
}
