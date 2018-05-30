package nikola.malencic.chatapplication;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

/**
 * Created by mace on 30.5.18..
 */

public class myBinder extends ImyBinder.Stub {

    private ICallback callback;
    private CallbackCaller caller;



    @Override
    public void setCallback(ICallback callback) throws RemoteException {
        this.callback = callback;
        caller = new CallbackCaller();
        caller.start();
    }

    public void stop(){
        caller.stop();

    }


    private class CallbackCaller implements Runnable{

        private static final int waitTime = 5000;
        private Handler handler;
        private boolean running = true;

        public void start(){
            handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(this, waitTime);
        }

        public void stop(){
            running = false;
            handler.removeCallbacks(this);
        }
        @Override
        public void run() {
            if(running){

                try {
                    callback.onCallback();
                } catch (RemoteException e){
                    e.printStackTrace();
                }

                handler.postDelayed(this, waitTime);
            }

            else {
                return;
            }

        }
    }


}
