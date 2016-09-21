var exec = require('cordova/exec');

function CordovaSMS() { 
    console.log("CordovaSMS.js: is created");
    exec(function(){}, function(){}, "CordovaSMS", 'initalize', []);
    
}

CordovaSMS.prototype.sendSMS = function(recipient, message, success, error ){
    exec(success, error, "CordovaSMS", 'sendsms', [{"recipient":recipient,"message":message}]);
}

CordovaSMS.prototype.onSMS = function(callback){
    CordovaSMS.prototype.onSMSReceived = callback;
}

//Default SMS receving callback
CordovaSMS.prototype.onSMSReceived = function(message){
    console.log("Received SMS")
    console.log(message)
}

CordovaSMS.prototype.checkDefault= function(success, error ){
    exec(success, error, "CordovaSMS", 'checkdefault', []);
}


//var CordovaSMS = new CordovaSMS();
module.exports = new CordovaSMS();