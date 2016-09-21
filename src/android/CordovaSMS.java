package com.ramkumar.cordovaplugins.sms;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.content.Intent;
import android.provider.Telephony;
import android.util.Log;


public class CordovaSMS extends CordovaPlugin {
    
    public static CordovaWebView gWebView;
    public static String notificationCallBack = "CordovaSMS.onSMSReceived";
    private static final String LOG_TAG = CordovaSMS.class.getSimpleName();

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        gWebView = webView;

        String packageName = cordova.getActivity().getPackageName();
        Log.d(LOG_TAG,"initialize::packageName::"+packageName+":::Telephony data::"+Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()));
        Log.d(LOG_TAG,""+(packageName.equalsIgnoreCase(Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()))));
        if(!packageName.equalsIgnoreCase(Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()))){
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
            cordova.getActivity().startActivity(intent);
            Log.d(LOG_TAG,"Default SMS Set successfully");
        }

        Log.d(LOG_TAG, "==> CordovaSMSPlugin initialize");
    }

    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) {
        Log.d(LOG_TAG,"Action Called::"+ action);
        Log.d(LOG_TAG,"Incoming Object::"+ args.toString());
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {
                            final String packageName = cordova.getActivity().getPackageName();
                            Log.d(LOG_TAG,"State restored 2::packageName::"+packageName+":::Telephony data::"+Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()));
                            if (action.equalsIgnoreCase("sendsms")) {
                                Log.d(LOG_TAG,"Default SMS mismatch");
                                if(!packageName.equalsIgnoreCase(Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()))){
                                    Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                                    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
                                    cordova.getActivity().startActivity(intent);
                                    Log.d(LOG_TAG,"Default SMS Set successfully");
                                }
                                Log.d(LOG_TAG,"Defult SMS is Your App");
                                JSONObject argument = args.getJSONObject(0);
                                String recipient = argument.getString("recipient");
                                String message = argument.getString("message");
                                Log.d(LOG_TAG,"Calling send Activity");
                                ComposeSMSActivity.sendSMS(recipient, message,cordova.getActivity().getApplicationContext());
                                callbackContext.success("Sending initiated");
                                
                            }
                            else if (action.equalsIgnoreCase("checkdefault")) {
                                if(!packageName.equalsIgnoreCase(Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()))){
                                    Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                                    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
                                    cordova.getActivity().startActivity(intent);
                                    Log.d(LOG_TAG,"Default SMS Set successfully");
                                    callbackContext.error("error");
                                }else{
                                    callbackContext.success("success");
                                }
                            }
                        } catch (JSONException e) {
                             e.printStackTrace();
                             callbackContext.error("Input failure");
                        }
                        
                    }
                });
        
        
        return true;
    }

    public static void sendSMSPayload(String message) {
        Log.d(LOG_TAG, "==> CordovaSMSPlugin sendSMSPayload");
        Log.d(LOG_TAG, "\tgWebView: " + gWebView);
        Log.d(LOG_TAG,"Message Received :: "+ message);
        try {
            
            String callBack = "javascript:" + notificationCallBack + "(" + message + ")";
            if(gWebView != null){
                Log.d(LOG_TAG, "\tSent SMS to view: " + callBack);
                gWebView.sendJavascript(callBack);
            }else {
                Log.d(LOG_TAG, "\tView not ready. SAVED NOTIFICATION: " + callBack);
                //lastPush = payload;
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "\tERROR sendSMSToView.: " + e.getMessage());
            //lastPush = payload;
        }
    }

}