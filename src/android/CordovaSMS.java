package com.ramkumar.cordovaplugins.sms;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.provider.Telephony;
import android.util.Log;


public class CordovaSMS extends CordovaPlugin {
    
    public static CordovaWebView gWebView;
    public static String notificationCallBack = "CordovaSMS.onSMSReceived";
    public static String defaultSMSDialogCallback = "CordovaSMS.onDefaultSMSDialog";
    private static final String LOG_TAG = CordovaSMS.class.getSimpleName();

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        gWebView = webView;
        Log.d(LOG_TAG, "==> CordovaSMSPlugin initialize");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        String callBack = "javascript:" + defaultSMSDialogCallback + "(" + "false" + ")";
        if(resultCode == Activity.RESULT_OK){
            callBack = "javascript:" + defaultSMSDialogCallback + "(" + "true" + ")";
        }
        
        gWebView.sendJavascript(callBack);
    }    

    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) {
        cordova.setActivityResultCallback(this);
                cordova.getThreadPool().execute(new Runnable() {
                    public void run() {
                        try {
                            final String packageName = cordova.getActivity().getPackageName();
                            Log.d(LOG_TAG,"State restored 2::packageName::"+packageName+":::Telephony data::"+Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()));
                            if (action.equalsIgnoreCase("sendsms")) {
                                if(!packageName.equalsIgnoreCase(Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()))){
                                    callbackContext.error("Unable to send SMS, make your app as Default SMS app by calling setDefault method");
                                }
                                else{
                                    JSONObject argument = args.getJSONObject(0);
                                    String recipient = argument.getString("recipient");
                                    String message = argument.getString("message");
                                    ComposeSMSActivity.sendSMS(recipient, message,cordova.getActivity().getApplicationContext());
                                    callbackContext.success("Sending initiated");
                                }
                            }
                             else if (action.equalsIgnoreCase("checkdefault")) {
                                    JSONObject response = new JSONObject();
                                    response.put("thisApp",packageName);
                                    response.put("currentDefault",Telephony.Sms.getDefaultSmsPackage(cordova.getActivity()));
                                    callbackContext.success(response.toString());
                                    
                            }
                            else if (action.equalsIgnoreCase("setdefault")) {
                                    String currentSMSDefault = Telephony.Sms.getDefaultSmsPackage(cordova.getActivity());
                                    JSONObject argument = args.getJSONObject(0);
                                    String defaultPackage = argument.getString("defaultPackage");
                                    Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                                    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, defaultPackage);
                                    cordova.getActivity().startActivityForResult(intent,0);
                                    //callbackContext.success("success");
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
        try {
            String callBack = "javascript:" + notificationCallBack + "(" + message + ")";
            if(gWebView != null){
                gWebView.sendJavascript(callBack);
            }else {
                //lastPush = payload;
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "\tERROR sendSMSToView.: " + e.getMessage());
            //lastPush = payload;
        }
    }
}