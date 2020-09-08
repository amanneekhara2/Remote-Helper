package com.boomerdroid.remotehelper;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;;
import android.util.Log;
import android.widget.Toast;
import static com.boomerdroid.remotehelper.Notify.channelId;

public class Remote extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Intent notificationIntent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification=new NotificationCompat.Builder(this,channelId)
                .setContentTitle("Remote Helper")
                .setContentText("Remote Helper is running")
                .setSmallIcon(R.drawable.ic_settings_remote)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);
        Toast.makeText(this,"Service",Toast.LENGTH_SHORT).show();
        Log.i("service","service started");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("service","new thread  started");
                checkMessage(intent);
            }
        }).start();

        return START_NOT_STICKY;
    }
    void checkMessage(Intent intent){
        String message=intent.getStringExtra("message");
        Log.i("service",message);
        String[] parts=message.split(" ");
        if(parts[0].equals("#RH") && parts.length>=3){
                if(parts[1].equals("message") && parts.length>=4){
                    StringBuffer sms=new StringBuffer();
                    for(int i=3;i<parts.length;i++)
                        sms.append(parts[i]);
                    sendSMS(parts[2],sms.toString());

                }
                else if(parts[1].equals("contact")){
                    StringBuffer name=new StringBuffer();
                    name.append(parts[2]);
                    for(int i=3;i<parts.length;i++){
                        name.append(" "+parts[i]);
                    }
                    Log.i("checkSMS","sendContat called");
                    sendContact(name.toString(),intent.getStringExtra("sender"));
                }
                else if(parts[1].equals("profile")){
                    changeProfile(parts[2]);
                }
                else{
                    Log.i("check","failed");
                }
        }
        stopSelf();
    }

    private void changeProfile(String part) {
        Log.i("Change profile",part);
        AudioManager audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);
        if(part.equals("vibrate")){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
        else if(part.equals("silent")){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        else if(part.equals("normal")){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        else{
            Log.i("change profile ","not matched");
        }

    }

    private void sendContact(String name,String sender) {
        Log.i("sendContact","begin");
        //String tempsender="7398611662";
        String selectionClause=ContactsContract.Contacts.DISPLAY_NAME + " = ? ";
        String[] selectionArgs={name};
        Log.i("sendContact","before Content Resolver");
        ContentResolver resolver=getContentResolver();
        Cursor cursor;
        cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,null,selectionClause,
                selectionArgs,null );
        Log.i("sendContact","after query");
        if(cursor.moveToNext()){
            //cursor.moveToFirst();

            Log.i("sendContact","Cursor not null");
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));

            String hasPhoneNumber=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            Log.i("SendContact","after has phone");
            if(hasPhoneNumber.equals("1")){
                StringBuffer phoneNo=new StringBuffer();
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null);
                Log.i("SendContact","before phones.moveNext");
                while (phones.moveToNext()) {
                    phoneNo.append(phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }
                phones.close();
                cursor.close();
                Log.i("send Contact",phoneNo.toString());
                sendSMS(sender,phoneNo.toString());
            }
            else{
                sendSMS(sender,"No number");
            }
        }
        else{
            Log.i("sendContact","else");
            sendSMS(sender,"No contact found");
        }


    }

    void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.i("send Sms", "SMS sent");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.i("send Sms", "generic failure");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.i("send Sms", "No service");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.i("send Sms", "null pdu");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.i("send Sms", "radio off");
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.i("deliver Sms", "delivered");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("deliver Sms", "not delivered");
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
}
