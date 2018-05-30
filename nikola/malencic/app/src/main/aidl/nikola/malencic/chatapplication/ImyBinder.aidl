// ImyBinder.aidl
package nikola.malencic.chatapplication;

// Declare any non-default types here with import statements

import nikola.malencic.chatapplication.ICallback;

interface ImyBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void setCallback(in ICallback callback);

}
