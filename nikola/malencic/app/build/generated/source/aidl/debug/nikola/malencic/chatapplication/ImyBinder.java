/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/mace/AndroidStudioProjects/nikola/malencic/app/src/main/aidl/nikola/malencic/chatapplication/ImyBinder.aidl
 */
package nikola.malencic.chatapplication;
public interface ImyBinder extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements nikola.malencic.chatapplication.ImyBinder
{
private static final java.lang.String DESCRIPTOR = "nikola.malencic.chatapplication.ImyBinder";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an nikola.malencic.chatapplication.ImyBinder interface,
 * generating a proxy if needed.
 */
public static nikola.malencic.chatapplication.ImyBinder asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof nikola.malencic.chatapplication.ImyBinder))) {
return ((nikola.malencic.chatapplication.ImyBinder)iin);
}
return new nikola.malencic.chatapplication.ImyBinder.Stub.Proxy(obj);
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
case TRANSACTION_setCallback:
{
data.enforceInterface(DESCRIPTOR);
nikola.malencic.chatapplication.ICallback _arg0;
_arg0 = nikola.malencic.chatapplication.ICallback.Stub.asInterface(data.readStrongBinder());
this.setCallback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements nikola.malencic.chatapplication.ImyBinder
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
@Override public void setCallback(nikola.malencic.chatapplication.ICallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_setCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void setCallback(nikola.malencic.chatapplication.ICallback callback) throws android.os.RemoteException;
}
