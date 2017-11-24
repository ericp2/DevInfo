# DevInfo
Cordova plugin to retrieve useful device information (temperature, mac addresss...)

Currently only implemented for Android.

Sample usage: TBD


// create an event to recover device temperature every 2s . this call should probably be done only after onDeviceReady  event received.

window.setInterval(function(){DevInfo.getTemperature(onSuccess);}, 2000);

// log tempeature in java console. can usually be seen from command line by doing something like  :   adb logcat|grep CONSOLE

function onSuccess(values) {
 console.log (values[0]);
  };
