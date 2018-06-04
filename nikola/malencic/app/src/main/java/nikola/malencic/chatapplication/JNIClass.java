package nikola.malencic.chatapplication;

/**
 * Created by mace on 4.6.18..
 */

public class JNIClass {

    static {
        System.loadLibrary("myLib");
    }

    public native String encryptDecrypt(String msg);
}
