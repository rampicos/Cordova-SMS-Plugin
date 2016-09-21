package com.ramkumar.cordovaplugins.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

public class ComposeSMSActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    public static void sendSMS(String recipient, String message,Context context){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent("SMS_Delivered"), 0);
            smsManager.sendTextMessage(recipient, "SMS", message, sentPI, deliveredPI);
        }catch(Exception e){
            e.printStackTrace();
            Log.d("CordovaSMS","Error occured::"+e.getMessage());
        }
    }
}