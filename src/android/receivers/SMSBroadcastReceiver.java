package com.ramkumar.cordovaplugins.sms.receivers;

import org.apache.cordova.PluginResult;

import com.ramkumar.cordovaplugins.sms.CordovaSMS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import org.json.JSONObject;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    public SMSBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for(Object currentObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) currentObj);
                    /*Message message = new Message(
                            currentMessage.getDisplayMessageBody(),
                            currentMessage.getDisplayOriginatingAddress(),
                            "CordovaSMS",
                            new Date()
                    );
                    DataProvider.getInstance().addMessage(message);*/
                    JSONObject sms = new JSONObject();
                    sms.put("message",currentMessage.getDisplayMessageBody());
                    sms.put("sender",currentMessage.getDisplayOriginatingAddress());
                    CordovaSMS.sendSMSPayload( sms.toString() );
                }
            }
        } catch (Exception e) {
            Log.e("SMS", "Exception: " + e);
            e.printStackTrace();
        }
        
        
    }
}