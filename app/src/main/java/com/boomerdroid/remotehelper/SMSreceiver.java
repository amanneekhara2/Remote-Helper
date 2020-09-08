package com.boomerdroid.remotehelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSreceiver extends BroadcastReceiver {

    private Intent serviceIntent;
    private String senderNum;
    private SmsMessage currentMessage;
    private String message;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        try{

            if(bundle!=null){
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    senderNum  = currentMessage.getDisplayOriginatingAddress();
                    message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    Toast.makeText(context, "senderNum: " + senderNum + ", message: " + message, Toast.LENGTH_LONG).show();

                }
                serviceIntent = new Intent(context, Remote.class);
                serviceIntent.putExtra("sender", senderNum);
                serviceIntent.putExtra("message", message);
                context.startService(serviceIntent);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
