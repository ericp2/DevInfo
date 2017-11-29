# DevInfo
Cordova plugin to retrieve useful device information (temperature, mac addresss...)

Currently only implemented for Android.

entry points:

float getTemperature(void): return battery temperature in degrees celsius.
String[] getInterfacesNames(void): retrieve all interfaces names declared in Android.
setInterfaceByName(String Name): set selected interface by name (as retrieved by getInterfacesNames)
String getIPv4(void): retrieve currently selected ip v4 address. may not work for current interface.
String getIPv6(void): retrieve currently selected ip v6 address. may not work for current interface.
String getMacAddress(void): retrieve currently selected mac address (hardware address).
String getEUI48MacAddress(void) retrieve 48 bit mac address from currently selected interface hardware address. only works if starts with 0xFE80


Sample usage: TBD


// create an event to recover device temperature every 2s . this call should probably be done only after onDeviceReady  event received.

window.setInterval(function(){DevInfo.getTemperature(onSuccess);}, 2000);

// log tempeature in java console. can usually be seen from command line by doing something like  :   adb logcat|grep CONSOLE

function onSuccess(values) {
 console.log (values[0]);
  };
