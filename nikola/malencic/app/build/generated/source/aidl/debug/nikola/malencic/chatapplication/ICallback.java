/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/mace/AndroidStudioProjects/nikola/malencic/app/src/main/aidl/nikola/malencic/chatapplication/ICallback.aidl
 */
package nikola.malencic.chatapplication;
// Declare any non-default types here with import statements

public interface ICallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements nikola.malencic.chatapplication.ICallback
{
private static final java.lang.String DESCRIPTOR = "nikola.malencic.chatapplication.ICallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an nikola.malencic.chatapplication.ICallback interface,
 * generating a proxy if needed.
 */
public static nikola.malencic.chatapplication.ICallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof nikola.malencic.chatapplication.ICallback))) {
return ((nikola.malencic.chatapplication.ICallback)iin);
}
return new nikola.malencic.chatapplication.ICallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_onCallback:
{
data.enforceInterface(DESCRIPTOR);
this.onCallback();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements nikola.malencic.chatapplication.ICallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void onCallback() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void onCallback() throws android.os.RemoteException;
}
