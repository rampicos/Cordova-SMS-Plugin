Android SMS Cordova plugin
========

This plugin is designed for supporting Send & Receive SMS on Cordova based Android apps.

Since Android 4.4+, the Android requires app to be a default SMS app to send and receive SMS, else the sending should silently failed without issues. This Plugin is made to set your Cordova app as default SMS app and give your HTML and Javascript to send and receive SMSs


Installation
--------

```bash
cordova plugin add CordovaSMS
```

Usage
--------

### To Check your App is default SMS App

```javascript

  var errCallback =function(){
    alert("You have set this app as default SMS to proceed, kindly click yes");
    CordovaSMS.checkDefault(function(){},errCallback);
  };

  CordovaSMS.checkDefault(function(){},errCallback);

```

### To Receive SMS

```javascript

  CordovaSMS.onSMS(function(sms){
    alert('Message is '+JSON.stringify(sms));
  });

```

### To Send SMS

```javascript

  var message = "SMS TEXT to Send";
  var receiver = "Receiver's Mobile number";
  CordovaSMS.sendSMS(receiver,message,successCallback,errorCallback});

```

License
--------

    Copyright (C) 2016 Ramkumar Murugadoss

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.